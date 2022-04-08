package com.yarmouk.protodatastore.presentation.info.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    text: String,
    hint:String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChanged:(String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChanged:(FocusState) -> Unit,
) {

    Box(
        modifier = modifier
            .border(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface,
            shape = RoundedCornerShape(5.dp))
            .padding(16.dp)

    ){
        BasicTextField(
            value = text,
            textStyle = MaterialTheme.typography.body2,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChanged(it) }
        )
        if(isHintVisible){
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray,
            )
        }
    }
}