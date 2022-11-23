package at.aleb.countrybrowser

import at.aleb.countrybrowser.data.CountriesRepository
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.presentation.CountriesViewModel
import at.aleb.countrybrowser.util.CoroutineRule
import at.aleb.countrybrowser.util.Samples
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: CountriesViewModel
    private lateinit var repository: CountriesRepository

    @Before
    fun before() = runTest {
        repository = mockk()

        coEvery { repository.getCountries() } returns Resource.Success(Samples.countriesList)

        viewModel = CountriesViewModel(repository)
    }

    @Test
    fun `state flow returns countries`() = runTest {
        val result = viewModel.countries.value

        assertEquals(Resource.Empty::class, result::class)
        coVerify { repository.getCountries() wasNot Called }

        viewModel.update()

        advanceUntilIdle()

        val result2 = viewModel.countries.value

        assertEquals(Resource.Success(Samples.countriesList), result2)
        coVerify { repository.getCountries() }
    }
}