package net.minecraftforge.common.alexiil.tmp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;

public class MinecartHooks {

    // Temp instead of events
    public static IMinecartHook HOOK;

    public interface IMinecartHook {
        /** Fired before moving a minecart as if it was derailed.
         * 
         * @return True to continue moving it as if it was disabled, false to abort. */
        boolean onUnknownRail(EntityMinecart cart, BlockPos pos, IBlockState state);
    }

    /** Fired before moving a minecart as if it was derailed.
     * 
     * @return True to continue moving it as if it was disabled, false to abort. */
    public static boolean onUnknownRail(EntityMinecart cart, BlockPos pos, IBlockState state) {
        if (HOOK != null) {
            return HOOK.onUnknownRail(cart, pos, state);
        }
        return true;
    }
}
