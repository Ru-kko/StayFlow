package com.stayflow.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textColor: Color = AppTheme.palette.Surface0,
    backgroundColor: Color = AppTheme.palette.Green,
    textStyle: TextStyle = Typography.bodyLarge,
    drawableLeft: Painter? = null,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            drawableLeft?.let {
                Image(
                    painter = it,
                    contentDescription = text,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(textStyle.fontSize.value.dp)
                )
            }

            Text(
                text = text,
                style = textStyle,
                color = textColor,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textColor: Color = AppTheme.palette.Green,
    stroke: Dp = 2.dp,
    textStyle: TextStyle = Typography.bodyLarge,
    drawableSize: Dp = textStyle.fontSize.value.dp - 2.dp,
    drawableLeft: Painter? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable { if (enabled) onClick() }
            .border(stroke, textColor, RoundedCornerShape(50))
            .then(modifier),
    ) {
        drawableLeft?.let {
            Image(
                painter = it,
                contentDescription = text,
                modifier = Modifier
                    .padding(end = textStyle.fontSize.value.dp / 2)
                    .size(drawableSize)
            )
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(0.dp)
        )

    }
}


@Composable
fun RoundedIconButton(
    onClick: () -> Unit,
    size: Dp = 50.dp,
    painter: Painter,
    padding: Dp = 5.dp,
    contentDescription: String
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(AppTheme.palette.Crust)
            .border(2.dp, AppTheme.palette.Text, CircleShape)
            .padding(padding),
        contentPadding = PaddingValues(padding),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.palette.Crust,
            contentColor = AppTheme.palette.Text
        ),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
fun BigIconButton(
    text: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.palette.Green,
    contentColor: Color = AppTheme.palette.Surface0,
    style: TextStyle = Typography.displaySmall,
    size: Dp = 200.dp,
    onClick: () -> Unit = {},
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .width(size)
            .height(size)
            .clip(RoundedCornerShape(10))
            .background(backgroundColor)
            .padding(10.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = icon,
            tint = contentColor,
            contentDescription = text,
            modifier = Modifier
                .width(size - 20.dp - (style.fontSize.value.dp + 5.dp))
                .height(size - 20.dp - (style.fontSize.value.dp + 5.dp)),
        )
        Text(
            text = text,
            style = style,
            fontWeight = FontWeight.ExtraBold,
            color = contentColor,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}
