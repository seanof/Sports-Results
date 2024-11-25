package com.seanof.sportsresults.data.remote

import com.seanof.sportsresults.data.model.SportsResponse
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getSportsResults(): Flow<ApiResult<SportsResponse>>
}
