package dev.azeredo.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


sealed class AppIcons() {
    companion object {
        val Outbound: ImageVector
            get() {
                if (_Outbound != null) {
                    return _Outbound!!
                }
                _Outbound = ImageVector.Builder(
                    name = "Outbound",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(356f, 660f)
                        lineToRelative(204f, -204f)
                        verticalLineToRelative(90f)
                        horizontalLineToRelative(80f)
                        verticalLineToRelative(-226f)
                        horizontalLineTo(414f)
                        verticalLineToRelative(80f)
                        horizontalLineToRelative(89f)
                        lineTo(300f, 603f)
                        close()
                        moveTo(480f, 880f)
                        quadToRelative(-83f, 0f, -156f, -31.5f)
                        reflectiveQuadTo(197f, 763f)
                        reflectiveQuadToRelative(-85.5f, -127f)
                        reflectiveQuadTo(80f, 480f)
                        reflectiveQuadToRelative(31.5f, -156f)
                        reflectiveQuadTo(197f, 197f)
                        reflectiveQuadToRelative(127f, -85.5f)
                        reflectiveQuadTo(480f, 80f)
                        reflectiveQuadToRelative(156f, 31.5f)
                        reflectiveQuadTo(763f, 197f)
                        reflectiveQuadToRelative(85.5f, 127f)
                        reflectiveQuadTo(880f, 480f)
                        reflectiveQuadToRelative(-31.5f, 156f)
                        reflectiveQuadTo(763f, 763f)
                        reflectiveQuadToRelative(-127f, 85.5f)
                        reflectiveQuadTo(480f, 880f)
                        moveToRelative(0f, -80f)
                        quadToRelative(134f, 0f, 227f, -93f)
                        reflectiveQuadToRelative(93f, -227f)
                        reflectiveQuadToRelative(-93f, -227f)
                        reflectiveQuadToRelative(-227f, -93f)
                        reflectiveQuadToRelative(-227f, 93f)
                        reflectiveQuadToRelative(-93f, 227f)
                        reflectiveQuadToRelative(93f, 227f)
                        reflectiveQuadToRelative(227f, 93f)
                        moveToRelative(0f, -320f)
                    }
                }.build()
                return _Outbound!!
            }

        private var _Outbound: ImageVector? = null

        val Remove: ImageVector
            get() {
                if (_Remove != null) {
                    return _Remove!!
                }
                _Remove = ImageVector.Builder(
                    name = "Remove",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(200f, 520f)
                        verticalLineToRelative(-80f)
                        horizontalLineToRelative(560f)
                        verticalLineToRelative(80f)
                        close()
                    }
                }.build()
                return _Remove!!
            }

        private var _Remove: ImageVector? = null
    }
}