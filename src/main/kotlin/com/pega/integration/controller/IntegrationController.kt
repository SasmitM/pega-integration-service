package com.pega.integration.controller

import com.pega.integration.model.dto.ComplaintRequest
import com.pega.integration.model.dto.ComplaintResponse
import com.pega.integration.service.ComplaintService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated

@Controller("/integrations")
@Validated
open class IntegrationController(
    private val complaintService: ComplaintService
) {

    @Post("/complaints")
    open suspend fun submitComplaint(@Body request: ComplaintRequest): ComplaintResponse {
        return complaintService.processComplaint(request)
    }
}