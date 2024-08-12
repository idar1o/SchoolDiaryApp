package com.example.schooldiaryapp.domain.use_cases

import android.util.Log
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.domain.models.Assignment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAssignmentsByClassIdUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    operator fun invoke(id: Long): Flow<List<Assignment>> =
        apiRepository.getAssignmentList(classId = id)
            .map { apiData ->
                // Обновляем локальное хранилище с полученными данными
                apiRepository.updateDbLocal(apiData)
                // Возвращаем обновленные данные
                Log.d("LOL", "Возвращаем обновленные данные")
                apiData
            }
            .catch { e ->
                // В случае ошибки (например, отсутствие интернета), получаем данные из локального хранилища
                val localData = apiRepository.getDatabaseList(classId = id)
                Log.d("LOL", "В случае ошибки ${e.message}")
                // Если данные из локального хранилища отсутствуют, выбрасываем исключение
            }
            .flowOn(Dispatchers.IO) // Выполняем поток на нужном диспетчере
}