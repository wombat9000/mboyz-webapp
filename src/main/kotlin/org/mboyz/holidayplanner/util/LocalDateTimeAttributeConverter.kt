package org.mboyz.holidayplanner.util

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Suppress("UNUSED") // used by DAO when writing holidays into DB
@Converter(autoApply = true)
class LocalDateTimeAttributeConverter: AttributeConverter<LocalDateTime, Timestamp> {
    override fun convertToDatabaseColumn(locDate: LocalDateTime?): Timestamp? {
        return if (locDate == null) null else Timestamp.valueOf(locDate)
    }

    override fun convertToEntityAttribute(sqlDate: Timestamp?): LocalDateTime? {
        return sqlDate?.toLocalDateTime()
    }
}