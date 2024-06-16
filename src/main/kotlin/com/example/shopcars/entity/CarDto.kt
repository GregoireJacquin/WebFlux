package com.example.shopcars.entity

class CarDto(val name: String) {
}
fun CarDto.toEntity(): Car = Car(
    name = name
)