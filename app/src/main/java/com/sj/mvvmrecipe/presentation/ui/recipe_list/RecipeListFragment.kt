package com.sj.mvvmrecipe.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sj.mvvmrecipe.presentation.BaseApplication
import com.sj.mvvmrecipe.presentation.ui.components.CircularIndeterminateProgressBar
import com.sj.mvvmrecipe.presentation.ui.components.LoadingRecipeListShimmer
import com.sj.mvvmrecipe.presentation.ui.components.RecipeCard
import com.sj.mvvmrecipe.presentation.ui.components.SearchAppBar
import com.sj.mvvmrecipe.presentation.ui.components.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var  application : BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val categoryScrollPosition = viewModel.categoryScrollPosition
                    val loading = viewModel.loading.value


                    Column {

                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = viewModel::newSearch,
                            categories = getAllFoodCategory(),
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            scrollPosition = categoryScrollPosition,
                            onChangeScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            onToggleTheme = { application.toggleLightTheme()}
                            )


                        Box(modifier = Modifier.fillMaxSize()
                            .background(color = MaterialTheme.colors.background)) {
                            if (loading){
                                LoadingRecipeListShimmer(imageHeight = 250.dp,)
                            }else{
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }

                    }
                }
            }
        }
    }

}