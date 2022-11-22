package at.aleb.countrybrowser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.aleb.countrybrowser.data.CountryDetailsRepository
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val repository: CountryDetailsRepository
) : ViewModel() {
    fun update(code: String) = viewModelScope.launch {
        _countries.emit(Resource.LOADING())
        _countries.emit(repository.getDetails(code))
    }

    private val _countries: MutableStateFlow<Resource<CountryDetails>> =
        MutableStateFlow(Resource.EMPTY())

    val countries = _countries.asStateFlow()
}