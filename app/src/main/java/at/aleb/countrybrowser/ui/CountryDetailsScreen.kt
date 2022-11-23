package at.aleb.countrybrowser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.domain.getDetailDescriptions
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CountryDetailsScreen(
    detail: State<Resource<CountryDetails>>,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    columns: Int = 2
) {
    val title =
        (detail.value as? Resource.Success)?.data?.name ?: stringResource(R.string.country_detail)

    ResourceScreen(
        state = detail,
        title = title,
        onBack = onBack,
        onRetry = onRetry,
    ) { data, padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(data.coatOfArmsUrl)
                    .crossfade(true)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .build(),
                modifier = Modifier
                    .padding(16.dp)
                    .testTag(data.coatOfArmsUrl),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.coat_of_arms),
                contentScale = ContentScale.FillWidth
            )

            Spacer(Modifier.height(12.dp))

            data.getDetailDescriptions().chunked(columns).forEach {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    it.forEach {
                        DetailCard(
                            Modifier.weight(1f),
                            detail = it
                        )
                    }
                    repeat(columns - it.size) {
                        Box(
                            Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}