package com.sj.mvvmrecipe.network.responses

import com.google.gson.annotations.SerializedName
import com.sj.mvvmrecipe.network.model.RecipeDto

data class RecipeSearchResponse(

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>,
)