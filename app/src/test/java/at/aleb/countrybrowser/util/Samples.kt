package at.aleb.countrybrowser.util

import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.data.graphql.CountryDetailQuery
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Language

object Samples {

    val countriesDtoList = listOf(
        CountriesQuery.Country(
            "IT",
            "Italy",
            "Italia",
            "Rome",
            "xyz"
        ),
        CountriesQuery.Country(
            "AT",
            "Austria",
            "Österreich",
            "Wien",
            "zyx"
        ),
    )

    val countriesList = listOf(
        Country(
            "IT",
            "Italy",
            "Italia",
            "Rome",
            "xyz"
        ),
        Country(
            "AT",
            "Austria",
            "Österreich",
            "Wien",
            "zyx"
        ),
    )

    val countryDetailDto =
        CountryDetailQuery.Country(
            "IT",
            "Italy",
            "Italia",
            "Rome",
            "xyz",
            "39",
            CountryDetailQuery.Continent("Europe"),
            listOf(CountryDetailQuery.Language("Italian", "Italiano"))
        )

    val countryDetail = CountryDetails(
        "IT",
        "Italy",
        "Italia",
        "Rome",
        "xyz",
        "39",
        "Europe",
        listOf(Language("Italian", "Italiano"))
    )
}