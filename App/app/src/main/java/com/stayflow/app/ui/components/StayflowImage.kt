package com.stayflow.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.stayflow.app.R
import com.stayflow.app.presentation.ImageViewModel

@Composable
fun StayflowImage(
    modifier: Modifier = Modifier,
    url: String? = null,
    painter: Painter = painterResource(R.drawable.placeholder),
    loading: Boolean = false,
    useHardware: Boolean = true,
    contentDescription: String,
) {
    val imageViewModel: ImageViewModel = hiltViewModel()
    val context = LocalContext.current

    val download = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(useHardware)
            .build(),
        imageLoader = imageViewModel.imageLoader,
    )
    val downloadState by download.state.collectAsState()

    when {
        loading || downloadState is AsyncImagePainter.State.Loading -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = modifier.blur(3.dp),
            )
        }

        url.isNullOrEmpty() || downloadState is AsyncImagePainter.State.Error -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = modifier,
            )
        }

        else -> {
            Image(
                painter = download,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = modifier,
            )
        }
    }
}