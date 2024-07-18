package com.example.schooldiaryapp.domain

import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.data.network.models.SchoolClass
import com.example.schooldiaryapp.data.network.models.Student
import com.example.schooldiaryapp.data.network.models.StudentsInfo
import com.example.schooldiaryapp.data.network.models.Teacher
import com.example.schooldiaryapp.utils.Resource

interface ApiRepository {
    suspend fun getClassesByTeacherId(teacherId: Int): Resource<List<SchoolClass>>
    suspend fun getStudentsByClassID(classId: Int?): Resource<List<Student>>
    suspend fun getTeachersByClass(classId: Int?) : Resource<List<Teacher>>
    suspend fun getGradesByStudentID(studentId: Int?): Resource<List<Grade>>
    suspend fun addStudentGrade(studentData: Grade?): Resource<Unit>
    suspend fun getStudentsInfoByClassID(classId: Int?): Resource<List<StudentsInfo>>

}