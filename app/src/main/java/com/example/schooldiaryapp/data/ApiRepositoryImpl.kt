package com.example.schooldiaryapp.data

import android.util.Log
import com.example.ecommerceapp.network.ApiService
import com.example.schooldiaryapp.data.network.models.Grade
import com.example.schooldiaryapp.data.network.models.SchoolClass
import com.example.schooldiaryapp.data.network.models.Student
import com.example.schooldiaryapp.data.network.models.Teacher
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Resource
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService
): ApiRepository {
    override suspend fun getClassesByTeacherId(teacherId: Int): Resource<List<SchoolClass>> {
        val response = try{
            api.getClassesByTeacherId(teacherId)
        }catch (e: Exception){
            return Resource.Error("Error ${e.message}")
        }
        Log.d("LOL", "response ${response[0]}")
        return Resource.Success(response)
    }

    override suspend fun getStudentsByClassID(classId: Int?): Resource<List<Student>> {
        val response = try{
            api.getStudentsByClassID(classId)
        }catch (e: Exception){
            Log.d("LOL", "Error ${e.message}")
            return Resource.Error("Error ${e.message}")
        }
        Log.d("LOL", "response ${response[0]}")
        return Resource.Success(response)
    }

    override suspend fun getTeachersByClass(classId: Int?): Resource<List<Teacher>> {
        val response = try{
            api.getTeachersByClass(classId)
        }catch (e: Exception){
            Log.d("LOL", "Error ${e.message}")
            return Resource.Error("Error ${e.message}")
        }
        Log.d("LOL", "response ${response[0]}")
        return Resource.Success(response)
    }

    override suspend fun getGradesByStudentID(studentId: Int?): Resource<List<Grade>> {
        val response = try{
            api.getGradesByStudentID(studentId)

        }catch (e: Exception){
            Log.d("LOL", "Error ${e.message}")
            return Resource.Error("Error ${e.message}")
        }
        Log.d("LOL", "response ${response[0]}")
        return Resource.Success(response)
    }

    override suspend fun addStudentGrade(studentData: Grade?):Resource<Unit> {
        return if (studentData == null) {
            Resource.Error(message="Student data is null")
        } else {
            try {
                val response = api.addStudentGrade(studentData)
                if (response.isSuccessful) {
                    Resource.Success(Unit)
                } else {
                    Resource.Error("Failed to add student grade: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("LOL", "Error: ${e.message}")
                Resource.Error("Error: ${e.message}")
            }
        }
    }



}