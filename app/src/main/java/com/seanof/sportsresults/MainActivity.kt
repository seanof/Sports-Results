package com.seanof.sportsresults

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.seanof.sportsresults.data.model.F1Result
import com.seanof.sportsresults.data.model.NbaResult
import com.seanof.sportsresults.data.model.TennisResult
import com.seanof.sportsresults.data.remote.ApiResult
import com.seanof.sportsresults.ui.F1ResultItem
import com.seanof.sportsresults.ui.NbaResultItem
import com.seanof.sportsresults.ui.TennisResultItem
import com.seanof.sportsresults.ui.theme.PrimaryColor
import com.seanof.sportsresults.ui.theme.SecondaryColor
import com.seanof.sportsresults.ui.theme.SportsResultsTheme
import com.seanof.sportsresults.ui.theme.TertiaryColor
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsResultsTheme {
                val viewModel: SportsResultsViewModel by viewModels()
                val apiResult by viewModel.sportsResults.collectAsState()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    Text(text = stringResource(R.string.app_name),
                                        style = TextStyle(
                                            fontSize = 26.sp,
                                            color = SecondaryColor
                                        )
                                    )
                                }
                            }, colors = TopAppBarDefaults.topAppBarColors(PrimaryColor)
                        )
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        color = SecondaryColor
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 4.dp, end = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when (apiResult) {
                                is ApiResult.Idle -> {
                                    Button(
                                        modifier = Modifier.padding(50.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                                        onClick = { viewModel.fetchSportsResults() }) {
                                        Text(
                                            text = stringResource(R.string.fetch_button_text),
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                                is ApiResult.Loading -> CircularProgressIndicator(
                                    modifier = Modifier.size(100.dp),
                                    color = PrimaryColor
                                )
                                is ApiResult.Error -> Text(stringResource(R.string.generic_error_message))
                                is ApiResult.Success -> {
                                    LazyColumn(
                                        modifier = Modifier
                                            .background(color = SecondaryColor)
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentPadding = PaddingValues(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        stickyHeader(
                                            contentType = "sticky"
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .background(color = TertiaryColor)
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
                                                    .zIndex(1f),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    stringResource(R.string.results_timestamp_header) + viewModel.latestTimeStamp,
                                                    style = TextStyle(
                                                        color = Color.White,
                                                        fontSize = 20.sp,
                                                        letterSpacing = 1.2.sp
                                                    )
                                                )
                                            }
                                        }

                                        items(viewModel.getMostRecentResults(apiResult.data)) { result ->
                                            when (result) {
                                                is TennisResult -> {
                                                    TennisResultItem(result, getString(R.string.tennis_result_formatter))
                                                }

                                                is F1Result -> {
                                                    F1ResultItem(result, getString(R.string.f1_result_formatter))
                                                }

                                                is NbaResult -> {
                                                    NbaResultItem(result, getString(R.string.nba_result_formatter))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}