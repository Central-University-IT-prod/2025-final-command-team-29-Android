package ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import okhttp3.ResponseBody
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.repository.PartnerStatRepository
import java.io.File
import java.io.FileOutputStream

class GetPartnerStatFileUseCase(
    private val repository: PartnerStatRepository,
    private val sharedPrefs: SharedPreferences
) {
    suspend operator fun invoke(context: Context) {
        try {
            val partnerId = sharedPrefs.getString("partnerId", "")
                ?: throw IllegalStateException("Partner id is not set")
            val body = repository.getStatFile(partnerId = partnerId)
            save(body, context)
        } catch (_: Exception) {
        }
    }

    fun save(
        body: ResponseBody,
        context: Context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "data.xlsx")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/xlsx")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?.let {
                    context.contentResolver.openOutputStream(it)?.use { outputStream ->
                        body.byteStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
        } else {
            FileOutputStream(
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "data.xlsx"
                )
            ).use { out ->
                body.byteStream().use { inputStream ->
                    inputStream.copyTo(out)
                }
            }
        }
    }
}