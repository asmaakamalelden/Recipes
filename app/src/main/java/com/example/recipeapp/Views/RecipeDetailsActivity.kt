package com.example.recipeapp.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeapp.Models.RecipeModel
import com.example.recipeapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipe = intent.getSerializableExtra("RECIPE_OBJ") as? RecipeModel
        tv_recipe_name.text=recipe?.name
        tv_description.text=recipe?.description
        Picasso.with(this).load(recipe?.image)
            .placeholder(R.drawable.ic_launcher_background).fit().into(imgview)
    }
    companion object {
        fun newInstance(): RecipeDetailsActivity = RecipeDetailsActivity()
    }
}