package com.smsrn.exchangerate.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smsrn.exchangerate.data.source.local.convertors.HashMapToStringConverter
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity


/**
 * Created by Sibtain Raza on 11/23/2023.
 * sraza@adnocdistribution.ae
 */

@Database(entities = [ExchangeRateEntity::class], version = 1, exportSchema = false)
@TypeConverters(HashMapToStringConverter::class)
abstract class DBExchangeRate : RoomDatabase() {

    abstract fun exchangeRatesDao(): ExchangeRatesDao
}