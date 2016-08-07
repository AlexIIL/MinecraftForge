package net.minecraftforge.event.entity.minecart;

import net.minecraft.entity.item.EntityMinecart;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

/**
 * MinecartUpdateEvent is fired when a minecart is updated on
 * the logical server and attempts to find a rail to move along.
 * <br>
 * This event is {@link Cancelable}, and canceling this event
 * will cancel the minecart's vanilla rail movement.<br>
 * <br>
 * This event is fired in {@link EntityMinecart#onUpdate()},
 * before any vanilla positional update code runs.
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class MinecartFindRailEvent extends MinecartEvent
{
    public MinecartFindRailEvent(EntityMinecart minecart)
    {
        super(minecart);
    }
}
