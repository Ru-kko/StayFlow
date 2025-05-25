package com.stayflow.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.domain.model.Reservation
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ReservationCard(
    reservation: Reservation,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()

    Column (
        modifier = modifier
            .clip(RoundedCornerShape(15))
            .background(AppTheme.palette.Surface2)
            .width(120.dp)
    ) {
        StayflowImage(
            url = reservation.room.image.url,
            contentDescription = reservation.room.image.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp, 10.dp)
        ) {
            Text(
                text = reservation.room.name,
                color = AppTheme.palette.Text,
                style = Typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = Typography.bodyLarge.lineHeight.value.dp * 2)
            )
            DateInfo(reservation.startDate, "From")
            DateInfo(reservation.endDate, "To")
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pin),
                    tint = AppTheme.palette.Text,
                    contentDescription = "Location",
                    modifier = Modifier
                        .height(20.dp)
                        .padding(end = 5.dp)
                )
                Text(
                    text = "${reservation.room.city.name}, ${reservation.room.city.country.name}",
                    style = Typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.palette.Text,
                )
            }
            OutlinedButton(
                text = if (today > reservation.startDate) "Passed" else "Cancel",
                onClick = { },
                textColor = if (today > reservation.startDate)
                    AppTheme.palette.Yellow
                else
                    AppTheme.palette.Red,
                modifier = Modifier.padding(30.dp, 8.dp)
            )
        }
    }
}

@Composable
private fun DateInfo(date: LocalDate, text: String) {
    val df = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(bottom = 5.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.calendar),
            tint = AppTheme.palette.Text,
            contentDescription = text,
            modifier = Modifier
                .height(25.dp)
                .padding(end = 5.dp)
        )

        Column (verticalArrangement = Arrangement.Center) {
            Text(
                text = df.format(date),
                style = Typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            )
            Text(
                text = text,
                color = AppTheme.palette.Subtext0,
                style = Typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}