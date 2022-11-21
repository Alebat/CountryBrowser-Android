package at.aleb.countrybrowser.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.Resource

@Composable
fun ProblemWidget(resource: Resource<*>, onRetry: () -> Unit, modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (resource) {
            is Resource.EMPTY -> Text(stringResource(R.string.no_items), Modifier.padding(48.dp))
            is Resource.ERROR -> Text(
                stringResource(R.string.generic_error),
                Modifier.padding(48.dp)
            )
            is Resource.LOADING -> CircularProgressIndicator(Modifier.padding(48.dp))
            is Resource.NOCONNECTION -> Text(
                stringResource(R.string.no_connection),
                Modifier.padding(48.dp)
            )
            is Resource.NOTFOUND -> Text(
                stringResource(R.string.no_items_found),
                Modifier.padding(48.dp)
            )
            is Resource.SUCCESS -> {
                Text(stringResource(R.string.success))
            }
        }
        when (resource) {
            is Resource.LOADING -> {}
            is Resource.SUCCESS -> {}
            else -> {
                Button(onClick = onRetry, content = {
                    Text(stringResource(R.string.retry))
                })
            }
        }
    }
}