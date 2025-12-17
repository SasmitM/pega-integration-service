package com.pega.integration.model.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExternalApiResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)