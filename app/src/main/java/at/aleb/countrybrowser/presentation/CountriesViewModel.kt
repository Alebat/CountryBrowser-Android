package at.aleb.countrybrowser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.aleb.countrybrowser.data.CountriesRepository
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val repository: CountriesRepository
) : ViewModel() {
    fun update() = viewModelScope.launch {
        _countries.emit(Resource.Loading())
        _countries.emit(repository.getCountries())
    }

    private val _countries: MutableStateFlow<Resource<List<Country>>> =
        MutableStateFlow(Resource.Empty())

    val countries = _countries.asStateFlow()
}