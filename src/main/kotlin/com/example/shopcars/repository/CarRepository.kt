package com.example.shopcars.repository

import com.example.shopcars.entity.Car
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CarRepository : CoroutineCrudRepository<Car,Long> {
    override fun findAll(): Flow<Car>
    override suspend fun findById(id: Long): Car?
    override suspend fun existsById(id: Long): Boolean
    override suspend fun <S : Car> save(entity: S): Car
    override suspend fun deleteById(id: Long)
}