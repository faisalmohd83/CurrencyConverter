package com.fm.exchange.model

/**
 *
 */
data class Currency(
    val code: String?,
    val name: String?,
    val rate: Double? = 0.00,
    val flag_url: String?
)
