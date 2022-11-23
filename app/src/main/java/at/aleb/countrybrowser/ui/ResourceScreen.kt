package at.aleb.countrybrowser.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.Resource

@Composable
fun <T>ResourceScreen(
    state: State<Resource<T>>,
    title: String,
    onBack: (() -> Unit)?,
    onRetry: () -> Unit,
    content: @Composable (T, PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(title)
                },
                navigationIcon = onBack?.let { {
                    IconButton(onClick = it) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                } }
            )
        },
        content = {
            when (val resource = state.value) {
                is Resource.Success ->
                    content(
                        resource.data,
                        it
                    )
                else ->
                    ProblemWidget(
                        resource, onRetry,
                        Modifier
                            .fillMaxWidth()
                            .padding(it)
                    )
            }
        }
    )
}