package drzhark.mocreatures.compat.futuremc;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thedarkcolour.futuremc.recipe.campfire.CampfireRecipes;

public class FutureMCIntegration {

    public static void addRecipes() {
        // Campfire
        CampfireRecipes.INSTANCE.addRecipe(Ingredient.fromStacks(new ItemStack(MoCItems.crabraw)), new ItemStack(MoCItems.crabcooked), 600);
        CampfireRecipes.INSTANCE.addRecipe(Ingredient.fromStacks(new ItemStack(MoCItems.ostrichraw)), new ItemStack(MoCItems.ostrichcooked), 600);
        CampfireRecipes.INSTANCE.addRecipe(Ingredient.fromStacks(new ItemStack(MoCItems.ratRaw)), new ItemStack(MoCItems.ratCooked), 600);
        CampfireRecipes.INSTANCE.addRecipe(Ingredient.fromStacks(new ItemStack(MoCItems.rawTurkey)), new ItemStack(MoCItems.cookedTurkey), 600);
        CampfireRecipes.INSTANCE.addRecipe(Ingredient.fromStacks(new ItemStack(MoCItems.turtleraw)), new ItemStack(MoCItems.turtlecooked), 600);
    }
}