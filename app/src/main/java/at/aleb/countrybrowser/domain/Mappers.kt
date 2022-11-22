package at.aleb.countrybrowser.domain

import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.data.graphql.CountryDetailQuery

fun CountriesQuery.Country.toEntity(): Country =
    Country(
        code,
        name,
        native,
        capital ?: "",
        emoji
    )

fun CountryDetailQuery.Country.toEntity(): CountryDetails =
    CountryDetails(
        code,
        name,
        native,
        capital ?: "",
        emoji,
        phone,
        continent.name,
        languages.map { Language(it.name, it.native) }
    )
