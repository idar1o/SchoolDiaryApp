package com.example.schooldiaryapp.domain

import com.example.schooldiaryapp.data.source.network.models.AssignmentRequest
import com.example.schooldiaryapp.data.source.network.models.Grade
import com.example.schooldiaryapp.data.source.network.models.LoginRequest
import com.example.schooldiaryapp.data.source.network.models.LoginResponse
import com.example.schooldiaryapp.data.source.network.models.MessageResponse
import com.example.schooldiaryapp.data.source.network.models.SchoolClass
import com.example.schooldiaryapp.data.source.network.models.Student
import com.example.schooldiaryapp.data.source.network.models.StudentsInfo
import com.example.schooldiaryapp.data.source.network.models.Teacher
import com.example.schooldiaryapp.data.source.network.models.WeeklySchedules
import com.example.schooldiaryapp.domain.models.Assignment
import com.example.schooldiaryapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun getClassesByTeacherId(teacherId: Int): Resource<List<SchoolClass>>
    suspend fun getStudentsByClassID(classId: Int?): Resource<List<Student>>

    fun getAssignmentList(classId: Long): Flow<List<Assignment>>
    fun getAssignment(assignmentId: Int): Flow<Assignment>
    suspend fun getDatabaseList(classId: Long): Flow<List<Assignment?>>

    fun updateAssignmentById(assignmentId: Int, assignment: AssignmentRequest): Flow<Boolean>

    suspend fun updateDbLocal(apiData: List<Assignment>): Flow<Boolean>
    suspend fun getTeachersByClass(classId: Int?) : Resource<List<Teacher>>
    suspend fun getGradesByStudentID(studentId: Int?): Resource<List<Grade>>
    suspend fun addStudentGrade(studentData: Grade?): Resource<Unit>
    suspend fun teacherLogin(loginData: LoginRequest?): Resource<LoginResponse?>
    suspend fun getStudentsInfoByClassID(classId: Int?): Resource<List<StudentsInfo>>
    suspend fun getLessonsByTeacherID(teacherId: Int?): Resource<WeeklySchedules>
    suspend fun getMessagesByUsersID(senderId: Int, receiverId: Int): Resource<List<MessageResponse>>



}