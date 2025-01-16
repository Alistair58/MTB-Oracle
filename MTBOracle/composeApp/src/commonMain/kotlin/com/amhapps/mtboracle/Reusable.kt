package com.amhapps.mtboracle

import BikeData
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.ui.window.Dialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.frame0
import mtboracle.composeapp.generated.resources.frame1
import mtboracle.composeapp.generated.resources.frame10
import mtboracle.composeapp.generated.resources.frame11
import mtboracle.composeapp.generated.resources.frame12
import mtboracle.composeapp.generated.resources.frame13
import mtboracle.composeapp.generated.resources.frame14
import mtboracle.composeapp.generated.resources.frame15
import mtboracle.composeapp.generated.resources.frame16
import mtboracle.composeapp.generated.resources.frame17
import mtboracle.composeapp.generated.resources.frame18
import mtboracle.composeapp.generated.resources.frame19
import mtboracle.composeapp.generated.resources.frame2
import mtboracle.composeapp.generated.resources.frame20
import mtboracle.composeapp.generated.resources.frame3
import mtboracle.composeapp.generated.resources.frame4
import mtboracle.composeapp.generated.resources.frame5
import mtboracle.composeapp.generated.resources.frame6
import mtboracle.composeapp.generated.resources.frame7
import mtboracle.composeapp.generated.resources.frame8
import mtboracle.composeapp.generated.resources.frame9
import mtboracle.composeapp.generated.resources.frame21
import mtboracle.composeapp.generated.resources.frame22
import mtboracle.composeapp.generated.resources.frame23
import mtboracle.composeapp.generated.resources.frame24
import mtboracle.composeapp.generated.resources.frame25
import mtboracle.composeapp.generated.resources.frame26
import mtboracle.composeapp.generated.resources.frame27
import mtboracle.composeapp.generated.resources.frame28
import mtboracle.composeapp.generated.resources.frame29
import mtboracle.composeapp.generated.resources.frame30
import mtboracle.composeapp.generated.resources.frame31
import mtboracle.composeapp.generated.resources.frame32
import mtboracle.composeapp.generated.resources.frame33
import mtboracle.composeapp.generated.resources.frame34
import mtboracle.composeapp.generated.resources.frame35
import mtboracle.composeapp.generated.resources.frame36
import mtboracle.composeapp.generated.resources.frame37
import mtboracle.composeapp.generated.resources.frame38
import mtboracle.composeapp.generated.resources.frame39
import mtboracle.composeapp.generated.resources.frame40
import mtboracle.composeapp.generated.resources.frame41
import mtboracle.composeapp.generated.resources.frame42
import mtboracle.composeapp.generated.resources.frame43
import mtboracle.composeapp.generated.resources.frame44
import mtboracle.composeapp.generated.resources.frame45
import mtboracle.composeapp.generated.resources.frame46
import mtboracle.composeapp.generated.resources.frame47
import mtboracle.composeapp.generated.resources.frame48
import mtboracle.composeapp.generated.resources.frame49
import mtboracle.composeapp.generated.resources.frame50
import mtboracle.composeapp.generated.resources.frame51
import mtboracle.composeapp.generated.resources.frame52
import mtboracle.composeapp.generated.resources.frame53
import mtboracle.composeapp.generated.resources.frame54
import mtboracle.composeapp.generated.resources.frame55
import mtboracle.composeapp.generated.resources.frame56
import mtboracle.composeapp.generated.resources.frame57
import mtboracle.composeapp.generated.resources.frame58
import org.jetbrains.compose.resources.painterResource

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
    onDropdownClick: (String) -> Unit,
    label:String,
    modifier:Modifier = Modifier,
    items:List<String>,
    iconContentDescription:String = "Dropdown reveal icon"
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
            onValueChange = {  },
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
    items:List<String>
){
    var dropDownExpanded by remember { mutableStateOf(false) }
    var dropDownSize by remember { mutableStateOf(Size.Zero) }
    var currentItems by remember{ mutableStateOf(items) }
//    val icon = if (dropDownExpanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
    Box(modifier = modifier){
        MTBOracleTextInput(
            value = value,
            onValueChange = { newVal ->
                onValueChange(newVal)
                currentItems = items.filter {
                    var words = it.lowercase().split(" ")
                    var valid = false
                    if(it.length >= newVal.length && it.lowercase().substring(0,newVal.length) == newVal.lowercase()){ //startsWith didn't like spaces
                        valid = true
                        dropDownExpanded = true
                        words = emptyList() //skip the next bit
                    }
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

@Composable
fun WarningDialog(
    confirmText:String = "Confirm",
    onConfirmation: () -> Unit,
    dismissText:String = "Dismiss",
    alwaysDismiss: () -> Unit, //Typically closes the dialog
    explicitDismiss: () -> Unit = {}, //Do something only on dismissButton e.g. backNavigation
    dialogTitle: String,
    dialogText: String,
    confirmExists:Boolean? = true,
    confirmationColor:Color? = null,
    dismissColor:Color,
    modifier:Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { alwaysDismiss() ; explicitDismiss() },
        confirmButton = {
            if(confirmExists == true){ //because it is nullable it actually has to be like this
                confirmationColor?.let { Modifier.background(color= it) }?.let {
                    TextButton(
                        onClick = {
                            onConfirmation()
                            alwaysDismiss()
                        },
                        modifier = it
                    ) {
                        Text(confirmText,color=Color.White)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                     alwaysDismiss() ; explicitDismiss()
                },
                modifier = Modifier
                    .background(color=dismissColor)
            ) {
                Text(dismissText,color=Color.White)
            }
        },
        modifier = modifier
    )
}

@Composable
fun SpecText(specName:String,specValue:String,fontSize: TextUnit?=18.sp){
    Text(
        text = buildAnnotatedString {
            append(specName)
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize =fontSize?:18.sp,
                )
            ) {
                append(specValue)
            }
        },
        fontSize = 18.sp,
        modifier = Modifier.padding(0.dp,5.dp)
    )
}

@Composable
fun BikeInputDisplay(bikeData:BikeData){
    val brandFontSize = if(bikeData.brand.length>15) 14.sp else 18.sp
    val brandOutput = if(bikeData.brand.length>25) bikeData.brand.subSequence(0,25) else {
        if (bikeData.brand.length > 12) {
            "\n" + bikeData.brand
        } else {
            bikeData.brand
        }
    }
    val modelFontSize = if(bikeData.model.length>15) 14.sp else 18.sp
    val modelOutput = if(bikeData.model.length>25) bikeData.model.subSequence(0,25) else{
        if(bikeData.model.length>12){
            "\n"+bikeData.model
        }
        else{
            bikeData.model
        }

    }
    val countryFontSize = if(bikeData.country.length>15) 14.sp else 18.sp
    val countryOutput = if(bikeData.country.length>25) bikeData.country.subSequence(0,25) else{
        if(bikeData.country.length>12){
            "\n"+bikeData.country
        }
        else{
            bikeData.country
        }

    }
    val yearOutput = if(bikeData.year in 0..2999) bikeData.year.toString() else ""

    val frontSusOutput = if(bikeData.frontTravel >=0 && bikeData.frontTravel<1000) bikeData.frontTravel.toString()+"mm" else ""
    val rearSusOutput = if(bikeData.rearTravel >=0 && bikeData.rearTravel<1000) bikeData.rearTravel.toString()+"mm" else ""

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            "Based on these inputs:",
            fontSize = 25.sp,
            modifier = Modifier
                .padding(0.dp, 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ){
                SpecText("Brand: ",brandOutput.toString(),brandFontSize)
                SpecText("Model: ",modelOutput.toString(),modelFontSize)
                SpecText("Year: ",yearOutput)
                SpecText("Country: ", countryOutput.toString(),countryFontSize)
                SpecText("Category: ",bikeData.category)
                SpecText("Condition: ",bikeData.condition)
                SpecText("Size: ",bikeData.size)

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ){
                SpecText("Wheel Size: ",bikeData.wheelSize)
                SpecText("Material: ",bikeData.material)
                SpecText("Front Travel: ",frontSusOutput,16.sp)
                SpecText("Rear Travel: ",rearSusOutput,16.sp)
            }
        }
    }
}

@Composable
fun DropdownText(title:String, titleFontSize:TextUnit=16.sp, body:String, bodyFontSize:TextUnit=12.sp,iconContentDescription:String = "Dropdown text reveal icon"){
    var textShowing by remember { mutableStateOf(false) }
    val icon = if (textShowing)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(text=title, fontSize = titleFontSize)
        Button(onClick = {textShowing = !textShowing},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Gray,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = Color.Gray
            ),
            border = BorderStroke(0.dp,Color.Transparent),
            elevation = null //Causes a grey shadow
        ) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(icon, contentDescription = iconContentDescription, modifier = Modifier.width(20.dp).height(20.dp))
                }
            }
    }
    if(textShowing){
        Text(text=body, fontSize = bodyFontSize)
    }


}

@Composable
fun LoadingAnimation(){
    val scope = rememberCoroutineScope()
    var count by remember{mutableStateOf(0)}
    LaunchedEffect(true) {
        scope.launch {
            while(true){
                delay(20) //33 is 30 fps
                count = (count+1) %59
            }

        }
    }
    Dialog(onDismissRequest = {}){ //Don't want the user to be able to cancel the animation
        Card(/*modifier = Modifier
            .background(Color.White),
            shape = RoundedCornerShape(10)*/
        ){
            AnimatedContent(
                targetState = count,
                label = "Loading Animation",
                transitionSpec = {
                    (fadeIn(initialAlpha = 0.8f, animationSpec = tween(15)) +
                            scaleIn(initialScale = 1f, animationSpec = tween(0)))
                            .togetherWith(fadeOut(animationSpec = tween(15), targetAlpha = 0.8f))
                                 },
            ) { targetCount ->
                Image(
                    painter = painterResource(
                        when (targetCount) {
                            0 -> Res.drawable.frame0
                            1 -> Res.drawable.frame1
                            2 -> Res.drawable.frame2
                            3 -> Res.drawable.frame3
                            4 -> Res.drawable.frame4
                            5 -> Res.drawable.frame5
                            6 -> Res.drawable.frame6
                            7 -> Res.drawable.frame7
                            8 -> Res.drawable.frame8
                            9 -> Res.drawable.frame9
                            10 -> Res.drawable.frame10
                            11 -> Res.drawable.frame11
                            12 -> Res.drawable.frame12
                            13 -> Res.drawable.frame13
                            14 -> Res.drawable.frame14
                            15 -> Res.drawable.frame15
                            16 -> Res.drawable.frame16
                            17 -> Res.drawable.frame17
                            18 -> Res.drawable.frame18
                            19 -> Res.drawable.frame19
                            20 -> Res.drawable.frame20
                            21 -> Res.drawable.frame21
                            22 -> Res.drawable.frame22
                            23 -> Res.drawable.frame23
                            24 -> Res.drawable.frame24
                            25 -> Res.drawable.frame25
                            26 -> Res.drawable.frame26
                            27 -> Res.drawable.frame27
                            28 -> Res.drawable.frame28
                            29 -> Res.drawable.frame29
                            30 -> Res.drawable.frame30
                            31 -> Res.drawable.frame31
                            32 -> Res.drawable.frame32
                            33 -> Res.drawable.frame33
                            34 -> Res.drawable.frame34
                            35 -> Res.drawable.frame35
                            36 -> Res.drawable.frame36
                            37 -> Res.drawable.frame37
                            38 -> Res.drawable.frame38
                            39 -> Res.drawable.frame39
                            40 -> Res.drawable.frame40
                            41 -> Res.drawable.frame41
                            42 -> Res.drawable.frame42
                            43 -> Res.drawable.frame43
                            44 -> Res.drawable.frame44
                            45 -> Res.drawable.frame45
                            46 -> Res.drawable.frame46
                            47 -> Res.drawable.frame47
                            48 -> Res.drawable.frame48
                            49 -> Res.drawable.frame49
                            50 -> Res.drawable.frame50
                            51 -> Res.drawable.frame51
                            52 -> Res.drawable.frame52
                            53 -> Res.drawable.frame53
                            54 -> Res.drawable.frame54
                            55 -> Res.drawable.frame55
                            56 -> Res.drawable.frame56
                            57 -> Res.drawable.frame57
                            58 -> Res.drawable.frame58
                            else -> Res.drawable.frame0
                        }
                    ),
                    contentDescription = "Loading animation"
                )
            }
        }
    }

}