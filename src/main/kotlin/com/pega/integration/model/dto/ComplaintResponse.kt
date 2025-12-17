package com.pega.integration.model.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ComplaintResponse(
    val ticketId: String,
    val status: String,
    val customerId: String,
    val complaintType: String,
    val priority: String,
    val externalSystemResponse: ExternalSystemData?,
    val processedAt: String,
    val message: String
)

@Serdeable
data class ExternalSystemData(
    val externalId: Int,
    val externalTitle: String,
    val externalBody: String
)