package com.sj.mvvmrecipe.presentation.ui.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sj.mvvmrecipe.presentation.ui.recipe_list.FoodCategory
import com.sj.mvvmrecipe.presentation.ui.recipe_list.getAllFoodCategory
import kotlinx.coroutines.launch


@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categories: List<FoodCategory>,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    scrollPosition: Float,
    onChangeScrollPosition: (Float) -> Unit,
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        color = Color.White
    ) {
        Column{
            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp),value = query,
                    onValueChange = {newVlaue->
                       onQueryChanged(newVlaue)
                    },label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search), leadingIcon = { Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    ) },
                    onImeActionPerformed = { action , softkeyboardController ->
                        if (action == ImeAction.Search){
                            onExecuteSearch()
                            softkeyboardController?.hideSoftwareKeyboard()
                        }
                    }, textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    backgroundColor = MaterialTheme.colors.surface

                )
            }

            val scrollState = rememberScrollState()
            val scope = rememberCoroutineScope()
            ScrollableRow(modifier = Modifier.fillMaxWidth()
                .padding(start = 8.dp , bottom = 8.dp),
                scrollState = scrollState
            ) {
                scope.launch { scrollState.scrollTo(scrollPosition) }
                for(category in categories){
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onChangeScrollPosition(scrollState.value)
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        },
                    )
                }

            }

        }

    }
}