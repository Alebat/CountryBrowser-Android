
package at.aleb.countrybrowser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.aleb.countrybrowser.presentation.CountriesViewModel
import at.aleb.countrybrowser.ui.theme.CountriesScreen
import at.aleb.countrybrowser.ui.theme.CountryBrowserTheme
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apolloClient: ApolloClient

    private val viewModel: CountriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountryBrowserTheme {
                CountriesScreen(
                    viewModel.countries,
                    { countryCode ->
                        Log.i("TAG","Clicked $countryCode")
                    }, {
                        viewModel.update()
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountryBrowserTheme {
        Greeting("Android")
    }
}