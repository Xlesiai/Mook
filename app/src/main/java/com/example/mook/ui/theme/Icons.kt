package com.example.mook.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

class CustomIcons {
    companion object {
        @Composable
        fun gridView(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "grid_view",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(7.875f, 18.667f)
                        quadToRelative(-1.083f, 0f, -1.854f, -0.771f)
                        quadToRelative(-0.771f, -0.771f, -0.771f, -1.854f)
                        verticalLineTo(7.875f)
                        quadToRelative(0f, -1.083f, 0.771f, -1.854f)
                        quadToRelative(0.771f, -0.771f, 1.854f, -0.771f)
                        horizontalLineToRelative(8.167f)
                        quadToRelative(1.083f, 0f, 1.875f, 0.771f)
                        quadToRelative(0.791f, 0.771f, 0.791f, 1.854f)
                        verticalLineToRelative(8.167f)
                        quadToRelative(0f, 1.083f, -0.791f, 1.854f)
                        quadToRelative(-0.792f, 0.771f, -1.875f, 0.771f)
                        close()
                        moveToRelative(0f, 16.083f)
                        quadToRelative(-1.083f, 0f, -1.854f, -0.771f)
                        quadToRelative(-0.771f, -0.771f, -0.771f, -1.854f)
                        verticalLineToRelative(-8.167f)
                        quadToRelative(0f, -1.083f, 0.771f, -1.875f)
                        quadToRelative(0.771f, -0.791f, 1.854f, -0.791f)
                        horizontalLineToRelative(8.167f)
                        quadToRelative(1.083f, 0f, 1.875f, 0.791f)
                        quadToRelative(0.791f, 0.792f, 0.791f, 1.875f)
                        verticalLineToRelative(8.167f)
                        quadToRelative(0f, 1.083f, -0.791f, 1.854f)
                        quadToRelative(-0.792f, 0.771f, -1.875f, 0.771f)
                        close()
                        moveToRelative(16.083f, -16.083f)
                        quadToRelative(-1.083f, 0f, -1.854f, -0.771f)
                        quadToRelative(-0.771f, -0.771f, -0.771f, -1.854f)
                        verticalLineTo(7.875f)
                        quadToRelative(0f, -1.083f, 0.771f, -1.854f)
                        quadToRelative(0.771f, -0.771f, 1.854f, -0.771f)
                        horizontalLineToRelative(8.167f)
                        quadToRelative(1.083f, 0f, 1.854f, 0.771f)
                        quadToRelative(0.771f, 0.771f, 0.771f, 1.854f)
                        verticalLineToRelative(8.167f)
                        quadToRelative(0f, 1.083f, -0.771f, 1.854f)
                        quadToRelative(-0.771f, 0.771f, -1.854f, 0.771f)
                        close()
                        moveToRelative(0f, 16.083f)
                        quadToRelative(-1.083f, 0f, -1.854f, -0.771f)
                        quadToRelative(-0.771f, -0.771f, -0.771f, -1.854f)
                        verticalLineToRelative(-8.167f)
                        quadToRelative(0f, -1.083f, 0.771f, -1.875f)
                        quadToRelative(0.771f, -0.791f, 1.854f, -0.791f)
                        horizontalLineToRelative(8.167f)
                        quadToRelative(1.083f, 0f, 1.854f, 0.791f)
                        quadToRelative(0.771f, 0.792f, 0.771f, 1.875f)
                        verticalLineToRelative(8.167f)
                        quadToRelative(0f, 1.083f, -0.771f, 1.854f)
                        quadToRelative(-0.771f, 0.771f, -1.854f, 0.771f)
                        close()
                        moveTo(7.875f, 16.042f)
                        horizontalLineToRelative(8.167f)
                        verticalLineTo(7.875f)
                        horizontalLineTo(7.875f)
                        close()
                        moveToRelative(16.083f, 0f)
                        horizontalLineToRelative(8.167f)
                        verticalLineTo(7.875f)
                        horizontalLineToRelative(-8.167f)
                        close()
                        moveToRelative(0f, 16.083f)
                        horizontalLineToRelative(8.167f)
                        verticalLineToRelative(-8.167f)
                        horizontalLineToRelative(-8.167f)
                        close()
                        moveToRelative(-16.083f, 0f)
                        horizontalLineToRelative(8.167f)
                        verticalLineToRelative(-8.167f)
                        horizontalLineTo(7.875f)
                        close()
                        moveToRelative(16.083f, -16.083f)
                        close()
                        moveToRelative(0f, 7.916f)
                        close()
                        moveToRelative(-7.916f, 0f)
                        close()
                        moveToRelative(0f, -7.916f)
                        close()
                    }
                }.build()
            }
        }

        @Composable
        fun pause(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "pause",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(24.5f, 31.458f)
                        quadToRelative(-1.083f, 0f, -1.854f, -0.791f)
                        quadToRelative(-0.771f, -0.792f, -0.771f, -1.875f)
                        verticalLineTo(11.208f)
                        quadToRelative(0f, -1.083f, 0.771f, -1.875f)
                        quadToRelative(0.771f, -0.791f, 1.854f, -0.791f)
                        horizontalLineToRelative(4.292f)
                        quadToRelative(1.083f, 0f, 1.875f, 0.791f)
                        quadToRelative(0.791f, 0.792f, 0.791f, 1.875f)
                        verticalLineToRelative(17.584f)
                        quadToRelative(0f, 1.083f, -0.791f, 1.875f)
                        quadToRelative(-0.792f, 0.791f, -1.875f, 0.791f)
                        close()
                        moveToRelative(-13.292f, 0f)
                        quadToRelative(-1.083f, 0f, -1.875f, -0.791f)
                        quadToRelative(-0.791f, -0.792f, -0.791f, -1.875f)
                        verticalLineTo(11.208f)
                        quadToRelative(0f, -1.083f, 0.791f, -1.875f)
                        quadToRelative(0.792f, -0.791f, 1.875f, -0.791f)
                        horizontalLineTo(15.5f)
                        quadToRelative(1.083f, 0f, 1.854f, 0.791f)
                        quadToRelative(0.771f, 0.792f, 0.771f, 1.875f)
                        verticalLineToRelative(17.584f)
                        quadToRelative(0f, 1.083f, -0.771f, 1.875f)
                        quadToRelative(-0.771f, 0.791f, -1.854f, 0.791f)
                        close()
                        moveTo(24.5f, 28.792f)
                        horizontalLineToRelative(4.292f)
                        verticalLineTo(11.208f)
                        horizontalLineTo(24.5f)
                        close()
                        moveToRelative(-13.292f, 0f)
                        horizontalLineTo(15.5f)
                        verticalLineTo(11.208f)
                        horizontalLineToRelative(-4.292f)
                        close()
                        moveToRelative(0f, -17.584f)
                        verticalLineToRelative(17.584f)
                        close()
                        moveToRelative(13.292f, 0f)
                        verticalLineToRelative(17.584f)
                        close()
                    }
                }.build()
            }
        }

        @Composable
        fun replay_30(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "replay_30",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.083f, 26.833f)
                        quadToRelative(-0.416f, 0f, -0.729f, -0.312f)
                        quadToRelative(-0.312f, -0.313f, -0.312f, -0.771f)
                        reflectiveQuadToRelative(0.312f, -0.771f)
                        quadToRelative(0.313f, -0.312f, 0.729f, -0.312f)
                        horizontalLineToRelative(3.709f)
                        verticalLineToRelative(-2.042f)
                        horizontalLineToRelative(-2.25f)
                        quadToRelative(-0.375f, 0f, -0.646f, -0.292f)
                        quadToRelative(-0.271f, -0.291f, -0.271f, -0.666f)
                        quadToRelative(0f, -0.417f, 0.271f, -0.709f)
                        quadToRelative(0.271f, -0.291f, 0.687f, -0.291f)
                        horizontalLineToRelative(2.209f)
                        verticalLineToRelative(-2.084f)
                        horizontalLineToRelative(-3.709f)
                        quadToRelative(-0.458f, 0f, -0.75f, -0.312f)
                        quadToRelative(-0.291f, -0.313f, -0.291f, -0.771f)
                        quadToRelative(0f, -0.417f, 0.312f, -0.75f)
                        quadToRelative(0.313f, -0.333f, 0.771f, -0.333f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.792f, 0f, 1.292f, 0.521f)
                        quadToRelative(0.5f, 0.52f, 0.5f, 1.27f)
                        verticalLineToRelative(6.834f)
                        quadToRelative(0f, 0.75f, -0.521f, 1.27f)
                        quadToRelative(-0.521f, 0.521f, -1.271f, 0.521f)
                        close()
                        moveToRelative(9.792f, 0f)
                        quadToRelative(-0.75f, 0f, -1.25f, -0.521f)
                        quadToRelative(-0.5f, -0.52f, -0.5f, -1.27f)
                        verticalLineToRelative(-6.834f)
                        quadToRelative(0f, -0.75f, 0.5f, -1.27f)
                        quadToRelative(0.5f, -0.521f, 1.25f, -0.521f)
                        horizontalLineToRelative(3.333f)
                        quadToRelative(0.75f, 0f, 1.271f, 0.521f)
                        quadToRelative(0.521f, 0.52f, 0.521f, 1.27f)
                        verticalLineToRelative(6.834f)
                        quadToRelative(0f, 0.75f, -0.521f, 1.27f)
                        quadToRelative(-0.521f, 0.521f, -1.271f, 0.521f)
                        close()
                        moveToRelative(0.375f, -2.166f)
                        horizontalLineToRelative(2.625f)
                        verticalLineToRelative(-6.084f)
                        horizontalLineTo(23.25f)
                        verticalLineToRelative(6.084f)
                        close()
                        moveTo(20f, 36.375f)
                        quadToRelative(-5.833f, 0f, -10.021f, -3.854f)
                        quadToRelative(-4.187f, -3.854f, -4.687f, -9.563f)
                        quadToRelative(-0.042f, -0.541f, 0.333f, -0.937f)
                        reflectiveQuadToRelative(0.958f, -0.396f)
                        quadToRelative(0.5f, 0f, 0.896f, 0.375f)
                        reflectiveQuadToRelative(0.479f, 0.958f)
                        quadToRelative(0.459f, 4.584f, 3.896f, 7.688f)
                        quadTo(15.292f, 33.75f, 20f, 33.75f)
                        quadToRelative(5.042f, 0f, 8.583f, -3.521f)
                        quadToRelative(3.542f, -3.521f, 3.542f, -8.562f)
                        quadToRelative(0f, -5.084f, -3.479f, -8.604f)
                        quadToRelative(-3.479f, -3.521f, -8.521f, -3.521f)
                        horizontalLineToRelative(-0.708f)
                        lineToRelative(1.958f, 1.958f)
                        quadToRelative(0.417f, 0.417f, 0.396f, 0.938f)
                        quadToRelative(-0.021f, 0.52f, -0.396f, 0.895f)
                        quadToRelative(-0.417f, 0.417f, -0.937f, 0.417f)
                        quadToRelative(-0.521f, 0f, -0.896f, -0.417f)
                        lineToRelative(-4.292f, -4.25f)
                        quadToRelative(-0.208f, -0.208f, -0.292f, -0.437f)
                        quadToRelative(-0.083f, -0.229f, -0.083f, -0.479f)
                        quadToRelative(0f, -0.25f, 0.083f, -0.479f)
                        quadToRelative(0.084f, -0.23f, 0.292f, -0.438f)
                        lineToRelative(4.292f, -4.292f)
                        quadToRelative(0.375f, -0.375f, 0.916f, -0.375f)
                        quadToRelative(0.542f, 0f, 0.917f, 0.375f)
                        reflectiveQuadToRelative(0.375f, 0.917f)
                        quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                        lineTo(19.25f, 6.917f)
                        horizontalLineTo(20f)
                        quadToRelative(3.083f, 0f, 5.771f, 1.146f)
                        quadToRelative(2.687f, 1.145f, 4.667f, 3.145f)
                        quadToRelative(1.979f, 2f, 3.145f, 4.688f)
                        quadToRelative(1.167f, 2.687f, 1.167f, 5.729f)
                        quadToRelative(0f, 3.083f, -1.146f, 5.771f)
                        quadToRelative(-1.146f, 2.687f, -3.146f, 4.687f)
                        quadToRelative(-2f, 2f, -4.687f, 3.146f)
                        quadToRelative(-2.688f, 1.146f, -5.771f, 1.146f)
                        close()
                    }
                }.build()
            }
        }

        @Composable
        fun forward_30(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "forward_30",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.083f, 26.833f)
                        quadToRelative(-0.416f, 0f, -0.729f, -0.291f)
                        quadToRelative(-0.312f, -0.292f, -0.312f, -0.75f)
                        quadToRelative(0f, -0.459f, 0.312f, -0.771f)
                        quadToRelative(0.313f, -0.313f, 0.729f, -0.313f)
                        horizontalLineToRelative(3.709f)
                        verticalLineToRelative(-2.083f)
                        horizontalLineToRelative(-2.25f)
                        quadToRelative(-0.375f, 0f, -0.646f, -0.292f)
                        quadToRelative(-0.271f, -0.291f, -0.271f, -0.708f)
                        reflectiveQuadToRelative(0.271f, -0.667f)
                        quadToRelative(0.271f, -0.25f, 0.687f, -0.25f)
                        horizontalLineToRelative(2.209f)
                        verticalLineToRelative(-2.166f)
                        horizontalLineToRelative(-3.709f)
                        quadToRelative(-0.458f, 0f, -0.75f, -0.292f)
                        quadToRelative(-0.291f, -0.292f, -0.291f, -0.75f)
                        reflectiveQuadToRelative(0.312f, -0.771f)
                        quadToRelative(0.313f, -0.312f, 0.771f, -0.312f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.792f, 0f, 1.292f, 0.541f)
                        quadToRelative(0.5f, 0.542f, 0.5f, 1.25f)
                        verticalLineToRelative(6.875f)
                        quadToRelative(0f, 0.75f, -0.521f, 1.25f)
                        reflectiveQuadToRelative(-1.271f, 0.5f)
                        close()
                        moveToRelative(9.792f, 0f)
                        quadToRelative(-0.75f, 0f, -1.25f, -0.5f)
                        reflectiveQuadToRelative(-0.5f, -1.25f)
                        verticalLineToRelative(-6.875f)
                        quadToRelative(0f, -0.708f, 0.5f, -1.25f)
                        quadToRelative(0.5f, -0.541f, 1.25f, -0.541f)
                        horizontalLineToRelative(3.333f)
                        quadToRelative(0.75f, 0f, 1.271f, 0.521f)
                        quadToRelative(0.521f, 0.52f, 0.521f, 1.27f)
                        verticalLineToRelative(6.917f)
                        quadToRelative(0f, 0.708f, -0.521f, 1.208f)
                        reflectiveQuadToRelative(-1.271f, 0.5f)
                        close()
                        moveToRelative(0.375f, -2.125f)
                        horizontalLineToRelative(2.625f)
                        verticalLineToRelative(-6.166f)
                        horizontalLineTo(23.25f)
                        verticalLineToRelative(6.166f)
                        close()
                        moveTo(20f, 36.375f)
                        quadToRelative(-3.083f, 0f, -5.771f, -1.146f)
                        quadToRelative(-2.687f, -1.146f, -4.687f, -3.146f)
                        quadToRelative(-2f, -2f, -3.146f, -4.687f)
                        quadToRelative(-1.146f, -2.688f, -1.146f, -5.771f)
                        quadToRelative(0f, -3.042f, 1.167f, -5.729f)
                        quadToRelative(1.166f, -2.688f, 3.145f, -4.688f)
                        quadToRelative(1.98f, -2f, 4.667f, -3.145f)
                        quadTo(16.917f, 6.917f, 20f, 6.917f)
                        horizontalLineToRelative(0.708f)
                        lineToRelative(-2.125f, -2.125f)
                        quadToRelative(-0.375f, -0.375f, -0.375f, -0.917f)
                        reflectiveQuadToRelative(0.375f, -0.917f)
                        quadToRelative(0.375f, -0.375f, 0.917f, -0.375f)
                        reflectiveQuadToRelative(0.917f, 0.375f)
                        lineToRelative(4.291f, 4.292f)
                        quadToRelative(0.167f, 0.208f, 0.271f, 0.438f)
                        quadToRelative(0.104f, 0.229f, 0.104f, 0.479f)
                        quadToRelative(0f, 0.25f, -0.104f, 0.479f)
                        quadToRelative(-0.104f, 0.229f, -0.271f, 0.437f)
                        lineToRelative(-4.291f, 4.292f)
                        quadToRelative(-0.417f, 0.375f, -0.917f, 0.375f)
                        reflectiveQuadToRelative(-0.875f, -0.375f)
                        quadToRelative(-0.417f, -0.375f, -0.417f, -0.917f)
                        quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                        lineToRelative(2f, -2f)
                        horizontalLineTo(20f)
                        quadToRelative(-5.042f, 0f, -8.583f, 3.521f)
                        quadToRelative(-3.542f, 3.52f, -3.542f, 8.562f)
                        quadToRelative(0f, 5.083f, 3.542f, 8.604f)
                        quadTo(14.958f, 33.75f, 20f, 33.75f)
                        quadToRelative(4.708f, 0f, 8.146f, -3.104f)
                        quadToRelative(3.437f, -3.104f, 3.937f, -7.688f)
                        quadToRelative(0.042f, -0.583f, 0.417f, -0.958f)
                        reflectiveQuadToRelative(0.917f, -0.375f)
                        quadToRelative(0.541f, 0f, 0.937f, 0.396f)
                        reflectiveQuadToRelative(0.354f, 0.937f)
                        quadToRelative(-0.5f, 5.709f, -4.687f, 9.563f)
                        quadTo(25.833f, 36.375f, 20f, 36.375f)
                        close()
                    }
                }.build()
            }
        }
    }

}