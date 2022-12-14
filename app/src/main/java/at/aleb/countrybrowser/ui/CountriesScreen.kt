package at.aleb.countrybrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource

@Composable
fun CountriesScreen(
    countries: State<Resource<List<Country>>>,
    onSelect: (String) -> Unit,
    onRetry: () -> Unit
) {
    ResourceScreen(
        state = countries,
        title = stringResource(id = R.string.app_name),
        onBack = null,
        onRetry = onRetry) { data, it ->
        LazyColumn(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            itemsIndexed(data) { index, it ->
                if (index > 0)
                    Divider(
                        color = MaterialTheme.colors.onSurface
                            .copy(alpha = ContentAlpha.disabled),
                        thickness = 1.dp
                    )
                Row(
                    Modifier
                        .clickable {
                            onSelect(it.code)
                        }
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(it.emoji, Modifier.padding(6.dp), fontSize = 50.sp)
                    Column {
                        Text(it.name, style = MaterialTheme.typography.h5)
                        Text(
                            it.native, style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground
                                .copy(alpha = ContentAlpha.medium)
                        )
                    }
                }
            }
        }
    }
}