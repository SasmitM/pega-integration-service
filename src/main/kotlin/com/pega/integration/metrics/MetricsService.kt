package com.pega.integration.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import jakarta.inject.Singleton
import java.time.Duration

@Singleton
class MetricsService(private val meterRegistry: MeterRegistry) {

    private val requestCounter: Counter = Counter.builder("integration.requests.total")
        .description("Total number of integration requests")
        .register(meterRegistry)

    private val failureCounter: Counter = Counter.builder("integration.failures.total")
        .description("Total number of integration failures")
        .register(meterRegistry)

    private val externalApiLatencyTimer: Timer = Timer.builder("external.api.latency.seconds")
        .description("External API call latency")
        .register(meterRegistry)

    private val transformationErrorCounter: Counter = Counter.builder("json.transformation.errors.total")
        .description("JSON transformation errors")
        .register(meterRegistry)

    fun recordRequest() {
        requestCounter.increment()
    }

    fun recordFailure() {
        failureCounter.increment()
    }

    fun recordExternalApiLatency(duration: Duration) {
        externalApiLatencyTimer.record(duration)
    }

    fun recordTransformationError() {
        transformationErrorCounter.increment()
    }

    fun <T> recordExternalApiCall(block: () -> T): T {
        val startTime = System.nanoTime()
        try {
            return block()
        } finally {
            val duration = Duration.ofNanos(System.nanoTime() - startTime)
            recordExternalApiLatency(duration)
        }
    }
}