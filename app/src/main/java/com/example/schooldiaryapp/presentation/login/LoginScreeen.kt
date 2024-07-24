package com.example.schooldiaryapp.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.presentation.navigation.ScreenRoutes

@Composable
fun LoginScreen(
    context: Context,
    navHostController: NavHostController,
    vm : LoginScreenViewModel
) {
    val resultData = vm.loginResult.value

    var loginField = remember {
        "user1"
    }
    var passField = remember {
        "123"
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login as",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = loginField,
                onValueChange = { loginField = it }
            )
            OutlinedTextField(
                value = passField,
                onValueChange = { passField = it }
            )

            Button(
                onClick = {
                            vm.teacherLogin(username = loginField, password = passField)
                            if(resultData.loadError == ""){
                                Toast.makeText(context,"Вход", Toast.LENGTH_LONG).show()
                                navHostController.navigate(ScreenRoutes.BottomBar.route)
                            }else {
                                Toast.makeText(context,"Не получилось, Error ${resultData.loadError}", Toast.LENGTH_LONG).show()
                            }
                          },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Teacher", fontSize = 18.sp)
            }
            Button(
                onClick = {

                          },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Student", fontSize = 18.sp)
            }
        }
    }
}