package com.stayflow.app.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.domain.model.City
import com.stayflow.app.domain.model.Country
import com.stayflow.app.domain.model.Image
import com.stayflow.app.domain.model.Room
import com.stayflow.app.ui.components.DateRange
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.IconText
import com.stayflow.app.ui.components.LazyMapView
import com.stayflow.app.ui.components.OutlinedButton
import com.stayflow.app.ui.components.StayflowImage
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

data class ReservationRequest(
    val from: LocalDate,
    val to: LocalDate,
    val roomId: UUID
)

class RoomDetail @Inject constructor() : ConfigurableComposableRoute<UUID> {
    override val logoBackGround = true
    override val height = mutableStateOf(250.dp)
    private lateinit var roomId: UUID
    private var roomData by mutableStateOf<Room?>(null)
    private var isLoading by mutableStateOf(true)

    private suspend fun getData() {
        delay(200)
        roomData = Room(
            roomId = roomId,
            name = "Duplex Penthouse",
            description = lorem,
            image = Image(
                imageId = UUID.randomUUID(),
                url = "https://a0.muscache.com/im/pictures/miso/Hosting-635983960492703673" +
                        "/original/97bf944e-231c-487d-acc0-8ee6903c4e44.jpeg?im_w=720",
                name = "Test Image",
                contentType = "image/jpeg"
            ),
            nightPrice = BigDecimal(100),
            lat = 4.675741,
            lon = -74.087361,
            beds = 5,
            city = City(
                cityId = UUID.randomUUID(),
                name = "Bogota",
                country = Country(
                    countryId = UUID.randomUUID(),
                    name = "Colombia"
                )
            )
        )
        isLoading = false
    }

    override fun setProperties(props: UUID) {
        this.roomId = props
    }

    @Composable
    private fun LoadRoomData() {
        LaunchedEffect(roomId) {
            getData()
        }
    }

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        LoadRoomData()

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .padding(horizontal = 15.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                StayflowImage(
                    contentDescription = roomData?.image?.name ?: "Loading",
                    url = roomData?.image?.url,
                    loading = isLoading,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .clip(CircleShape)
                        .border(2.dp, AppTheme.palette.Sky, CircleShape)
                )
                Spacer(Modifier.padding(horizontal = 10.dp))
                OutlinedButton(
                    onClick = {},
                    text = "Delete",
                    textColor = AppTheme.palette.Red,
                    textStyle = Typography.titleMedium,
                    drawableLeft = painterResource(R.drawable.trash),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Text(
                text = roomData?.name ?: "Loading",
                color = AppTheme.palette.Text,
                style = Typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 5.dp)
            )

            var colModifier = Modifier
                .fillMaxSize()

            if (height.value == 0.dp)
                colModifier = colModifier.verticalScroll(rememberScrollState())


            Column(
                verticalArrangement = Arrangement.Top,
                modifier = colModifier.padding(bottom = if (height.value == 0.dp) 110.dp else 0.dp)
            ) {
                CommonInfo()

                if (height.value == 0.dp && roomData != null) {
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        val size = maxWidth
                        LazyMapView(
                            latitude = roomData!!.lat,
                            longitude = roomData!!.lon,
                            image = roomData!!.image.url,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppTheme.palette.Text)
                                .height(size)
                        )
                    }
                    HorizontalDivider(
                        color = AppTheme.palette.Overlay2,
                        thickness = 2.dp,
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 20.dp)
                    )
                    Text(
                        text = roomData!!.description,
                        style = Typography.bodyMedium,
                        color = AppTheme.palette.Text,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    OutlinedButton(
                        onClick = { height.value = 250.dp },
                        text = "See Less",
                        textColor = AppTheme.palette.Sapphire,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }

            }
        }
    }

    @Composable
    private fun CommonInfo() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            IconText(
                text = "${roomData?.nightPrice ?: "0.00"} USD/Night",
                description = "Price",
                painter = painterResource(R.drawable.price),
                fontWeight = FontWeight.SemiBold,
                style = Typography.titleLarge,
            )
            IconText(
                text = "${roomData?.beds ?: "Loading"} Bed${
                    if (roomData?.beds != 1) "s" else ""
                }",
                painter = painterResource(R.drawable.bed),
                description = "Beds",
                fontWeight = FontWeight.SemiBold,
                style = Typography.titleLarge,
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 10.dp)
        ) {
            IconText(
                text = "${roomData?.city?.name}, ${roomData?.city?.country?.name}",
                description = "Location",
                painter = painterResource(R.drawable.pin),
                fontWeight = FontWeight.Normal,
            )
            if (!isLoading && height.value != 0.dp)
                OutlinedButton(
                    onClick = { height.value = 0.dp },
                    text = "See More",
                    textColor = AppTheme.palette.Sapphire,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                )
        }
    }

    @Composable
    override fun BodyContent(scope: BoxScope) = with(scope) {
        var reserveDates by remember {
            mutableStateOf<ReservationRequest?>(null)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 110.dp, top = 10.dp)
                .padding(horizontal = 10.dp)
                .align(Alignment.TopCenter)
        ) {
            DateRange {
                reserveDates = ReservationRequest(it.first, it.second, roomId)
            }
            if (reserveDates != null && roomData != null) {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                RequestBtn(reserveDates!!, roomData!!)
            }
        }
    }

    @Composable
    private fun RequestBtn(reserveDates: ReservationRequest, roomData: Room) {
        val days = reserveDates.to.toEpochDay() - reserveDates.from.toEpochDay()
        val text = "Reserve ${if (days == 0L) 1 else days} " +
                "Night${if (days == 1L || days == 0L) "" else "s"} Room"
        val price = roomData.nightPrice.multiply(BigDecimal(if (days == 0L) 1 else days))

        FilledButton(
            text = text,
            onClick = { },
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
        )
        Text(
            text = "For $price USD",
            style = Typography.bodySmall,
            color = AppTheme.palette.Subtext1
        )
    }
}