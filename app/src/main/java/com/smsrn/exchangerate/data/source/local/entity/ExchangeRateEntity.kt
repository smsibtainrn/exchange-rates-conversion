package com.smsrn.exchangerate.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.TreeMap

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity(
    @PrimaryKey
    val currencyCode: String,
    val rates: TreeMap<String, Double>
)