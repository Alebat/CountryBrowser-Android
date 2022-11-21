package at.aleb.countrybrowser.domain

import at.aleb.countrybrowser.data.graphql.CountriesQuery

fun CountriesQuery.Country.toEntity(): Country =
    Country(
        code,
        name,
        native,
        capital ?: "",
        emoji
    )