package net.minecraftforge.test;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartFindRailEvent;
import net.minecraftforge.event.entity.minecart.MinecartGetPosEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid="pressure_plate_rail_test", name="PressurePlateRailTest", version="0.0.0")
public class PressurePlateRailTest
{
    @Mod.EventHandler
    public static void fmlPreInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(PressurePlateRailTest.class);
    }

    @SubscribeEvent
    public static void onMinecartFindRail(MinecartFindRailEvent event)
    {
        EntityMinecart cart = event.getMinecart();
        BlockPos pos = cart.getPosition();
        boolean onPlate = false;

        if (cart.worldObj.getBlockState(pos).getBlock() instanceof BlockPressurePlate)
        {
            onPlate = true;
        }
        else if (cart.worldObj.getBlockState(pos.down()).getBlock() instanceof BlockPressurePlate)
        {
            onPlate = true;
            pos = pos.down();
        }

        if (onPlate)
        {
            // Similar code to what EntityMinecart does
            cart.fallDistance = 0;
            double x = cart.posX;
            double z = cart.posZ;
            cart.setPosition(x, pos.getY(), z);
            cart.moveMinecartOnRail(pos);
            cart.callApplyDrag();

            // Make sure the minecart itself doesn't get to run any code
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMinecartGetPos(MinecartGetPosEvent event)
    {
        EntityMinecart cart = event.getMinecart();
        boolean onPlate = false;

        double x = event.getX();
        double y = event.getY();
        double z = event.getZ();

        BlockPos pos = new BlockPos(x, y, z);

        if (cart.worldObj.getBlockState(pos).getBlock() instanceof BlockPressurePlate)
        {
            onPlate = true;
        }
        else if (cart.worldObj.getBlockState(pos.down()).getBlock() instanceof BlockPressurePlate)
        {
            onPlate = true;
        }

        if (onPlate)
        {
            double offset = event.getOffset();
            x += cart.motionX * offset;
            y += cart.motionY * offset;
            z += cart.motionZ * offset;
            event.setPos(new Vec3d(x, y + 1 / 16.0, z));
        }
    }
}
