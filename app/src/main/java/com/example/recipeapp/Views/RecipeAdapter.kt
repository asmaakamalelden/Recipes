package com.example.recipeapp.Views

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Models.RecipeModel
import com.example.recipeapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeAdapter(val context: Context) : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {
    var recipeList = emptyList<RecipeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipeList[position])
        Picasso.with(context).load(recipeList[position].image)
            .placeholder(R.drawable.ic_launcher_background).fit()
            .into(holder.itemView.imgview_recipe)

        holder.itemView.setOnClickListener {
                var intent = Intent(context, RecipeDetailsActivity::class.java)
                intent.putExtra("RECIPE_OBJ", recipeList[position])
                context.startActivity(intent);
        }
    }

    internal fun setRecipes(recipes: List<RecipeModel>) {
        this.recipeList = recipes
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(current: RecipeModel) {
            itemView.tv_name.text = current.name
            itemView.tv_calories.text = "Calories      " + current.calories
            itemView.tv_fats.text = "Fats      " + current.fats


        }
    }
}
