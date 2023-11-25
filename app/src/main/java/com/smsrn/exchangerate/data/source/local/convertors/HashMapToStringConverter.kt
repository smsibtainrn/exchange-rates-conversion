package com.smsrn.exchangerate.data.source.local.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by Sibtain Raza on 12/26/2022.
 * sibtain.raza@10pearls.com
 */

object HashMapToStringConverter {

    @TypeConverter
    @JvmStatic
    fun fromJson(json: String): TreeMap<String, Double> {
        return Gson().fromJson(json, object : TypeToken<TreeMap<String, Double>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun toJson(rates: TreeMap<String, Double>): String {
        return Gson().toJson(rates)
    }
}