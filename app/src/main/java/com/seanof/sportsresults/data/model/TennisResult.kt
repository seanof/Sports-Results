package com.seanof.sportsresults.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class TennisResult(
    @SerialName("looser") val loser: String,
    val numberOfSets: Int,
) : SportResult()
