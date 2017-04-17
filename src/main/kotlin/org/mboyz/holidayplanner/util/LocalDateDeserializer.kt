package org.mboyz.holidayplanner.util

import com.fasterxml.jackson.core.JsonParser
import java.time.LocalDate
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import java.io.IOException
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class LocalDateDeserializer protected constructor() : StdDeserializer<LocalDate>(LocalDate::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): LocalDate {
        return LocalDate.parse(jp.readValueAs(String::class.java))
    }

    companion object {
        private val serialVersionUID = 1L
    }
}