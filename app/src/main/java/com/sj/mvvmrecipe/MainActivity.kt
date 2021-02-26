package com.sj.mvvmrecipe

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.sj.mvvmrecipe.network.RecipeService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    @Inject
    lateinit var app: BaseApplication

    @Inject
    lateinit var randomString: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("MainActivity", "Msg $randomString")

//        println("onCreate: the app context: ${app}")
//        println("onCreate: ${randomString}")
//
//        val service = Retrofit.Builder()
//            .baseUrl("https://food2fork.ca/api/recipe/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(RecipeService::class.java)
//
//        CoroutineScope(IO).launch {
//            val recipe = service.get("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", 583)
//            Log.d("MainActivity", "Value : ${recipe.title}")
//        }
    }
}