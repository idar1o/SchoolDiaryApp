package com.example.ecommerceapp.network

import com.example.schooldiaryapp.data.source.network.models.AssignmentRequest
import com.example.schooldiaryapp.data.source.network.models.AssignmentResponse
import com.example.schooldiaryapp.data.source.network.models.Grade
import com.example.schooldiaryapp.data.source.network.models.LoginRequest
import com.example.schooldiaryapp.data.source.network.models.LoginResponse
import com.example.schooldiaryapp.data.source.network.models.MessageResponse
import com.example.schooldiaryapp.data.source.network.models.SchoolClass
import com.example.schooldiaryapp.data.source.network.models.Student
import com.example.schooldiaryapp.data.source.network.models.StudentsInfo
import com.example.schooldiaryapp.data.source.network.models.Teacher
import com.example.schooldiaryapp.data.source.network.models.WeeklySchedules
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


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
    suspend fun getAssignmentsByClassID(@Path("class_id") classId: Long): List<AssignmentResponse>
    @PUT("/assignments/{assignment_id}")
    suspend fun updateAssignmentByID(@Path("assignment_id") assignmentId: Int,
                                     @Body assignment: AssignmentRequest
    ): Response<Void>
    @GET("/assignments/{assignment_id}")
    suspend fun getAssignmentByID(@Path("assignment_id") assignmentId: Int): AssignmentResponse

    @GET("/chat/messages")
    suspend fun getMessagesByUsersID(@Query("sender_id") senderId: Int, @Query("receiver_id") receiverId: Int ): List<MessageResponse>

    @GET("/lessons/teacher/{teacher_id}")
    suspend fun getLessonsByTeacherId(@Path("teacher_id") teacherId: Int?): WeeklySchedules

    @POST("/students/grade")
    suspend fun addStudentGrade(@Body studentData: Grade): Response<Unit>

    @POST("/login")
    suspend fun teacherLogin(@Body loginData: LoginRequest): Response<LoginResponse>



}