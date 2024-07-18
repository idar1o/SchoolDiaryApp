package com.example.schooldiaryapp.presentation.acc_teacher.chat_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schooldiaryapp.data.network.models.Student
import com.example.schooldiaryapp.presentation.models.ClassListState
import com.example.schooldiaryapp.presentation.models.StudentListState
import com.example.schooldiaryapp.ui.theme.ActiveBgColor
import com.example.schooldiaryapp.ui.theme.AppBarBgColor


val TOP_BAR_HEIGHT = 56.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Composable
fun ChatListScreen(
    stateClassList: ClassListState,
    onItemClick: (classId: Int) -> Unit,
    vm: ChatListViewModel,

    ) {
    val listState = rememberLazyListState()
    val defaultValue: String = if (stateClassList.classList.isNotEmpty()) stateClassList.classList[0].className else ""
    val selectedClass = remember { mutableStateOf(defaultValue) }
    val studentList = remember { mutableStateOf<List<Student>>(vm.studentList.value.studentList) }

    studentList.value = vm.studentList.value.studentList

    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                MainContent(listState = listState, stateStudentList = studentList , onItemClick = onItemClick)

                TopBar(
                    listState = listState,
//                    stateClassList = stateClassList,
                    selectedClass = selectedClass,
                    classNames = stateClassList,
                    vm = vm

                )
            }

        }
    )

}

@Composable
fun TopBar(
    listState: LazyListState,
    classNames: ClassListState,
    selectedClass: MutableState<String>,
    vm: ChatListViewModel,

) {

    TopAppBar (
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (listState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
        backgroundColor = AppBarBgColor
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            classNames.classList.forEach { itclass ->
                val isSelected = itclass.className == selectedClass.value
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = if (isSelected) ActiveBgColor else AppBarBgColor
                        )
                        .clickable {
                            selectedClass.value = itclass.className
                            vm.fetchStudentList(itclass.classId.toInt())
                        }

                ) {
                    val textClass = if (isSelected) itclass.className else itclass.className+ "0"

                    Text(
                        text = textClass,
                        color = Color.White,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ClassAppBarPreview() {
    val selectedClass = remember { mutableStateOf("") }
    val classNames = listOf("Class 1", "Class 2", "Class 3", "Class 4", "Class 5")
//    TopBar(classNames = classNames,selectedClass = selectedClass )
}


@Composable
fun MainContent(
    listState: LazyListState,
    stateStudentList: MutableState<List<Student>>,
    onItemClick: (classId: Int) -> Unit
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
            items = stateStudentList.value,
        ) { student ->
            ClassItem(student = student)
        }
    }
}

@Composable
fun ClassItem(student: Student,
//              onItemClick: (classId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//            .clickable { onItemClick(schoolClass.classId.toInt()) }, // Добавлен модификатор clickable,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = student.fullname,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),

        ) {
            student.gradeList?.value?.forEach { grade ->
                androidx.compose.material3.Text(
                    text = grade.grade.toString(),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

    }
}

