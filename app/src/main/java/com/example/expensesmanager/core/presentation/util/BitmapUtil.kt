package com.example.expensesmanager.core.presentation.util

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun rememberBitmapFromBytes(byteArray: ByteArray?): ImageBitmap? {
    return remember(byteArray) {
        if (byteArray != null) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        } else {
            null
        }
    }
}

fun Context.createImageUri(): Uri? {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageName = "JPEG_$timestamp.jpg"
    val resolver = this.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}