package com.example.schooldiaryapp.presentation.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schooldiaryapp.ui.theme.ActiveBgColor
import com.example.schooldiaryapp.ui.theme.AppBarBgColor


val TOP_BAR_HEIGHT = 56.dp
@Composable
fun TopClassBar(
    vm: TopClassesBarViewModel
){
    val classNames =  vm.classList.value
    val defaultValue = if (classNames.classList.isNotEmpty()) 0 else -10
    Log.d("LOL", "defaultValue = $defaultValue")
    var selectedClass by rememberSaveable { mutableStateOf(defaultValue) }
    LaunchedEffect(selectedClass) {
        if (selectedClass != -10) {
            vm.selectItem(classNames.classList[selectedClass].classId)
        }
    }
    TopAppBar (
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = TOP_BAR_HEIGHT)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
//            .height(height = if (listState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
        backgroundColor = AppBarBgColor
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            classNames.classList.forEachIndexed { index, itclass ->
                val isSelected = index == selectedClass
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = if (isSelected) ActiveBgColor else AppBarBgColor
                        )
                        .clickable {
                            selectedClass = index
                        }

                ) {
                    Text(
                        text = itclass.className,
                        color = Color.White,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

            }
        }

    }

}

@Composable
fun ErrorContent(exception: Throwable?) {
    Log.d("LOL", "ErrorContent: ${ exception?.message }")
}

@Composable
fun CircleProgress(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            strokeWidth = 5.dp,
            modifier = Modifier
                .progressSemantics()
                .size(48.dp),
        )
    }
}