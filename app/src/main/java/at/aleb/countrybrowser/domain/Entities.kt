package at.aleb.countrybrowser.domain

data class Country(
    val code: String,
    val name: String,
    val native: String,
    val capital: String,
    val emoji: String,
)

data class CountryDetails(
    val code: String,
    val name: String,
    val native: String,
    val capital: String,
    val emoji: String,
    val phone: String,
    val continent: String,
    val languages: List<Language>,
)

data class Language(
    val name: String?,
    val native: String?
)
