package at.aleb.countrybrowser.util

import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Language

object Samples {

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