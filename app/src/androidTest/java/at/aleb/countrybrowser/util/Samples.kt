package at.aleb.countrybrowser.util

import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.domain.toEntity

object Samples {

    val countriesDtoList = listOf(
        CountriesQuery.Country("IT", "Italy", "Italia", "Rome", "xyz"),
        CountriesQuery.Country("AT", "Austria", "Österreich", "Wien", "zyx"),
    )

    val countriesList = countriesDtoList.map { it.toEntity() }
}