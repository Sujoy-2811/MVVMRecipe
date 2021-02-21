package com.sj.mvvmrecipe.network.model.responses

import com.google.gson.annotations.SerializedName
import com.sj.mvvmrecipe.network.model.RecipeNetworkEntity

class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("result")
    var recipes : List<RecipeNetworkEntity>

)