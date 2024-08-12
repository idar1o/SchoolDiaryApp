package com.example.schooldiaryapp.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooldiaryapp.data.encryptedprefs.EncryptedPrefsHelper
import com.example.schooldiaryapp.data.source.network.models.LoginRequest
import com.example.schooldiaryapp.domain.use_cases.TeacherLoginUseCase
import com.example.schooldiaryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val teacherLoginUseCase: TeacherLoginUseCase,
    private val encryptedPrefsHelper: EncryptedPrefsHelper
) : ViewModel(){
    private val _loginResult = mutableStateOf(TeacherDataState())
    val loginResult: State<TeacherDataState> = _loginResult


    fun teacherLogin(username : String, password: String){
        viewModelScope.launch {
            when(val result = teacherLoginUseCase.invoke(LoginRequest(password, username))){
                is Resource.Success -> {
                    result.data?.let { data ->
                        encryptedPrefsHelper.saveLoginData(
                            TeacherData(
                                username = data.username,
                                password = password,
                                fullname = data.fullName,
                                userId = data.userId,
                                teacherId = data.teacherId,
                                user_type = data.userType
                            )
                        )
                        _loginResult.value = _loginResult.value.copy(
                            teacherData = TeacherData(
                                username = data.username,
                                password = password,
                                fullname = data.fullName,
                                userId = data.userId,
                                teacherId = data.teacherId,
                                user_type = data.userType
                            )
                        )
                        Log.d("LOL", "encryptedPrefsHelper.saveLoginData()")
                    }

                }
                is Resource.Error -> {
                        _loginResult.value = _loginResult.value.copy(
                            loadError = result.message.toString()
                        )
                }
                is Resource.Loading -> {

                }
            }
            Log.d(
                "LOL", "Error : ${_loginResult.value.loadError}, " +
                        "Success : ${_loginResult.value.teacherData}, " +
                        "Loading : ${_loginResult.value.isLoading}"
            )
        }
    }

}