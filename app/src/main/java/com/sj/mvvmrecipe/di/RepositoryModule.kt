package com.sj.mvvmrecipe.di

import com.sj.mvvmrecipe.network.RecipeService
import com.sj.mvvmrecipe.network.model.RecipeDtoMapper
import com.sj.mvvmrecipe.repository.RecipeRepository
import com.sj.mvvmrecipe.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        recipeService: RecipeService,
        recipeDToMapper: RecipeDtoMapper
    ):RecipeRepository{
        return  RecipeRepository_Impl(recipeService , recipeDToMapper)
    }
}