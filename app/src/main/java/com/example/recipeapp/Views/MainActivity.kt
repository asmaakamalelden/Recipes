package com.example.recipeapp.Views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Models.RecipeModel
import com.example.recipeapp.R
import com.example.recipeapp.Views.Utils.NetworkConnection
import com.example.recipeapp.Views.ViewModels.RecipeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var recipeList = ArrayList<RecipeModel>()
    lateinit var viewModel: RecipeViewModel
    lateinit var adapter: RecipeAdapter
    private val sharedPrefFile = "sharedpreference"
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecipeAdapter(this)
        rv_recipe.adapter = adapter
        rv_recipe.layoutManager = LinearLayoutManager(this)
        var sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val sharedNameValue = sharedPreferences.getString("sort_key", "")
        checkConnection()


        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        viewModel.getRecipeLiveData()?.observe(this, Observer {
            recipeList = it as ArrayList<RecipeModel>
            if (sharedNameValue.equals("CALORIES")) {
                recipeList.sortByDescending { it.calories }
            }
            if (sharedNameValue.equals("FATS")) {
                recipeList.sortByDescending { it.fats }
            }
            adapter.setRecipes(recipeList)

        })

        etxt_search.addTextChangedListener(searchTextWatcher)
    }

    private fun checkConnection() {
        val networkconnection = NetworkConnection(applicationContext)
        networkconnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                val snack = Snackbar.make(layout, "Connection is Online", Snackbar.LENGTH_LONG)
                snack.show()
            } else {
                val snack = Snackbar.make(layout, "connection is offline", Snackbar.LENGTH_LONG)
                snack.show()
            }
        })
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            filterList(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun filterList(filterName: String) {
        var tempList: MutableList<RecipeModel> = ArrayList()
        for (d in recipeList) {
            if (filterName in d.name.toLowerCase()) {
                tempList.add(d)
            }
        }
        adapter.setRecipes(tempList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item_calories -> {
                recipeList.sortByDescending { it.calories }
                adapter.setRecipes(recipeList)
                editor.putString("sort_key", "CALORIES").apply()
                editor.commit()
            }
            R.id.item_fat -> {
                recipeList.sortByDescending { it.fats }
                adapter.setRecipes(recipeList)
                editor.putString("sort_key", "FATS").apply()
                editor.commit()
            }
            else ->
                recipeList.sortByDescending { it.fats }

        }
        return true
    }
}