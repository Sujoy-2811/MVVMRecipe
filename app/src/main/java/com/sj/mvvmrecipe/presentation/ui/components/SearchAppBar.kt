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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sj.mvvmrecipe.presentation.ui.recipe_list.FoodCategory
import com.sj.mvvmrecipe.presentation.ui.recipe_list.RecipeListEvent
import com.sj.mvvmrecipe.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: (RecipeListEvent) -> Unit,
    categories: List<FoodCategory>,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    scrollPosition: Float,
    onChangeScrollPosition: (Float) -> Unit,
    onToggleTheme: () -> Unit,
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
        ,
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ){
        Column{
            Row(modifier = Modifier.fillMaxWidth()){
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp)
                    ,
                    value = query,
                    onValueChange = {
                        onQueryChanged(it)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search , contentDescription = null)
                    },
                    onImeActionPerformed = { action, softKeyboardController ->
                        if (action == ImeAction.Done) {
                            onExecuteSearch(NewSearchEvent)
                            softKeyboardController?.hideSoftwareKeyboard()
                        }
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    backgroundColor = MaterialTheme.colors.surface
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val (menu) = createRefs()
                    IconButton(
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                linkTo(top = parent.top, bottom = parent.bottom)
                            },
                        onClick = onToggleTheme
                        ,
                    ){
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }
                }
            }
            val scrollState = rememberScrollState()
            val scope = rememberCoroutineScope()
            ScrollableRow(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                ,
                scrollState = scrollState,
            ) {

                // restore scroll position after rotation
                scope.launch{ scrollState.scrollTo(scrollPosition) }


                for(category in categories){
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onChangeScrollPosition(scrollState.value)
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                            onExecuteSearch( NewSearchEvent)
                        },
                    )
                }
            }
        }
    }
}