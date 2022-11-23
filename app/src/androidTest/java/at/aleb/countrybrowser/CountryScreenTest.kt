package at.aleb.countrybrowser

import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.framework.MainActivity
import at.aleb.countrybrowser.ui.CountryDetailsScreen
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
class CountryScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var onBack: () -> Unit
    private lateinit var onRetry: () -> Unit
    private lateinit var country: MutableState<Resource<CountryDetails>>

    @Before
    fun before() = runTest {
        country = mutableStateOf(Resource.Empty())

        onBack = mockk()
        onRetry = mockk()

        every { onBack() } returns Unit
        every { onRetry() } returns Unit

        composeTestRule.activity.setContent {
            CountryBrowserTheme {
                CountryDetailsScreen(detail = country, onBack = onBack, onRetry = onRetry)
            }
        }
    }

    @Test
    fun clickBackCallsOnBack() {
        country.value = Resource.Success(Samples.countryDetail)

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.navigate_back)
        ).run {
            assertIsDisplayed()
            coVerify { onBack wasNot Called }
            performClick()
            coVerify { onBack() }
        }
    }

    @Test
    fun dataIsShown() {
        country.value = Resource.Success(Samples.countryDetail)

        composeTestRule.onNodeWithText(Samples.countryDetail.native).assertIsDisplayed()
        composeTestRule.onNodeWithText(Samples.countryDetail.capital).assertIsDisplayed()
        composeTestRule.onNodeWithText(Samples.countryDetail.emoji).assertIsDisplayed()
        composeTestRule.onNodeWithText(Samples.countryDetail.continent).assertIsDisplayed()
        composeTestRule.onNodeWithText("+39").assertIsDisplayed()
        composeTestRule.onNodeWithText("Italian").assertIsDisplayed()
        composeTestRule.onNodeWithTag(Samples.countryDetail.coatOfArmsUrl).assertIsDisplayed()
    }
}