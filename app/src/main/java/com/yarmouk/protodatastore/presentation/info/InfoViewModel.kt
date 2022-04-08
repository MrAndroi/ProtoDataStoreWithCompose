package com.yarmouk.protodatastore.presentation.info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarmouk.protodatastore.domain.model.Gender
import com.yarmouk.protodatastore.domain.model.InvalidInfoException
import com.yarmouk.protodatastore.domain.model.UserInfo
import com.yarmouk.protodatastore.domain.use_cases.GetUserInfo
import com.yarmouk.protodatastore.domain.use_cases.InsertUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    getUserInfoUSeCase:GetUserInfo,
    private val insertUserInfoUseCase:InsertUserInfo,
):ViewModel() {


    private val _currentInfo = mutableStateOf(getUserInfoUSeCase.invoke())
    val currentInfo: State<Flow<UserInfo>> = _currentInfo

    private val _userName = mutableStateOf(CustomTextFieldSates(
        hint = "Please enter your name "
    ))
    val userName: State<CustomTextFieldSates> = _userName


    private val _userEmail = mutableStateOf(CustomTextFieldSates(
        hint = "Please enter you email"
    ))
    val userEmail: State<CustomTextFieldSates> = _userEmail

    private val _userGender = mutableStateOf(Gender.NONE)
    val userGender:State<Gender> = _userGender

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        collectUserInfo()
    }

    private fun collectUserInfo(){
        viewModelScope.launch {
            currentInfo.value.collectLatest {
                _userName.value.text = it.name
                _userName.value.isHintVisible = it.name.isBlank()
                _userEmail.value.text = it.email
                _userEmail.value.isHintVisible= it.email.isBlank()
                _userGender.value = it.gender
            }
        }
    }


    fun onEvent(event: AddInfoEvents){
        when(event){
            is AddInfoEvents.EnteredName -> {
                _userName.value = _userName.value.copy(
                    text = event.title
                )
            }
            is AddInfoEvents.ChangeNameFocus -> {
                _userName.value = _userName.value.copy(
                    isHintVisible = !event.focusState.isFocused && _userName.value.text.isBlank()
                )
            }
            is AddInfoEvents.EnteredEmail -> {
                _userEmail.value = _userEmail.value.copy(
                    text = event.content
                )
            }
            is AddInfoEvents.ChangeEmailFocus -> {
                _userEmail.value = _userEmail.value.copy(
                    isHintVisible = !event.focusState.isFocused && _userEmail.value.text.isBlank()
                )
            }
            is AddInfoEvents.SelectGender ->{
                _userGender.value = event.gender
            }
            is AddInfoEvents.SaveUserInfo -> {
                viewModelScope.launch {
                    try {
                        insertUserInfoUseCase.invoke(
                            name = userName.value.text,
                            email = userEmail.value.text,
                            gender = userGender.value
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar("Info saved successfully !"))
                    }
                    catch (e:InvalidInfoException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "Unknown error"))
                    }
                }

            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
    }


}