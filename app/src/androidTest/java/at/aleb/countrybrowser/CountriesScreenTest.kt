package at.aleb.countrybrowser

import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.ui.CountriesScreen
import at.aleb.countrybrowser.ui.theme.CountryBrowserTheme
import at.aleb.countrybrowser.util.Samples
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CountriesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var onSelect: (String) -> Unit
    private lateinit var onRetry: () -> Unit
    private lateinit var countries: MutableState<Resource<List<Country>>>

    @Before
    fun before() = runTest {
        countries = mutableStateOf(Resource.EMPTY())

        onSelect = mockk()
        onRetry = mockk()

        every { onSelect(any()) } returns Unit
        every { onRetry() } returns Unit

        composeTestRule.activity.setContent {
            CountryBrowserTheme {
                CountriesScreen(countries = countries, onSelect = onSelect, onRetry = onRetry)
            }
        }
    }

    @Test
    fun retryCallsOnRetry() {
        countries.value = Resource.NOCONNECTION()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.retry)).run {
            assertIsDisplayed()
            coVerify { onRetry wasNot Called }
            performClick()
            coVerify { onRetry() }
        }
    }

    @Test
    fun clickCallsOnSelect() {
        countries.value = Resource.SUCCESS(Samples.countriesList)

        composeTestRule.onNodeWithText(Samples.countriesList[1].name).run {
            assertIsDisplayed()
            coVerify { onSelect wasNot Called }
            performClick()
            coVerify { onSelect(Samples.countriesList[1].code) }
        }
    }
}