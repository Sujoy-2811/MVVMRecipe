package com.sj.mvvmrecipe.presentation.ui.recipe_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sj.mvvmrecipe.repository.RecipeRepository
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
    private val randomString:String,
    private  val repository: RecipeRepository,
    private @Named("auth_token") val token :String
): ViewModel(){
    init {
        println("ViewModel : ${randomString}")
        println("ViewModel : ${repository}")
        println("ViewModel : ${token}")
    }

    fun getRepo()= repository
    fun getRandomString()= randomString
    fun getToken() = token
}