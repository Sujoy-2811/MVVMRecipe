package com.sj.mvvmrecipe.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sj.mvvmrecipe.presentation.ui.components.RecipeCard
import com.sj.mvvmrecipe.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.converter.gson.GsonConverterFactory


@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                for(recipe in recipes){
                    Log.d(TAG, "RECIPE: ${recipe.title}")
                }

                LazyColumn{
                    itemsIndexed( items = recipes){index, recipe ->
                        RecipeCard(recipe = recipe, onClick = { })
                    }
                }
            }
        }
    }

}
