package com.sj.mvvmrecipe.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sj.mvvmrecipe.presentation.BaseApplication
import com.sj.mvvmrecipe.presentation.ui.components.CircularIndeterminateProgressBar
import com.sj.mvvmrecipe.presentation.ui.components.IMAGE_HEIGHT
import com.sj.mvvmrecipe.presentation.ui.components.LoadingRecipeShimmer
import com.sj.mvvmrecipe.presentation.ui.components.RecipeView
import com.sj.mvvmrecipe.presentation.ui.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    private  val viewModel : RecipeViewModel by viewModels()
    @Inject
    lateinit var application: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { rId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(rId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                AppTheme(darkTheme = application.isDark.value) {

                    Scaffold {
                        Box(modifier = Modifier.fillMaxSize()
                        ){
                            if (loading && recipe == null) 
                                LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            else recipe?.let{
                                RecipeView(recipe = it)
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                        
                    }
                }
            }
        }
    }
}