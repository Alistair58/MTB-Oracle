package com.amhapps.mtboracle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties

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
    val selectionColors = TextSelectionColors(
        backgroundColor = MTBOracleTheme.colors.forestLight,
        handleColor = MTBOracleTheme.colors.forestLight
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides selectionColors
    ) {
        TextField(
            value = value,
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
                cursorColor = MTBOracleTheme.colors.forestLight,
                placeholderColor = MTBOracleTheme.colors.forestLight,
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
fun CompleteDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    onDropdownClick: (String) -> Unit,
    label:String,
    modifier:Modifier,
    items:List<String>,
    iconContentDescription:String
){
    var dropDownExpanded by remember { mutableStateOf(false) }
    var dropDownSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (dropDownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(modifier = modifier){
        MTBOracleTextInput(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    dropDownSize = coordinates.size.toSize()
                }
                .clickable { dropDownExpanded = !dropDownExpanded },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(icon, iconContentDescription,
                    Modifier.clickable { dropDownExpanded = !dropDownExpanded })
            }
        )
        DropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = {
                dropDownExpanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { dropDownSize.width.toDp() })
                .heightIn(0.dp,300.dp)
            //set it to same width as input box
        ) {
            items.forEach { materialOption ->
                DropdownMenuItem(onClick = {
                    onDropdownClick(materialOption)
                    dropDownExpanded = false
                }) {
                    Text(text = materialOption)
                }
            }
        }
    }
}

@Composable
fun SearchableDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    onDropdownClick: (String) -> Unit,
    label:String,
    modifier: Modifier,
    items:List<String>,
    iconContentDescription:String
){
    var dropDownExpanded by remember { mutableStateOf(false) }
    var dropDownSize by remember { mutableStateOf(Size.Zero) }
    var currentItems by remember{ mutableStateOf(items) }
    val icon = if (dropDownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Box(modifier = modifier){
        MTBOracleTextInput(
            value = value,
            onValueChange = { newVal ->
                onValueChange(newVal)
                currentItems = items.filter {
                    val words = it.lowercase().split(" ")
                    var valid = false
                    for (word: String in words) {
                        if (word.startsWith(newVal.lowercase())) {
                            valid = true
                            dropDownExpanded = true
                            break
                        }
                    }
                    valid
                }
            },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    dropDownSize = coordinates.size.toSize()
                },
                //.clickable { dropDownExpanded = !dropDownExpanded },
            label = { Text(label) },
            readOnly = false
        )
        DropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = {
                dropDownExpanded = false
            },
            properties = PopupProperties(focusable = false, clippingEnabled = false),
            modifier = Modifier
                .width(with(LocalDensity.current) { dropDownSize.width.toDp() })
                .heightIn(0.dp,200.dp)
            //set it to same width as input box
        ) {
            currentItems.forEach { materialOption ->
                DropdownMenuItem(onClick = {
                    onDropdownClick(materialOption)
                    dropDownExpanded = false
                }) {
                    Text(text = materialOption)
                }
            }
        }
    }
}