package com.stayflow.app.domain

import java.time.LocalDate
import java.util.UUID

data class Reservation (
    val reservationId: UUID,
    val user: User,
    val room: Room,
    val startDate: LocalDate,
    val endDate: LocalDate,
)