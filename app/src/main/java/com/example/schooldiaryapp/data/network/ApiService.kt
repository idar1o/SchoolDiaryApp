package com.example.ecommerceapp.network

import com.example.schooldiaryapp.data.network.models.Assignment
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.data.network.models.SchoolClass
import com.example.schooldiaryapp.data.network.models.Student
import com.example.schooldiaryapp.data.network.models.StudentsInfo
import com.example.schooldiaryapp.data.network.models.Teacher
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("/classes/{teacher_id}")
    suspend fun getClassesByTeacherId(@Path("teacher_id") teacherId: Int): List<SchoolClass>

    @GET("/classes/students/{class_id}")
    suspend fun getStudentsByClassID(@Path("class_id") classId: Int?): List<Student>

    @GET("/teachers/class/{class_id}")
    suspend fun getTeachersByClass(@Path("class_id") classId: Int?): List<Teacher>

    @GET("/students/{student_id}/grades")
    suspend fun getGradesByStudentID(@Path("student_id") studentId: Int?): List<Grade>

    @GET("/classes/students/grades/{class_id}")
    suspend fun getStudentsInfoByClassID(@Path("class_id") classId: Int?): List<StudentsInfo>

    @GET("/classes/assignments/{class_id}")
    suspend fun getAssignmentsByClassID(@Path("class_id") classId: Int?): List<Assignment>


    @POST("/students/grade")
    suspend fun addStudentGrade(@Body studentData: Grade): Response<Unit>


}