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
) {
    val coatOfArmsUrl = "https://mainfacts.com/media/images/coats_of_arms/${code.lowercase()}.png"
}

data class Language(
    val name: String?,
    val native: String?
)

data class DetailDescription(
    val text: String,
    val emoji: String,
    val description: String
)