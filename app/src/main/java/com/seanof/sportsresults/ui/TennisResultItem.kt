package com.seanof.sportsresults.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.seanof.sportsresults.R
import com.seanof.sportsresults.data.model.TennisResult
import com.seanof.sportsresults.ui.theme.BodyTextSize
import com.seanof.sportsresults.ui.theme.IconHeightDp
import com.seanof.sportsresults.ui.theme.PrimaryColor
import com.seanof.sportsresults.ui.theme.TertiaryColor

@Composable
fun TennisResultItem(result: TennisResult, textFormatter: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()) {
        Icon(
            painter = painterResource(id = R.drawable.raquet),
            contentDescription = "racket icon",
            tint = PrimaryColor,
            modifier = Modifier
                .height(IconHeightDp)
                .wrapContentWidth()
                .padding(end = 10.dp)
        )
        Text(
            text = String.format(
                textFormatter,
                result.tournament,
                result.winner.trim(),
                result.loser.trim(),
                result.numberOfSets.toString()
            ),
            style = TextStyle(
                color = TertiaryColor,
                fontSize = BodyTextSize,
                lineHeight = 1.3.em
            )
        )
    }

}
