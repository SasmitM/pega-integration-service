package com.pega.integration.service

import com.pega.integration.client.ExternalCrmClient
import com.pega.integration.metrics.MetricsService
import com.pega.integration.model.dto.ComplaintRequest
import com.pega.integration.model.dto.ComplaintResponse
import com.pega.integration.model.dto.ExternalSystemData
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

@Singleton
class ComplaintService(
    private val externalCrmClient: ExternalCrmClient,
    private val metricsService: MetricsService
) {
    private val logger = LoggerFactory.getLogger(ComplaintService::class.java)

    suspend fun processComplaint(request: ComplaintRequest): ComplaintResponse {
        metricsService.recordRequest()

        logger.info("Processing complaint for customer: ${request.customerId}, type: ${request.complaintType}")

        return try {
            // Generate a post ID from customer ID
            val postId = request.customerId.hashCode().rem(100).let { if (it < 1) 1 else it }

            logger.info("Calling external API with post ID: $postId")

            // Call external API - this is already a suspend function
            val startTime = System.nanoTime()
            val externalResponse = externalCrmClient.getComplaintDetails(postId)
            val duration = java.time.Duration.ofNanos(System.nanoTime() - startTime)
            metricsService.recordExternalApiLatency(duration)

            logger.info("Received external API response for complaint ID: ${externalResponse.id}")

            // Transform to internal format
            val ticketId = "TKT-${UUID.randomUUID().toString().take(8).uppercase()}"

            ComplaintResponse(
                ticketId = ticketId,
                status = "CREATED",
                customerId = request.customerId,
                complaintType = request.complaintType,
                priority = request.priority,
                externalSystemResponse = ExternalSystemData(
                    externalId = externalResponse.id,
                    externalTitle = externalResponse.title,
                    externalBody = externalResponse.body
                ),
                processedAt = Instant.now().toString(),
                message = "Complaint successfully processed and ticket created"
            )

        } catch (e: Exception) {
            logger.error("Failed to process complaint: ${e.message}", e)
            metricsService.recordFailure()
            metricsService.recordTransformationError()

            // Return error response
            ComplaintResponse(
                ticketId = "ERROR-${UUID.randomUUID().toString().take(8).uppercase()}",
                status = "FAILED",
                customerId = request.customerId,
                complaintType = request.complaintType,
                priority = request.priority,
                externalSystemResponse = null,
                processedAt = Instant.now().toString(),
                message = "Failed to process complaint: ${e.message}"
            )
        }
    }
}