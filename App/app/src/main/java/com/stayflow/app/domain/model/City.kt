package com.stayflow.app.domain.model

import java.util.UUID

data class City(
    val cityId: UUID,
    val country: Country,
    val name: String,
)