package com.stayflow.app.domain.model

import java.math.BigDecimal
import java.util.UUID

data class Room(
    val roomId: UUID,
    val name: String,
    val description: String,
    val nightPrice: BigDecimal,
    val beds: Int,
    val lon: Double,
    val lat: Double,
    val image: Image,
    val city: City,
)