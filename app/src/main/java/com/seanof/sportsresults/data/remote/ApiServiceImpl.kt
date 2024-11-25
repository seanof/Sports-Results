package com.seanof.sportsresults.data.remote

import com.seanof.sportsresults.data.model.SportsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override fun getSportsResults(): Flow<ApiResult<SportsResponse>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.post("/results"){}.body()))
        } catch (e:Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "An error occurred."))
        }
    }
}
