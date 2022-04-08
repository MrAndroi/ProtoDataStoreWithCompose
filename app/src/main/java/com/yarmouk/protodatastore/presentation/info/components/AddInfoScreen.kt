package com.yarmouk.protodatastore.presentation.info.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yarmouk.protodatastore.domain.model.UserInfo
import com.yarmouk.protodatastore.presentation.info.AddInfoEvents
import com.yarmouk.protodatastore.presentation.info.InfoViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddInfoScreen(
    viewModel: InfoViewModel = hiltViewModel(),
) {

    val nameState = viewModel.userName.value
    val emailState = viewModel.userEmail.value
    val genderState = viewModel.userGender.value
    val currentUserName = viewModel.currentInfo.value.collectAsState(initial = UserInfo()).value.name
    val welcomeMessage = if(currentUserName.isNotBlank()) "Welcome, $currentUserName" else "Welcome"

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                is InfoViewModel.UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            Column {
                Text(
                    text = welcomeMessage,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h4,
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    text = nameState.text,
                    hint = nameState.hint,
                    isHintVisible = nameState.isHintVisible,
                    onValueChanged = { viewModel.onEvent(AddInfoEvents.EnteredName(it)) },
                    onFocusChanged = { viewModel.onEvent(AddInfoEvents.ChangeNameFocus(it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    text = emailState.text,
                    hint = emailState.hint,
                    isHintVisible = emailState.isHintVisible,
                    onValueChanged = { viewModel.onEvent(AddInfoEvents.EnteredEmail(it)) },
                    onFocusChanged = { viewModel.onEvent(AddInfoEvents.ChangeEmailFocus(it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                DropDownMenu(genderState){
                    viewModel.onEvent(AddInfoEvents.SelectGender(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEvent(AddInfoEvents.SaveUserInfo) })
                {
                    Text(text = "Save!")
                }
            }

        }

    }

}