package com.stayflow.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

@Composable
fun IconText(text: String,
             style: TextStyle = Typography.titleMedium,
             description: String? = null,
             color: Color = AppTheme.palette.Text,
             painter: Painter,
             fontWeight: FontWeight? = null,
             modifier: Modifier = Modifier,
             ){

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Image(
                painter = painter,
                contentDescription = description,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .height(style.fontSize.value.dp - 3.dp)
            )
            Text(
                text = text,
                fontWeight = fontWeight,
                style = Typography.titleMedium,
                color = color
            )

    }
}