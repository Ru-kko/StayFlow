package com.stayflow.app.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography


data class Option<T>(
    val value: T,
    val label: String
)

@Composable
fun <T> StayflowDropdown(options: List<Option<T>>, onItemSelected: (T) -> Unit) {
    var isDialogOpen by remember {
        mutableStateOf(false)
    }

    var itemPosition by remember {
        mutableIntStateOf(0)
    }

    val scaleY by animateFloatAsState(
        targetValue = if (isDialogOpen) -1f else 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ), label = "scaleYAnimation"
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(150.dp)
                .border(3.dp, AppTheme.palette.Crust, CircleShape)
                .padding(vertical = 5.dp, horizontal = 15.dp)
                .clickable {
                    isDialogOpen = true
                }
        ) {
            Text(
                text = options[itemPosition].label,
                style = Typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = AppTheme.palette.Crust,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f)

            )
            Image(
                painter = painterResource(id = R.drawable.poligon),
                contentDescription = "DropDown Icon",
                modifier = Modifier
                    .height(15.dp)
                    .graphicsLayer { this.scaleY = scaleY }
            )
        }
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(0.dp)
                            .background(AppTheme.palette.Crust)
                            .clip(RoundedCornerShape(5.dp))
                            .verticalScroll(rememberScrollState())
                    ) {
                        DialogItem(
                            options = options,
                            itemPosition = itemPosition,
                            onItemSelected = onItemSelected,
                            updateItemPosition = {
                                itemPosition = it
                            },
                            closeDialog = {
                                isDialogOpen = false
                            }
                        )
                    }
                },
                confirmButton = {},
                containerColor = Color.Transparent,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
private fun <T> DialogItem(
    options: List<Option<T>>,
    itemPosition: Int,
    onItemSelected: (T) -> Unit,
    updateItemPosition: (Int) -> Unit,
    closeDialog: () -> Unit,
) {
    options.forEachIndexed { index, opt ->
        Text(
            text = opt.label,
            style = Typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (index != itemPosition) {
                        updateItemPosition(index)
                        onItemSelected(opt.value)
                    }
                    closeDialog()
                }
                .background(
                    if (index != itemPosition) {
                        AppTheme.palette.Crust
                    } else {
                        AppTheme.palette.Subtext1
                    }
                )
                .padding(vertical = 8.dp, horizontal = 10.dp),
            color = if (index == itemPosition)
                AppTheme.palette.Crust
            else
                AppTheme.palette.Text
        )
    }
}