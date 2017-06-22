package net.minecraftforge.debug;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.IGenericRecipeFactory;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.RecipeReloadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.BooleanSupplier;

@Mod(modid = CraftingSystemTest.MOD_ID, name = "CraftingTestMod", version = "1.0", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public class CraftingSystemTest
{
    static final String MOD_ID = "crafting_system_test";
    static final boolean ENABLED = false;
    static Logger log;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        log = event.getModLog();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        log.info("Registering Test Recipes:");
    }

    @SubscribeEvent
    public static void recipeReloadPre(RecipeReloadEvent.Pre event)
    {
        log.info("Registering Recipe Factories:");
        // Note that this isn't done properly:
        // we also need to clear out any custom smelting recipes, but we don't have
        // control over the maps. As such this isn't a good example and custom
    	// smelting recipes will need to be accomplished in a different way.
        CraftingHelper.register(new ResourceLocation(MOD_ID, "smelting"), new FurnaceFactory());
    }

    public static class IngredientFactory implements IIngredientFactory
    {
        @Override
        public Ingredient parse(JsonContext context, JsonObject json)
        {
            return null;
        }
    }

    public static class RecipeFactory implements IRecipeFactory
    {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json)
        {
            return null;
        }

    }

    public static class ConditionFactory implements IConditionFactory
    {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json)
        {
            return () -> true;
        }
    }

    public static class FurnaceFactory implements IGenericRecipeFactory {

        @Override
        public void parseAndRegister(JsonContext context, JsonObject json, ResourceLocation name) throws JsonSyntaxException {
            // While this does work, its not ideal
        	// Note that this WILL break when recipes are reloaded multiple times.
            ItemStack input = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "input"), context);
            ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
            FurnaceRecipes.instance().addSmeltingRecipe(input, result, 0);
        }
    }
}
