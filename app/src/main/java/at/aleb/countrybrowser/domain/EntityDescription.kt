package at.aleb.countrybrowser.domain

fun CountryDetails.getDetailDescriptions() = listOf(
    DetailDescription(native, "\uD83C\uDF10", "Native name"),
    DetailDescription(capital, "\uD83D\uDCCD", "Capital"),
    DetailDescription(emoji, "\uD83C\uDFF3", "Flag"),
    DetailDescription("+$phone", "\uD83D\uDCDE", "Phone prefix"),
    DetailDescription(continent, "\uD83C\uDF0D", "Continent"),
) + (languages.mapIndexed { index, language ->
    DetailDescription(
        language.name ?: language.native ?: "",
        "\uD83D\uDCDD",
        if (languages.size == 1)
            "Language"
        else
            "Language ${index + 1}"
    )
})