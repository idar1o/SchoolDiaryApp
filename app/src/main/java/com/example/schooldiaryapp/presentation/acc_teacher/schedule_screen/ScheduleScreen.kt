@file:Suppress("DEPRECATION")

package com.example.schooldiaryapp.presentation.acc_teacher.schedule_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.schooldiaryapp.data.source.network.models.Lesson
import com.example.schooldiaryapp.data.source.network.models.WeeklySchedules
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@Composable
fun ScheduleScreen(navHostController: NavHostController, vm: ScheduleScreenViewModel) {

    val lessons = vm.sheduleList.value.weeklySchedules
    val weeks: List<String>? = vm.sheduleList.value.weeklySchedules?.keys?.toList()?.reversed()

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            count = weeks?.size ?: 0,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MainContentWeek(page = weeks?.get(page), lessons = lessons)
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
    }
}

@Composable
fun MainContentWeek(page: String?, lessons: WeeklySchedules?) {
    val res: Map<String, List<Lesson>?> = mapOf(
        "monday" to lessons?.get(page)?.monday,
        "tuesday" to lessons?.get(page)?.tuesday,
        "wednesday" to lessons?.get(page)?.wednesday,
        "thursday" to lessons?.get(page)?.thursday,
        "friday" to lessons?.get(page)?.friday,
        "saturday" to lessons?.get(page)?.saturday,
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
        res.forEach { (key, lessons) ->
            if (!lessons.isNullOrEmpty()) {
                item {
                    Text(text = key)
                    WeekItem(lessons = lessons)
                }
            }
        }
    }

}

@Composable
fun WeekItem(lessons: List<Lesson>?){
    Column {

        lessons?.forEach{lesson ->
            Text(text = "Сабак: ${lesson.subjectName}, болмо: ${lesson.roomNum}, класс ${lesson.className}", modifier = Modifier)

        }

    }
}
