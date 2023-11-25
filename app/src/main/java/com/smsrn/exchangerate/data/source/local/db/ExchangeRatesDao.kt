package com.smsrn.exchangerate.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity

/**
 * Created by Sibtain Raza on 12/26/2022.
 * sibtain.raza@10pearls.com
 */

@Dao
interface ExchangeRatesDao {

    @Query("SELECT * FROM exchange_rates WHERE currencyCode = :currencyCode")
    fun getExchangeRates(currencyCode: String): ExchangeRateEntity

    @Query("SELECT EXISTS(SELECT * FROM exchange_rates where currencyCode = :currencyCode)")
    fun checkExchangeRateExist(currencyCode: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exchangeEntity: ExchangeRateEntity)

    @Delete
    fun delete(exchangeEntity: ExchangeRateEntity)
}