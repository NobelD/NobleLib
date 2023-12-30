package me.nobeld.minecraft.noblelib.model;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

public class RecipeModel {
    private Recipe recipe;
    private NamespacedKey key;
    public RecipeModel(Recipe recipe, NamespacedKey key) {
        this.recipe = recipe;
        this.key = key;
    }
}
