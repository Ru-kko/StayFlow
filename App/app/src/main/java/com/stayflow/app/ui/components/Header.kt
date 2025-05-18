package com.stayflow.app.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography


@Composable
fun Header(
    height: Dp = 100.dp,
    logoBackGround: Boolean = false,
    content: (@Composable BoxScope.() -> Unit)
) {
    val bg = AppTheme.palette.Crust

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val maxHeight = this.maxHeight
        val effectiveHeight = if (height == 0.dp) maxHeight + 50.dp else height
        val heightAnim by animateDpAsState(
            targetValue = effectiveHeight,
            label = "headerHeight",
            animationSpec = tween(durationMillis = 200)
        )

        Column {
            Box(
                modifier = Modifier
                    .background(bg)
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                    .height(heightAnim)
                    .fillMaxWidth()
                    .graphicsLayer {
                        clip = false
                    }
            ) {
                HeaderLogo(
                    showCircle = logoBackGround,
                    modifier = Modifier
                        .absoluteOffset(x = 20.dp, y = (-10).dp)
                        .align(Alignment.TopEnd)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    content()
                    if (height == 0.dp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        .0f to Color.Transparent,
                                        .4f to AppTheme.palette.Crust,
                                        1.0f to AppTheme.palette.Crust,
                                    )
                                )
                        )
                }
            }
            Wave(background = bg)
        }
    }
}

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Typography.displayMedium,

) {
    Text(
        text = text,
        color = AppTheme.palette.Text,
        style = style,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Composable
fun HeaderLogo(
    showCircle: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val animatedSize by animateDpAsState(
        targetValue = if (showCircle) 80.dp else 0.dp,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "circleAnim"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(100.dp),
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = 2f
                    scaleY = 2f
                }
                .size(animatedSize)
                .offset(x = 10.dp, y = (-15).dp)
                .background(AppTheme.palette.Sky, shape = CircleShape)
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun Wave(
    modifier: Modifier = Modifier,
    waveHeight: Dp = 50.dp,
    background: Color
) {
    val density = LocalDensity.current
    val heightPx = with(density) { waveHeight.toPx() }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(waveHeight)
    ) {
        val width = size.width

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, heightPx * 0.6f)

            cubicTo(
                width * 0.15f, heightPx * 0.3f,
                width * 0.45f, heightPx * 0.1f,
                width * 0.6f, heightPx * 0.6f
            )

            cubicTo(
                width * 0.75f, heightPx * 1.0f,
                width * 0.9f, heightPx * 0.6f,
                width, heightPx * 0.4f
            )

            lineTo(width, 0f)
            close()
        }

        drawPath(
            path = path,
            color = background
        )
    }
}