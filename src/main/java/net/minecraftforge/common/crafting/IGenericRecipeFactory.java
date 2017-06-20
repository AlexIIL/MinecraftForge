package net.minecraftforge.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.util.ResourceLocation;

@FunctionalInterface
public interface IGenericRecipeFactory {
    void parseAndRegister(JsonContext context, JsonObject json, ResourceLocation name) throws JsonSyntaxException;
}
