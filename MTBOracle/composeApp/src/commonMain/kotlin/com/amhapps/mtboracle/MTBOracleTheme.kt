package com.amhapps.mtboracle

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.alata_regular
import org.jetbrains.compose.resources.Font


@Immutable
data class ExtendedColors(
    val forest: Color,
    val forestLight: Color,
    val lightRed: Color,
    val yellowWarning:Color,
    val lightGrey: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        forest = Color(0xff5b8a6e),
        forestLight = Color(0xff88b89b),
        lightRed = Color(0xffff4747),
        yellowWarning = Color(0xffffd04e),
        lightGrey = Color(0xfff2f2f2)
    )
}

@Composable
fun mtbOracleFontFamily():FontFamily{
    return FontFamily(
        Font(Res.font.alata_regular,FontWeight.Normal)
    )
}


@Composable
fun mtbOracleTypography():Typography{
    val mtbOracleFontFamily = mtbOracleFontFamily()
    return Typography(
        body1 = TextStyle(
            fontFamily = mtbOracleFontFamily
        )
    )
}


@Composable
fun MTBOracleTheme( //Extends Material Theme
    /* ... */
    content: @Composable () -> Unit
) {
    val extendedColors = ExtendedColors(
        forest = Color(0xff5b8a6e),
        forestLight = Color(0xff88b89b),
        lightRed = Color(0xffff4747),
        yellowWarning = Color(0xffffd04e),
        lightGrey = Color(0xfff2f2f2)
    )
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            /* colors = ..., typography = ..., shapes = ... */
            content = content,
            typography = mtbOracleTypography()
        )
    }
}


object MTBOracleTheme{
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current

    val buttonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(backgroundColor = this.colors.forestLight)

}