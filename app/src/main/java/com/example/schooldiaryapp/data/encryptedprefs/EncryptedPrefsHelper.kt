package com.example.schooldiaryapp.data.encryptedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.schooldiaryapp.presentation.login.TeacherData
import javax.inject.Inject

class EncryptedPrefsHelper @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveLoginData(loginResponse: TeacherData) {
        with(sharedPreferences.edit()) {
            putInt("userID", loginResponse.userId)
            putInt("teacherID", loginResponse.teacherId)
            putString("username", loginResponse.username)
            putString("fullname", loginResponse.fullname)
            putString("userType", loginResponse.user_type)
            putString("password", loginResponse.password)
            apply()
        }
    }

    fun getLoginData(): TeacherData? {
        val userID = sharedPreferences.getInt("userID", -1)
        val teacherID = sharedPreferences.getInt("teacherID", -1)
        val username = sharedPreferences.getString("username", null)
        val fullname = sharedPreferences.getString("fullname", null)
        val userType = sharedPreferences.getString("userType", null)
        val password = sharedPreferences.getString("password", null)

        return if (userID != -1 && teacherID!=-1 && username != null && fullname != null && userType != null && password != null) {
            TeacherData(userId = userID, teacherId = teacherID, username = username, fullname = fullname, user_type =  userType, password = password)
        } else {
            null
        }
    }
}
