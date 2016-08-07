package net.minecraftforge.event.entity.minecart;

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

/**
 * MinecartUpdateEvent is fired when a minecart tries to find out its exact position.<br>
 * <br>
 * This event not is {@link Cancelable}<br>
 * <br>
 * This event is fired in {@link EntityMinecart#getPos(double, double, double)} and
 * {@link EntityMinecart#getPosOffset(double, double, double, double)}, after
 * vanilla has tried to find a normal {@link BlockRailBase} rail.
 * <br>
 * The pos ({@link #getPos()} and {@link #setPos(Vec3d)} will get and set
 * the resulting position that the minecart will use.
 * <br>
 * Note that the {@link #getX()}, {@link #getY()}, and {@link #getZ()} will return
 * different values than {@link Entity#posX} etc will, as these might be offset with
 * the partial ticks between its last tick position and its current.
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class MinecartGetPosEvent extends MinecartEvent
{
    private final double x, y, z, offset;
    private Vec3d pos = null;

    public MinecartGetPosEvent(EntityMinecart minecart, double x, double y, double z, double offset)
    {
        super(minecart);
        this.x = x;
        this.y = y;
        this.z = z;
        this.offset = offset;
    }

    /**
     * @return The x position that the cart is attempting to find from.
     * <br>
     * Note that this might be different to {@link Entity#posX}
     */
    public double getX()
    {
        return x;
    }

    /**
     * @return The y position that the cart is attempting to find from.
     * <br>
     * Note that this might be different to {@link Entity#posY}
     */
    public double getY()
    {
        return y;
    }

    /**
     * @return The z position that the cart is attempting to find from.
     * <br>
     * Note that this might be different to {@link Entity#posZ}
     */
    public double getZ()
    {
        return z;
    }

    /**
     * The offset that should be used when detecting the position of the minecart.
     * <br>
     * In vanilla code this is any of 0, -0.3, or 0.3
     */
    public double getOffset()
    {
        return offset;
    }

    public void setPos(Vec3d vec)
    {
        pos = vec;
    }

    public Vec3d getPos()
    {
        return pos;
    }
}
