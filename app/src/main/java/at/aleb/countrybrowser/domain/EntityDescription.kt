package at.aleb.countrybrowser.domain

import java.net.URLEncoder

@Suppress("DEPRECATION") // For compatibility of encode for api 21
fun CountryDetails.getDetailDescriptions() = listOf(
    DetailDescription(native, "\uD83C\uDF10", "Native name"),
    DetailDescription(
        capital,
        "\uD83D\uDCCD",
        "Capital",
        "https://www.google.com/maps/place/${URLEncoder.encode(capital)}"
    ),
    DetailDescription(emoji, "\uD83C\uDFF3", "Flag"),
    DetailDescription("+$phone", "\uD83D\uDCDE", "Phone prefix"),
    DetailDescription(
        continent,
        "\uD83C\uDF0D",
        "Continent",
        "https://www.google.com/maps/place/${
            URLEncoder.encode(
                continent.replace(
                    "Europe",
                    "Europa"
                )
            )
        }"
    ),
) + (languages.mapIndexed { index, language ->
    (language.name ?: language.native)?.let {
        DetailDescription(
            language.name ?: language.native ?: "",
            "\uD83D\uDCDD",
            if (languages.size == 1)
                "Language"
            else
                "Language ${index + 1}",

            "https://en.wikipedia.org/wiki/${URLEncoder.encode(it)}"
        )
    }
}.filterNotNull())