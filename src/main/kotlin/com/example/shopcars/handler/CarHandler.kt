package com.example.shopcars.handler

import com.example.shopcars.entity.Car
import com.example.shopcars.entity.CarDto
import com.example.shopcars.entity.toDto
import com.example.shopcars.entity.toEntity
import com.example.shopcars.repository.CarRepository
import kotlinx.coroutines.flow.map
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CarHandler(private val carRepository: CarRepository) {
    suspend fun getAll(req: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(
                carRepository.findAll().map { it.toDto() }
            )
    }

    suspend fun getById(req: ServerRequest): ServerResponse {
        val id = Integer.parseInt(req.pathVariable("id"))
        val existingCar = carRepository.findById(id.toLong())

        return existingCar?.let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(it)
        } ?: ServerResponse.notFound().buildAndAwait()
    }

    suspend fun add(req: ServerRequest): ServerResponse {
        val receivedCar = req.awaitBodyOrNull(CarDto::class)

        return receivedCar?.let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(
                    carRepository
                        .save(it.toEntity())
                        .toDto()
                )
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun update(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id")

        val receivedCar = req.awaitBodyOrNull(CarDto::class)
            ?: return ServerResponse.badRequest().buildAndAwait()

        val existingCar = carRepository.findById(id.toLong())
            ?: return ServerResponse.notFound().buildAndAwait()

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(
                carRepository.save(
                    receivedCar.toEntity().copy(id = existingCar.id)
                ).toDto()
            )
    }

    suspend fun delete(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id")

        return if (carRepository.existsById(id.toLong())) {
            carRepository.deleteById(id.toLong())
            ServerResponse.noContent().buildAndAwait()
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }
}