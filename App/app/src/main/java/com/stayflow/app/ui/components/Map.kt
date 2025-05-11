package com.stayflow.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.stayflow.app.R
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    image: String? = null,
    latitude: Double,
    longitude: Double,
    zoom: Float = 15.0f
) {
    val point = LatLng(latitude, longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(point, zoom)
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isBuildingEnabled = false,
            isTrafficEnabled = false,
            isMyLocationEnabled = false,
        ),
        modifier = modifier
    ) {
        MapMarkerPin(point, image)
    }
}

@Composable
fun LazyMapView(
    modifier: Modifier = Modifier,
    image: String? = null,
    latitude: Double,
    longitude: Double,
    zoom: Float = 15.0f
) {
    var showMap by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        if (showMap) {
            MapView(
                image = image,
                latitude = latitude,
                longitude = longitude,
                zoom = zoom,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.palette.Surface0)
                    .clickable { showMap = true },
                contentAlignment = Alignment.Center
            ) {
                Text("Show Location", color = AppTheme.palette.Subtext1, style = Typography.titleLarge)
            }
        }
    }
}

@Composable
private fun MapMarkerPin(
    point: LatLng,
    imageUrl: String? = null,
    color: Color = AppTheme.palette.Red
) {
    val markerState = remember { MarkerState(point) }

    MarkerComposable(
        state = markerState,
        anchor = if (imageUrl.isNullOrEmpty()) Offset(0.5f, 1f) else Offset(0f, 1f),
    ) {
        if (imageUrl.isNullOrEmpty()) {
            Icon(
                painter = painterResource(R.drawable.pin),
                contentDescription = "Location",
                modifier = Modifier.height(30.dp),
                tint = color,
            )
        } else {
            val shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 0.dp)
            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(AppTheme.palette.Red)
                    .padding(2.dp)
                    .size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                StayflowImage(
                    url = imageUrl,
                    useHardware = false,
                    contentDescription = "Location",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize(),
                )
            }
        }
    }
}