package com.example.schooldiaryapp.presentation.acc_student.subjects_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.schooldiaryapp.data.network.models.SchoolClass
import com.example.schooldiaryapp.data.network.models.Teacher
import com.example.schooldiaryapp.presentation.models.ClassListState
import com.example.schooldiaryapp.presentation.models.SubjectListState


val TOP_BAR_HEIGHT = 56.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Composable
fun SubjectsListScreen(
    stateSubjectList: SubjectListState,
    onItemClick: (teacherId: Int, subject: String) -> Unit
){
    val listState = rememberLazyListState()


    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                MainContent(listState = listState, stateSubjectList = stateSubjectList , onItemClick = onItemClick)
                TopBar(listState = listState, stateSubjectList = stateSubjectList)
            }

        }
    )

}

@Composable
fun TopBar(listState: LazyListState, stateSubjectList: SubjectListState) {
    TopAppBar (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (listState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
        contentPadding = PaddingValues(start = 16.dp)
    ){
        val teach_id = if (!stateSubjectList.subjectList.isEmpty()){
            stateSubjectList.subjectList[0].teacherId
        }else{
            2
        }
        Text(
            text = teach_id.toString(),
            style = TextStyle(
                fontSize = androidx.compose.material3.MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colors.surface
            )

        )

    }
}

@Composable
fun MainContent(
    listState: LazyListState,
    stateSubjectList: SubjectListState,
    onItemClick: (teacherId: Int, subject: String) -> Unit
) {
    val padding by animateDpAsState(
        targetValue = if (listState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )
    LazyColumn(
        modifier = Modifier.padding(top = padding),
        state = listState
    ) {
        items(
            items = stateSubjectList.subjectList,
            key = { it.teacherId }
        ) { subject ->
            ClassItem(subject = subject, onItemClick)
        }
    }
}

@Composable
fun ClassItem(subject: Teacher, onItemClick: (teacherId: Int, subject: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(subject.teacherId, subject.subject) }, // Добавлен модификатор clickable,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = subject.subject,
            style = TextStyle(
                fontSize = androidx.compose.material3.MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}