package com.example.recipeapp.Views.ViewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.Models.RecipeModel
import com.example.recipeapp.Repositories.RecipeRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class RecipeViewModel:ViewModel() {
    var recipeRepository: RecipeRepository? = null
    var recipeLiveData: MutableLiveData<List<RecipeModel>>? = null
    var disposable: Disposable? = null

     init{
         recipeRepository= RecipeRepository()
         recipeLiveData= MutableLiveData()
         getRecipes()
    }

    fun getRecipes() {
        disposable= recipeRepository!!.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            response -> recipeLiveData!!.setValue(response) }
                ) { error -> showError(error.toString()) }
    }


    fun showError(errorMsg: String?) {
        Log.d("Error Msg", errorMsg)
    }

    fun getRecipeLiveData(): LiveData<List<RecipeModel>>? {
        return recipeLiveData
    }

    fun disposeCalling(){
        disposable?.dispose()
    }
}