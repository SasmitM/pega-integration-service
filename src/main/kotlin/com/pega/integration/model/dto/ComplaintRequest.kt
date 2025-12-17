package com.pega.integration.model.dto

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class ComplaintRequest(
    @field:NotBlank
    val customerId: String,

    @field:NotBlank
    val complaintType: String,

    @field:NotBlank
    val priority: String,

    @field:NotBlank
    val description: String,

    @field:NotBlank
    val sourceSystem: String
)