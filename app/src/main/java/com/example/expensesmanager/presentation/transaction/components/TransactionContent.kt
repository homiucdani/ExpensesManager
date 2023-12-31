package com.example.expensesmanager.presentation.transaction.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.expensesmanager.R
import com.example.expensesmanager.core.presentation.components.ShowDateTimePickers
import com.example.expensesmanager.core.presentation.util.createImageUri
import com.example.expensesmanager.core.presentation.util.rememberBitmapFromBytes
import com.example.expensesmanager.domain.model.TransactionCategory
import com.example.expensesmanager.ui.theme.DefaultGreen
import com.example.expensesmanager.ui.theme.dimens
import com.example.expensesmanager.util.toInstant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionContent(
    modifier: Modifier = Modifier,
    formattedDate: String,
    amount: String,
    details: String,
    selectedCategory: TransactionCategory,
    imageBytes: ByteArray?,
    onDateTimeChange: (ZonedDateTime) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (TransactionCategory) -> Unit,
    onDetailsChange: (String) -> Unit,
    onPictureTaken: (Uri?) -> Unit,
    onClearImage: () -> Unit,
) {

    val context = LocalContext.current

    val showDateDialog = remember {
        mutableStateOf(false)
    }

    val showTimeDialog = remember {
        mutableStateOf(false)
    }

    val currentDate = remember {
        mutableStateOf(LocalDate.now())
    }

    val currentTime = remember {
        mutableStateOf(LocalTime.now())
    }

    val dateState = rememberDatePickerState()
    val timeState = rememberTimePickerState()

    val imgUri by remember {
        mutableStateOf(context.createImageUri())
    }

    val cameraContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isPictureTaken ->
            imgUri?.let { uri ->
                onPictureTaken(uri)
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isPermissionGranted ->
            if (isPermissionGranted) {
                Toast.makeText(context, "Permission accepted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val image = rememberBitmapFromBytes(byteArray = imageBytes)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
    ) {
        Text(
            text = stringResource(R.string.date),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = formattedDate,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            placeholder = {
                Text(text = stringResource(R.string.add_date))
            },
            shape = RoundedCornerShape(MaterialTheme.dimens.small2),
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        showDateDialog.value = true
                    }
                ) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
            }
        )

        CustomTransactionField(
            title = stringResource(R.string.amount),
            text = amount,
            onTextChange = { amount ->
                onAmountChange(amount)
            },
            hint = stringResource(R.string.enter_amount),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Text(
            text = stringResource(R.string.category),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )

        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
            content = {
                items(
                    TransactionCategory.entries.toTypedArray()
                ) { transactionCategory ->
                    CategoryItem(
                        transactionCategory = transactionCategory,
                        selectedCategory = selectedCategory,
                        onCategoryClick = {
                            onCategoryChange(transactionCategory)
                        }
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTransactionField(
                title = stringResource(R.string.details),
                text = details,
                onTextChange = { details ->
                    onDetailsChange(details)
                },
                hint = stringResource(R.string.more_information)
            )
            Surface(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.medium2)
                    .size(40.dp),
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraContract.launch(imgUri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AnimatedContent(
                modifier = Modifier.weight(0.8f),
                targetState = image != null,
                label = ""
            ) { isImageNotNull ->
                if (isImageNotNull && image != null) {
                    Image(
                        bitmap = image,
                        contentDescription = null
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.image_placeholder),
                        contentDescription = null
                    )
                }
            }

            IconButton(
                modifier = Modifier.weight(0.2f),
                onClick = {
                    onClearImage()
                }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = DefaultGreen
                )
            }
        }
    }

    ShowDateTimePickers(
        showDateDialog = showDateDialog,
        showTimeDialog = showTimeDialog,
        dateTimeState = dateState,
        timePickerState = timeState,
        onDateSelected = { timestamp ->
            currentDate.value =
                LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()).toLocalDate()
        },
        onTimeSelected = { hour, minute ->
            currentTime.value = LocalTime.of(hour, minute)
            if (currentDate.value != null && currentTime.value != null) {
                onDateTimeChange(
                    ZonedDateTime.of(currentDate.value, currentTime.value, ZoneId.systemDefault())
                )
            }
        }
    )
}
