package com.test.speedometer.ui.acceleration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private val ScoreOffWhite = Color(0xFFF4F1E8)
private val ScoreMutedWhite = Color(0xFFAAAEB7)
private val ScoreDeepBlue = Color(0xFF102846)
private val ScorePanelBlue = Color(0xFF17395F)
private val ScoreAccentBlue = Color(0xFF5AA9FF)

@Composable
fun AccelerationScoreCard(
    speed: Int,
    score: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ScorePanelBlue,
                        ScoreDeepBlue
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "CURRENT SPEED",
                color = ScoreMutedWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = speed.toString(),
                color = ScoreOffWhite,
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 105.sp
            )

            Text(
                text = "KM/H",
                color = ScoreAccentBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "ACCELERATION SCORE",
                color = ScoreMutedWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = String.format(Locale.US, "%.2f", score),
                color = ScoreOffWhite,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
