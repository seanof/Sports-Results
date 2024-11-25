package com.seanof.sportsresults.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class NbaResult(
    val gameNumber: Int,
    @SerialName("looser") val loser: String,
    val mvp: String,
) : SportResult()
