package com.sj.mvvmrecipe.presentation.ui.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sj.mvvmrecipe.presentation.ui.recipe_list.FoodCategory
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
    onToggleTheme : () -> Unit
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        color = MaterialTheme.colors.surface
    ) {
        Column{
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

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
                IconButton(onClick = onToggleTheme , modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Filled.MoreVert , contentDescription = null)

                }
            }

            val scrollState = rememberScrollState()
            val scope = rememberCoroutineScope()
            ScrollableRow(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp),
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