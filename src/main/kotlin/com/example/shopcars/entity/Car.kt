package com.example.shopcars.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Car(
    @Id var id: Long? = null,
    val name: String
)
fun Car.toDto(): CarDto = CarDto(
    name = name
)
