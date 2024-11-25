package com.seanof.sportsresults.data.model

import com.seanof.sportsresults.data.model.SportResult.Companion.TIMESTAMP_FOR_FORMATTER
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@kotlinx.serialization.Serializable
open class SportResult {
    val publicationDate: String = ""
    val winner: String = ""
    val tournament: String = ""

    companion object {
        const val TIMESTAMP_FOR_FORMATTER = "MMM d, yyyy h:mm:ss a"
    }
}

fun SportResult.getTimeStamp(): LocalDateTime {
    return LocalDateTime.parse(publicationDate, DateTimeFormatter.ofPattern(TIMESTAMP_FOR_FORMATTER))
}
