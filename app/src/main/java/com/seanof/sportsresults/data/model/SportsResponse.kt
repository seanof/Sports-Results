package com.seanof.sportsresults.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SportsResponse (
    val f1Results: List<F1Result>,
    val nbaResults: List<NbaResult>,
    @SerialName("Tennis") val tennis: List<TennisResult>)
