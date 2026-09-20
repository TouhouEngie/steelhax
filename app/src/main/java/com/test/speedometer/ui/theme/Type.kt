package com.test.speedometer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.test.speedometer.R

val RajdhaniFontFamily = FontFamily(
    Font(
        resId = R.font.rajdhani_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.rajdhani_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.rajdhani_bold,
        weight = FontWeight.Bold
    )
)

private val DefaultTypography = Typography()

val Typography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(fontFamily = RajdhaniFontFamily),
    displayMedium = DefaultTypography.displayMedium.copy(fontFamily = RajdhaniFontFamily),
    displaySmall = DefaultTypography.displaySmall.copy(fontFamily = RajdhaniFontFamily),

    headlineLarge = DefaultTypography.headlineLarge.copy(fontFamily = RajdhaniFontFamily),
    headlineMedium = DefaultTypography.headlineMedium.copy(fontFamily = RajdhaniFontFamily),
    headlineSmall = DefaultTypography.headlineSmall.copy(fontFamily = RajdhaniFontFamily),

    titleLarge = DefaultTypography.titleLarge.copy(fontFamily = RajdhaniFontFamily),
    titleMedium = DefaultTypography.titleMedium.copy(fontFamily = RajdhaniFontFamily),
    titleSmall = DefaultTypography.titleSmall.copy(fontFamily = RajdhaniFontFamily),

    bodyLarge = DefaultTypography.bodyLarge.copy(fontFamily = RajdhaniFontFamily),
    bodyMedium = DefaultTypography.bodyMedium.copy(fontFamily = RajdhaniFontFamily),
    bodySmall = DefaultTypography.bodySmall.copy(fontFamily = RajdhaniFontFamily),

    labelLarge = DefaultTypography.labelLarge.copy(fontFamily = RajdhaniFontFamily),
    labelMedium = DefaultTypography.labelMedium.copy(fontFamily = RajdhaniFontFamily),
    labelSmall = DefaultTypography.labelSmall.copy(fontFamily = RajdhaniFontFamily)
)
