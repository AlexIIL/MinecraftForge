package alexiil.mc.mod.traincraft._tmp.forge;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.alexiil.tmp.MinecartHooks.IMinecartHook;

import alexiil.mc.mod.traincraft.api.lib.MathUtil;
import alexiil.mc.mod.traincraft.api.track.ITrackBlock;
import alexiil.mc.mod.traincraft.api.track.path.ITrackPath;
import alexiil.mc.mod.traincraft.api.track.path.RayTraceTrackPath;

public enum MinecartHooksImpl implements IMinecartHook {
    INSTANCE;

    /** Fired before moving a minecart as if it was derailed.
     * 
     * @return True to continue moving it as if it was disabled, false to abort. */
    @Override
    public boolean onUnknownRail(EntityMinecart cart, BlockPos pos, IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof ITrackBlock) {
            ITrackBlock track = (ITrackBlock) block;
            Vec3d rayTraceStart = cart.getPositionVector().addVector(0, 0.5, 0);
            Vec3d rayTraceDirection = new Vec3d(0, -1, 0);
            RayTraceTrackPath rayTrace = track.behaviours(cart.worldObj, pos, state)//
                    .map((behaviour) -> behaviour.rayTrace(rayTraceStart, rayTraceDirection))//
                    .filter((trace) -> isRiding(cart, trace))//
                    .sorted(MinecartHooksImpl::compare).findFirst().orElse(null);
            if (rayTrace == null) {
                return false;
            }
            applyMovement(cart, rayTrace);
            cart.applyDragCaller();
            return true;
        }
        return false;
    }

    private static boolean isRiding(EntityMinecart cart, RayTraceTrackPath trace) {
        return trace.closestPoint.distanceTo(cart.getPositionVector()) < 0.3;
    }

    private static int compare(RayTraceTrackPath a, RayTraceTrackPath b) {
        double diff = b.distance - a.distance;
        if (diff < 0) return -1;
        if (diff > 0) return 1;
        return 0;
    }

    private static void applyMovement(EntityMinecart cart, RayTraceTrackPath rayTrace) {
        boolean slow = shouldSlow(cart);

        ITrackPath path = rayTrace.path;
        double interp = rayTrace.interp;
        Vec3d pathDirection = path.direction(interp);
        Vec3d position = path.interpolate(interp);
        Vec3d minecartMotion = new Vec3d(cart.motionX, cart.motionY, cart.motionZ).normalize();

        if (MathUtil.dot(pathDirection, minecartMotion) < 0) {
            pathDirection = new Vec3d(0, 0, 0).subtract(pathDirection);
        }

        double speedSquared = cart.motionX * cart.motionX + cart.motionY * cart.motionY + cart.motionZ * cart.motionZ;
        double speed = Math.sqrt(speedSquared);
        Vec3d motionScaled = MathUtil.scale(pathDirection, speed);
        if (slow) {
            if (speed < 0.03) {
                motionScaled = new Vec3d(0, 0, 0);
            } else {
                motionScaled = MathUtil.scale(motionScaled, 0.5);
            }
        }

        position = position.add(motionScaled);

        cart.setPosition(position.xCoord, position.yCoord, position.zCoord);

        cart.motionX = motionScaled.xCoord;
        cart.motionY = motionScaled.yCoord;
        cart.motionZ = motionScaled.zCoord;
    }

    private static boolean shouldSlow(EntityMinecart cart) {
        Entity passenger = cart.getPassengers().isEmpty() ? null : cart.getPassengers().get(0);
        if (passenger instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) passenger;
            double d6 = entity.moveForward;

            if (d6 > 0.0D) {
                double d7 = -Math.sin(entity.rotationYaw * 0.017453292F);
                double d8 = Math.cos(entity.rotationYaw * 0.017453292F);
                double d9 = cart.motionX * cart.motionX + cart.motionZ * cart.motionZ;

                if (d9 < 0.01D) {
                    cart.motionX += d7 * 0.1D;
                    cart.motionZ += d8 * 0.1D;
                    return false;
                }
            }
        }
        return true;
    }

}
