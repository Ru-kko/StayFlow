package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stayflow.app.domain.City
import com.stayflow.app.domain.Country
import com.stayflow.app.domain.Image
import com.stayflow.app.domain.Reservation
import com.stayflow.app.domain.Role
import com.stayflow.app.domain.Room
import com.stayflow.app.domain.User
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.ReservationCard
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID


class SelfReservationsRoute : ComposableRoute {
    override val height = mutableStateOf(80.dp)

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText("My Reservations")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            items(getData()) {
                ReservationCard(it)
            }
        }
    }

    private fun getData(): List<Reservation> {
        // Countries
        val spain = Country(
            countryId = UUID.randomUUID(),
            name = "Spain"
        )
        val france = Country(
            countryId = UUID.randomUUID(),
            name = "France"
        )

        // Cities
        val barcelona = City(
            cityId = UUID.randomUUID(),
            country = spain,
            name = "Barcelona"
        )
        val paris = City(
            cityId = UUID.randomUUID(),
            country = france,
            name = "Paris"
        )

        // Images
        val image1 = Image(
            imageId = UUID.randomUUID(),
            contentType = "image/jpeg",
            url = "https://i.scdn.co/image/ab67616d0000b273e32b600cf149063d811341e3",
            name = "Deluxe Room"
        )

        val image2 = Image(
            imageId = UUID.randomUUID(),
            contentType = "image/jpeg",
            url = "https://images.genius.com/eb3eefcac9101aa424474f15c6869f10.1000x1000x1.png",
            name = "Modern Apartment"
        )

        // Rooms
        val room1 = Room(
            roomId = UUID.randomUUID(),
            name = "Deluxe Room",
            description = "A spacious room with sea view.",
            nightPrice = BigDecimal("120.50"),
            beds = 2,
            lon = 2.1734,
            lat = 41.3851,
            image = image1,
            city = barcelona
        )

        val room2 = Room(
            roomId = UUID.randomUUID(),
            name = "Modern Apartment",
            description = "Cozy and stylish apartment in the heart of Paris.",
            nightPrice = BigDecimal("150.00"),
            beds = 1,
            lon = 2.3522,
            lat = 48.8566,
            image = image2,
            city = paris
        )

        // Users
        val user1 = User(
            userId = UUID.randomUUID(),
            firstName = "Carlos",
            lastName = "Martínez",
            email = "carlos@example.com",
            role = Role.USER
        )

        val user2 = User(
            userId = UUID.randomUUID(),
            firstName = "Lucía",
            lastName = "Gómez",
            email = "lucia@example.com",
            role = Role.USER
        )

        val user3 = User(
            userId = UUID.randomUUID(),
            firstName = "Jean",
            lastName = "Dupont",
            email = "jean.dupont@example.fr",
            role = Role.USER
        )

        val user4 = User(
            userId = UUID.randomUUID(),
            firstName = "Maria",
            lastName = "Lopez",
            email = "maria.lopez@example.com",
            role = Role.ADMIN
        )

        // Reservations
        val reservations = listOf(
            Reservation(
                reservationId = UUID.randomUUID(),
                user = user1,
                room = room1,
                startDate = LocalDate.of(2025, 6, 1),
                endDate = LocalDate.of(2025, 6, 7)
            ),
            Reservation(
                reservationId = UUID.randomUUID(),
                user = user2,
                room = room1,
                startDate = LocalDate.of(2025, 6, 10),
                endDate = LocalDate.of(2025, 6, 15)
            ),
            Reservation(
                reservationId = UUID.randomUUID(),
                user = user3,
                room = room2,
                startDate = LocalDate.of(2025, 7, 5),
                endDate = LocalDate.of(2025, 7, 10)
            ),
            Reservation(
                reservationId = UUID.randomUUID(),
                user = user4,
                room = room2,
                startDate = LocalDate.of(2025, 8, 1),
                endDate = LocalDate.of(2025, 8, 8)
            ),
            Reservation(
                reservationId = UUID.randomUUID(),
                user = user1,
                room = room2,
                startDate = LocalDate.of(2025, 9, 1),
                endDate = LocalDate.of(2025, 9, 5)
            )
        )

        return reservations
    }
}