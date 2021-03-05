package com.sj.mvvmrecipe.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sj.mvvmrecipe.domain.model.Recipe
import com.sj.mvvmrecipe.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.sj.mvvmrecipe.repository.RecipeRepository
import com.sj.mvvmrecipe.util.TAG
import kotlinx.coroutines.launch
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

class RecipeViewModel
@ViewModelInject
constructor(
    private  val recipeRepository : RecipeRepository,
    private @Named("auth_token") val token : String,
    @Assisted private val state : SavedStateHandle
):ViewModel(){

    val recipe : MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent( event : RecipeEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetRecipeEvent ->{
                        if (recipe.value == null){
                            getRecipe(event.id)
                        }
                    }
                }

            }catch (e : Exception){
                Log.d(TAG , "onTriggerEvent : Exception ${e}, ${e.cause}")
            }
        }
    }
    private suspend fun getRecipe(id: Int){
        loading.value = true
        val recipe = recipeRepository.get(token ,id)
        this.recipe.value= recipe

        state.set(STATE_KEY_RECIPE , recipe.id)
        loading.value =false
    }

}