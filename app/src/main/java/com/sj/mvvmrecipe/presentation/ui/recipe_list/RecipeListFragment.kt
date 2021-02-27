package com.sj.mvvmrecipe.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TextField
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sj.mvvmrecipe.presentation.ui.components.RecipeCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
                val recipes = viewModel.recipes.value
        return ComposeView(requireContext()).apply {
            setContent {

               Column {

                   val recipes = viewModel.recipes.value
                   var query = viewModel.query

                   TextField(value = query.value, onValueChange = {newVlaue->
                       viewModel.onQueryChanged(newVlaue)
                   })

                    LazyColumn{
                        itemsIndexed( items = recipes){index, recipe ->
                            RecipeCard(recipe = recipe, onClick = { })
                        }
                    }

               }
            }
        }
    }

}
