package com.pega.integration.client

import com.pega.integration.model.dto.ExternalApiResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("\${external-api.base-url}")
interface ExternalCrmClient {

    @Get("/posts/{id}")
    suspend fun getComplaintDetails(id: Int): ExternalApiResponse
}