package com.stayflow.app.ui.components

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayflow.app.R
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Roboto
import com.stayflow.app.ui.theme.Typography
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@Composable
fun StayFlowInputField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
    placeholder: String?,
    leadingIcon: Painter? = null,
    bellowHint: Boolean = false,
    style: TextStyle = Typography.headlineMedium,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.palette

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (!password) VisualTransformation.None else
                PasswordVisualTransformation(),
            placeholder = {
                if (placeholder != null)
                    Text(
                        text = placeholder,
                        color = colors.Subtext1,
                        style = style,
                        modifier = Modifier.background(Color.Transparent)
                    )
            },
            textStyle = TextStyle(
                color = colors.Text,
                fontSize = 30.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.W500
            ).merge(style),
            shape = RoundedCornerShape(50),
            singleLine = true,
            leadingIcon = {
                if (leadingIcon != null)
                    Icon(
                        painter = leadingIcon,
                        contentDescription = placeholder,
                        tint = colors.Text,
                        modifier = modifier
                            .padding(start = 20.dp, end = 10.dp)
                            .size(40.dp)
                    )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = colors.Mantle,
            ),
            modifier = Modifier
                .background(color = Color.Transparent, shape = RoundedCornerShape(50))
                .border(
                    width = 5.dp,
                    color = colors.Text,
                    shape = RoundedCornerShape(50)
                )
                .padding(10.dp, 5.dp)
        )
        if (bellowHint && placeholder != null) {
            Text(
                text = placeholder,
                color = colors.Overlay2,
                style = Typography.bodySmall,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 5.dp, start = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private object TodayToAYearDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val today = LocalDate.now()
        val oneYearFromToday = today.plusYears(1)
        val objective =
            Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate()

        return !objective.isBefore(today) && !objective.isAfter(oneYearFromToday)
    }

    override fun isSelectableYear(year: Int): Boolean {
        val todayYear = LocalDate.now().year
        return year in todayYear..(todayYear + 1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRange(
    modifier: Modifier = Modifier,
    onConfirm: (Pair<LocalDate, LocalDate>) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val rangeState = rememberDateRangePickerState(
        initialDisplayedMonthMillis = calendar.timeInMillis,
        selectableDates = TodayToAYearDates,
        initialDisplayMode = DisplayMode.Input
    )
    val isOpened = remember { mutableStateOf(false) }

    val df = DateFormat.getDateInstance(DateFormat.SHORT)

    if (isOpened.value) {
        DatePickerDialog(
            onDismissRequest = { isOpened.value = false },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledButton(
                        text = "Confirm",
                        onClick = {
                            val startMillis = rangeState.selectedStartDateMillis
                            val endMillis = rangeState.selectedEndDateMillis

                            if (startMillis != null && endMillis != null) {
                                val start = Date(startMillis)
                                val end = Date(endMillis)

                                onConfirm(
                                    Pair(
                                        LocalDate.ofEpochDay(start.time / (24 * 60 * 60 * 1000)),
                                        LocalDate.ofEpochDay(end.time / (24 * 60 * 60 * 1000))
                                    )
                                )
                                isOpened.value = false
                            }
                        },
                        textStyle = Typography.bodyLarge,
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.palette.Mantle,
            ),
            modifier = modifier,
        ) {
            RangePickerContents(rangeState, df)
        }
    }
    Column {
        Text(
            text = "From",
            style = Typography.bodyMedium,
            color = AppTheme.palette.Overlay2,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 4.dp)
        )
        OutlinedButton(
            textColor = AppTheme.palette.Text,
            textStyle = Typography.displaySmall,
            onClick = { isOpened.value = true },
            drawableSize = 28.dp,
            stroke = 4.dp,
            drawableLeft = painterResource(R.drawable.calendar),
            text = df.format(rangeState.selectedStartDateMillis ?: Date()),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 20.dp)
                .width(200.dp)
        )
        Text(
            text = "To",
            style = Typography.bodyMedium,
            color = AppTheme.palette.Overlay2,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 4.dp, top = 20.dp)
        )
        OutlinedButton(
            textColor = AppTheme.palette.Text,
            textStyle = Typography.displaySmall,
            onClick = { isOpened.value = true },
            drawableSize = 28.dp,
            stroke = 4.dp,
            drawableLeft = painterResource(R.drawable.calendar),
            text = df.format(rangeState.selectedEndDateMillis ?: Date()),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 20.dp)
                .width(200.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RangePickerContents(
    rangeState: DateRangePickerState,
    df: DateFormat
) {
    DateRangePicker(
        state = rangeState,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.palette.Mantle,
            titleContentColor = AppTheme.palette.Overlay0,
            headlineContentColor = AppTheme.palette.Text,
            weekdayContentColor = AppTheme.palette.Mauve,
            dayContentColor = AppTheme.palette.Text,
            disabledDayContentColor = AppTheme.palette.Overlay1,
            selectedDayContentColor = AppTheme.palette.Base,
            selectedDayContainerColor = AppTheme.palette.Mauve,
            todayContentColor = AppTheme.palette.Peach,
            todayDateBorderColor = AppTheme.palette.Peach,
            dayInSelectionRangeContentColor = AppTheme.palette.Crust,
            dayInSelectionRangeContainerColor = AppTheme.palette.Pink,
            dividerColor = AppTheme.palette.Mauve,
            subheadContentColor = AppTheme.palette.Text,
            yearContentColor = AppTheme.palette.Text,
            navigationContentColor = AppTheme.palette.Text,
            dateTextFieldColors = TextFieldDefaults.colors(
                cursorColor = AppTheme.palette.Text,
                focusedTextColor = AppTheme.palette.Text,
                focusedContainerColor = AppTheme.palette.Mantle,
                focusedLabelColor = AppTheme.palette.Subtext0,

                unfocusedTextColor = AppTheme.palette.Text,
                unfocusedContainerColor = AppTheme.palette.Mantle,
                unfocusedLabelColor = AppTheme.palette.Overlay0,
                unfocusedPlaceholderColor = AppTheme.palette.Surface1,

                errorTextColor = AppTheme.palette.Red,
                errorCursorColor = AppTheme.palette.Red,
                errorContainerColor = AppTheme.palette.Mantle,
                errorLabelColor = AppTheme.palette.Red
            )
        ),
        title = {
            Text(
                text = "Select Dates",
                textAlign = TextAlign.Center,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
            )
        },
        headline = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Text(
                    text = if (rangeState.selectedStartDateMillis != null)
                        df.format(rangeState.selectedStartDateMillis)
                    else
                        "...",
                    style = Typography.titleLarge
                )
                Text(
                    text = "to",
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = if (rangeState.selectedEndDateMillis != null)
                        df.format(rangeState.selectedEndDateMillis)
                    else
                        "...",
                    style = Typography.titleLarge
                )
            }
        },
    )
}

@Composable
fun ClearTextInput(
    value: String,
    hint: String,
    style: TextStyle = Typography.displayMedium,
    color: Color = AppTheme.palette.Text,
    hintColor: Color = AppTheme.palette.Subtext1,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf(value) }
    var isEditable by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val view = LocalView.current

    LaunchedEffect(isEditable) {
        if (isEditable) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            focusRequester.freeFocus()
            keyboardController?.hide()
        }
    }

    DisposableEffect(view) {
        val rootView = view.rootView
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight < screenHeight * 0.15) {
                isEditable = false
            }
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                maxLines = 1,
                singleLine = true,
                enabled = isEditable,
                readOnly = !isEditable,
                textStyle = style.merge(color = color),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isEditable = false
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            Text(
                text = hint,
                fontSize = 12.sp,
                color = hintColor
            )
        }

        Text(
            text = "Edit",
            color = AppTheme.palette.Sky,
            style = Typography.headlineMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.clickable {
                isEditable = true
                text = ""
            }
        )
    }
}

@Composable
fun SimpleOutlinedInput(
    value: String,
    style: TextStyle = Typography.titleLarge,
    password: Boolean = false,
    color: Color = AppTheme.palette.Text,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = AppTheme.palette.Text,
                shape = RoundedCornerShape(20)
            )
            .padding(0.dp, 0.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (!password) VisualTransformation.None else
                PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppTheme.palette.Subtext1,
                    style = style,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(0.dp)
                        .background(Color.Transparent)
                        .fillMaxWidth()
                )
            },
            textStyle = style.merge(TextStyle(color = color)),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
            modifier = Modifier
                .padding(0.dp)
                .background(color = Color.Transparent)
                .align(Alignment.Center)
                .fillMaxWidth()
        )
    }
}

@Composable
fun BulledItem(
    value: Boolean,
    onToggle: () -> Unit,
    size: Dp = 20.dp,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(10)

    Box(
        modifier = modifier
            .size(size)
            .background(if (value) AppTheme.palette.Sky else AppTheme.palette.Mantle, shape)
            .border(1.dp, AppTheme.palette.Text, shape = shape)
            .clip(shape)
            .clickable { onToggle() }
    )
}