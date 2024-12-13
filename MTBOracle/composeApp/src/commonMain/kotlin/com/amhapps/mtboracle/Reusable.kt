package com.amhapps.mtboracle

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun MTBOracleTextInput(  //has correct colouring, rounded corners and removed some unused TextField features
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly :Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
){
    TextField(  value= value,
                onValueChange = onValueChange,
                modifier = modifier,
                label = label,
                trailingIcon = trailingIcon,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                readOnly = readOnly,
                maxLines = maxLines,
                minLines = minLines,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MTBOracleTheme.colors.forestLight,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = MTBOracleTheme.colors.forestLight,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = MTBOracleTheme.colors.forestLight),
                shape = RoundedCornerShape(10.dp)
        )
}