package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemRepository menuItemRepository;

    public RecipeIngredientServiceImpl(
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            MenuItemRepository menuItemRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public RecipeIngredient addIngredientToRecipe(
            Long menuItemId, Long ingredientId, Double quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be positive");
        }

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ingredient not found"));

        if (!ingredient.getActive()) {
            throw new BadRequestException("Ingredient is not active");
        }

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu item not found"));

        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(ingredient);
        ri.setMenuItem(menuItem);
        ri.setQuantityRequired(quantity);

        return recipeIngredientRepository.save(ri);
    }

    @Override
    public RecipeIngredient updateRecipeIngredient(Long id, Double quantity) {
        RecipeIngredient ri = recipeIngredientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recipe ingredient not found"));
        ri.setQuantityRequired(quantity);
        return recipeIngredientRepository.save(ri);
    }

    @Override
    public List<RecipeIngredient> getIngredientsByMenuItem(Long menuItemId) {
        return recipeIngredientRepository.findByMenuItemId(menuItemId);
    }

    @Override
    public void removeIngredientFromRecipe(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    public Double getTotalQuantityOfIngredient(Long ingredientId) {
        return recipeIngredientRepository.getTotalQuantityByIngredientid(ingredientId);
    }
}
