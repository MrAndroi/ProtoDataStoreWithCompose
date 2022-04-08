package com.yarmouk.protodatastore.presentation.info.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yarmouk.protodatastore.domain.model.Gender

@Composable
fun DropDownMenu(
    gender:Gender,
    onItemSelected: (item:Gender) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    val genders = Gender.values()
    var selectedText by remember { mutableStateOf(gender.name)  }

    LaunchedEffect(key1 = gender){
        selectedText = gender.name
    }

    Column(modifier = Modifier.clickable { expanded = !expanded }) {
        Text(
            text = selectedText,
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(5.dp))
                .padding(top= 5.dp ,bottom= 5.dp, end = 16.dp,start=16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.5f)
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        onItemSelected(gender)
                        selectedText = gender.name
                        expanded = false
                })
                {
                    Text(
                        text = gender.name,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }

}