package com.yarmouk.protodatastore.presentation.info

import androidx.compose.ui.focus.FocusState
import com.yarmouk.protodatastore.domain.model.Gender

sealed class AddInfoEvents{
    data class EnteredName(val title: String): AddInfoEvents()
    data class ChangeNameFocus(val focusState: FocusState): AddInfoEvents()
    data class EnteredEmail(val content: String): AddInfoEvents()
    data class ChangeEmailFocus(val focusState: FocusState): AddInfoEvents()
    data class SelectGender(val gender: Gender): AddInfoEvents()
    object SaveUserInfo: AddInfoEvents()
}