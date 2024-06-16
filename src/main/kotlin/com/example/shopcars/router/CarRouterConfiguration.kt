package com.example.shopcars.router

import com.example.shopcars.handler.CarHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CarRouterConfiguration(val carHandler: CarHandler) {
    @Bean
    fun apiRouter() = coRouter {
        "/api/cars".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("", carHandler::getAll)

                contentType(MediaType.APPLICATION_JSON).nest {
                    POST("", carHandler::add)
                }

                "/{id}".nest {
                    GET("", carHandler::getById)
                    DELETE("", carHandler::delete)

                    contentType(MediaType.APPLICATION_JSON).nest {
                        PUT("", carHandler::update)
                    }
                }
            }
        }
    }
}