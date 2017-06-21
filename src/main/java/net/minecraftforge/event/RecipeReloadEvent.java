/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.minecraftforge.event;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.IGenericRecipeFactory;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Fired during loading of recipes.
 */
public class RecipeReloadEvent extends Event
{
    /**
     * Fired before scanning all mods for recipes.
     * <p>
     * Register custom {@link IConditionFactory}, {@link IIngredientFactory},
     * {@link IRecipeFactory}, and {@link IGenericRecipeFactory} with
     * {@link CraftingHelper} during this event. Additionally listeners should
     * clear recipe lists used by {@link IGenericRecipeFactory}, as then they
     * can be repopulated from world data.
     */
    public static class Pre extends RecipeReloadEvent
    {

    }

    /**
     * Fired after all recipes have been loaded. Use this event for 
     */
    public static class Post extends RecipeReloadEvent
    {

    }
}
