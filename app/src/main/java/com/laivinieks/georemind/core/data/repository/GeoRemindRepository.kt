package com.laivinieks.georemind.core.data.repository

import com.laivinieks.georemind.core.domain.model.BaseModel
import kotlinx.coroutines.flow.Flow

interface GeoRemindRepository<T : BaseModel> {
    fun getModels(): Flow<List<T>>
    suspend fun getModelById(id: Int): T?
    suspend fun insertModel(baseModel: T)
    suspend fun deleteModel(baseModel: T)
}