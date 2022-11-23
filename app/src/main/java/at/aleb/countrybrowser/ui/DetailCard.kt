package at.aleb.countrybrowser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.aleb.countrybrowser.R
import at.aleb.countrybrowser.domain.DetailDescription

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    detail: DetailDescription
) {
    Card(
        modifier
            .padding(10.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(detail.emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                Modifier.height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    detail.description,
                    color = MaterialTheme.colors.onSurface
                        .copy(alpha = ContentAlpha.disabled)
                )
                if (detail.text.isNotEmpty())
                    Text(
                        detail.text
                    )
                else
                    Text(
                        stringResource(R.string.unspecified),
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.onSurface
                            .copy(alpha = ContentAlpha.disabled)
                    )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}