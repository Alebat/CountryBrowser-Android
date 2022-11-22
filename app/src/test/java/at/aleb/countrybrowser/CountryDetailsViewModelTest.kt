package at.aleb.countrybrowser

import at.aleb.countrybrowser.data.CountryDetailsRepository
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.presentation.CountryDetailsViewModel
import at.aleb.countrybrowser.util.CoroutineRule
import at.aleb.countrybrowser.util.Samples
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CountryDetailsViewModel
    private lateinit var repository: CountryDetailsRepository

    @Before
    fun before() = runTest {
        repository = mockk()

        coEvery { repository.getDetails("IT") } returns Resource.SUCCESS(Samples.countryDetail)
        coEvery { repository.getDetails("AT") } returns Resource.NOTFOUND()

        viewModel = CountryDetailsViewModel(repository)
    }

    @Test
    fun `state flow returns country details`() {
        runTest {
            viewModel.update("IT")
        }

        runTest {
            val result = viewModel.countries.value

            assertEquals(Resource.SUCCESS(Samples.countryDetail), result)

            coVerify { repository.getDetails("IT") }
        }
    }

    @Test
    fun `state flow returns NOTFOUND with other code`() {
        runTest {
            viewModel.update("AT")
        }

        runTest {
            val result = viewModel.countries.value

            assertEquals(Resource.NOTFOUND::class, result::class)

            coVerify { repository.getDetails("AT") }
        }
    }
}