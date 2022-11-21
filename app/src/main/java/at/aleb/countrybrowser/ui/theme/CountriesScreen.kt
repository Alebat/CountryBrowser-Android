package at.aleb.countrybrowser.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CountriesScreen(
    countries: StateFlow<Resource<List<Country>>>,
    onClick: (String) -> Int,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        },
        content = {
            when (val result = countries.collectAsState().value) {
                is Resource.SUCCESS -> {
                    LazyColumn(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        itemsIndexed(result.data) { index, it ->
                            if (index > 0)
                                Divider(
                                    color = MaterialTheme.colors.onSurface
                                        .copy(alpha = ContentAlpha.disabled),
                                    thickness = 1.dp
                                )
                            Row(
                                Modifier
                                    .clickable {
                                        onClick(it.name)
                                    }
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(it.emoji, Modifier.padding(6.dp), fontSize = 50.sp)
                                Column {
                                    Text(it.name, style = MaterialTheme.typography.h5)
                                    Text(it.native, style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground
                                            .copy(alpha = ContentAlpha.medium)
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    ProblemWidget(result, onRetry, Modifier.fillMaxWidth())
                }
            }
        }
    )
}