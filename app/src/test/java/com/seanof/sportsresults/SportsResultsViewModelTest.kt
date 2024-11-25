package com.seanof.sportsresults

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.seanof.sportsresults.data.model.F1Result
import com.seanof.sportsresults.data.model.NbaResult
import com.seanof.sportsresults.data.model.SportsResponse
import com.seanof.sportsresults.data.model.TennisResult
import com.seanof.sportsresults.data.remote.ApiResult
import com.seanof.sportsresults.data.remote.ApiService
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class SportsResultsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService
    private lateinit var viewModel: SportsResultsViewModel

    @Before
    fun setUp() {
        apiService = mock()
        Dispatchers.setMain(UnconfinedTestDispatcher()) // Use test dispatcher for coroutines
        viewModel = SportsResultsViewModel(apiService, Dispatchers.Main)
    }

    @Test
    fun `getSportsResults should emit posts when successful`() = runTest {
        val sportsResults = SportsResponse(nbaResults = listOf(NbaResult(gameNumber = 1, loser = "Test", mvp = "Test")), tennis = listOf(
            TennisResult(loser = "Test", numberOfSets = 1)), f1Results = listOf(F1Result(seconds = 1.1)))
        `when`(apiService.getSportsResults()).thenReturn(flowOf(ApiResult.Success(sportsResults)))
        viewModel.sportsResults
        Assert.assertNotNull(ApiResult.Success(sportsResults))
    }
}