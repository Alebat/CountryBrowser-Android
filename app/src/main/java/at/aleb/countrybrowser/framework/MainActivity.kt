package at.aleb.countrybrowser.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import at.aleb.countrybrowser.ui.NavGraph
import at.aleb.countrybrowser.ui.theme.CountryBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountryBrowserTheme {
                window.navigationBarColor = MaterialTheme.colors.background.toArgb()
                NavGraph()
            }
        }
    }
}