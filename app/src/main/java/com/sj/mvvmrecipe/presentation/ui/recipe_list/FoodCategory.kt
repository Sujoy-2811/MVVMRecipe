package com.sj.mvvmrecipe.presentation.ui.recipe_list

import com.sj.mvvmrecipe.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory( val value : String){
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategory(): List<FoodCategory>{
    return listOf(CHICKEN , BEEF , SOUP,DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT)
}

fun getFoodCategory( value : String): FoodCategory?{
    val map = FoodCategory.values().associateBy( FoodCategory:: value)
    return map[value]
}