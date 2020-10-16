package com.example.weatherloggerapp.Repositories.Server

import com.example.recipeapp.Models.RecipeModel
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {
    @GET("/android-test/recipes.json")
    fun getRecipes(): Observable<List<RecipeModel>>
}