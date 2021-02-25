package com.sj.mvvmrecipe.network.responses

import com.google.gson.annotations.SerializedName
import com.sj.mvvmrecipe.network.model.RecipeDto

data class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("result")
    var recipes : List<RecipeDto>

)