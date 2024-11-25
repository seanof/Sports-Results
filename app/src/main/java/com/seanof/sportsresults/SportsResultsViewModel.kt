package com.seanof.sportsresults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seanof.sportsresults.data.model.SportResult
import com.seanof.sportsresults.data.model.SportsResponse
import com.seanof.sportsresults.data.model.getTimeStamp
import com.seanof.sportsresults.data.remote.ApiResult
import com.seanof.sportsresults.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SportsResultsViewModel @Inject constructor(
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _results = MutableStateFlow<ApiResult<SportsResponse>>(ApiResult.Idle())
    val sportsResults = _results.asStateFlow()
    var latestTimeStamp = ""

    fun fetchSportsResults() {
        viewModelScope.launch {
                apiService.getSportsResults()
                .flowOn(defaultDispatcher)
                .catch {
                    _results.value = ApiResult.Error(it.message ?: "An error occurred.")
                }
                .collect {
                    _results.value = it
                }
        }
    }

    fun getMostRecentResults(sportsResponse: SportsResponse?): List<SportResult> {
        val sportsItemList = ArrayList<SportResult>()
        sportsResponse?.let { sportsItemList.addAll(it.f1Results) }
        sportsResponse?.let { sportsItemList.addAll(it.nbaResults) }
        sportsResponse?.let { sportsItemList.addAll(it.tennis) }

        var latestDate = LocalDateTime.MIN
        sportsItemList.forEach {
            if (it.getTimeStamp() > latestDate) {
                latestDate = it.getTimeStamp()
                latestTimeStamp = it.getTimeStamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DISPLAY))
            }
        }

        return sportsItemList.filter {
            it.getTimeStamp().dayOfYear == latestDate.dayOfYear && it.getTimeStamp().year == latestDate.year
        }.sortedByDescending { it.getTimeStamp()}
    }

    companion object {
        const val DATE_FORMAT_DISPLAY = "yyyy-mm-dd"
    }
}