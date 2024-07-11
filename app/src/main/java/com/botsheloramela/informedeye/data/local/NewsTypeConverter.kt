package com.botsheloramela.informedeye.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.botsheloramela.informedeye.domain.model.Source

/**
 * Type converter for the Source class
 */
@ProvidedTypeConverter
class NewsTypeConverter {
     @TypeConverter
     fun fromSource(source: Source): String {
         return "${source.id},${source.name}"
     }

     @TypeConverter
     fun toSource(source: String): Source {
         return source.split(",").let {sourceArray ->
             Source(id = sourceArray[0], name = sourceArray[1])
         }
     }
}