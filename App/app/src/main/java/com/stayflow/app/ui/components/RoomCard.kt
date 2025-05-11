package com.stayflow.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

@Composable
fun RoomCard(
    image: String? = null,
    name: String,
    description: String,
    location: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AppTheme.palette.Surface0)
        ) {
            StayflowImage(
                url = image,
                contentDescription = name,
                modifier = Modifier.fillMaxHeight().width(150.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                Text(
                    style = Typography.bodyMedium,
                    text = name,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = AppTheme.palette.Text
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(
                    style = Typography.labelSmall,
                    text = description,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.palette.Overlay1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.pin),
                        contentDescription = "pin",
                        modifier = Modifier.height(12.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Text(
                        style = Typography.labelSmall,
                        text = location,
                        color = AppTheme.palette.Subtext0
                    )
                }
            }
        }
    }
}
