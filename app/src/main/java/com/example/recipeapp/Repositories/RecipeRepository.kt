package com.example.recipeapp.Repositories

import com.example.recipeapp.Models.RecipeModel
import com.example.weatherloggerapp.Repositories.Server.ApiClient
import com.example.weatherloggerapp.Repositories.Server.ApiInterface
import io.reactivex.Observable

class RecipeRepository {
    private val apiInterface: ApiInterface = ApiClient.getInstance()?.getApi!!


    fun getRecipes(): Observable<List<RecipeModel>> {
        return apiInterface.getRecipes()
    }
}