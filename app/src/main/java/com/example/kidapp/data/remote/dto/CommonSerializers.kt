package com.example.kidapp.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val dateString = decoder.decodeString()
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val temporalAccessor = formatter.parseBest(dateString, OffsetDateTime::from, LocalDateTime::from)
            if (temporalAccessor is OffsetDateTime) {
                temporalAccessor.toLocalDateTime()
            } else {
                temporalAccessor as LocalDateTime
            }
        } catch (e: Exception) {
            LocalDateTime.parse(dateString)
        }
    }
}

