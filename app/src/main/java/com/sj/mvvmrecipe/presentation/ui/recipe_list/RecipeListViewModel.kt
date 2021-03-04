package com.sj.mvvmrecipe.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sj.mvvmrecipe.domain.model.Recipe
import com.sj.mvvmrecipe.repository.RecipeRepository
import com.sj.mvvmrecipe.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f

    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    var recipeListScrollPostion =0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent( event : RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is RecipeListEvent.NewSearchEvent -> newSearch()
                    is RecipeListEvent.NextPageEvent -> nextPage()
                }
            }catch (e : Exception){
                Log.d(TAG , " onTriggerEvent : Exeption : ${e}, ${e.cause}")
            }
        }
    }

    private suspend  fun newSearch(){
            loading.value = true
            resetSearchState()
            delay(2000)
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value = false

    }

    private suspend fun nextPage(){
            if ((recipeListScrollPostion+1) >= (page.value * PAGE_SIZE)
            ){
                loading.value = true
                incrementPage()
                Log.d(TAG,"next page : ${page.value}")

                if(page.value > 1){
                    val result = repository.search(
                        token = token,
                        page = page.value,
                        query = query.value
                    )
                    Log.d(TAG,"nextPage : ${result}")
                    appendRecipes(result)
                }
                loading.value =false
            }
    }





    private fun appendRecipes( recipes : List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage (){
        page.value = page.value +1
    }

    fun onChangeRecipeScrollPosition(position : Int){
        recipeListScrollPostion = position
    }

    private  fun resetSearchState(){
        recipes.value = listOf()
        page.value =1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectwdCategory()
    }
    private fun clearSelectwdCategory(){
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String){
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float){
        categoryScrollPosition = position
    }
}







