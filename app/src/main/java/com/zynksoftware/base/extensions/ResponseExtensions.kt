package com.zynksoftware.base.extensions

import okhttp3.Request
import okhttp3.Response
import okio.GzipSource
import java.nio.charset.Charset

val Response.retryCount: Int
    get() {
        var currentResponse = priorResponse
        var result = 0
        while (currentResponse != null) {
            result++
            currentResponse = currentResponse.priorResponse
        }
        return result
    }

fun Response.createSignedRequest(tokenEvenIfExpired: String): Request? = try {
    request.signWithToken(tokenEvenIfExpired)
} catch (error: Throwable) {
    err("Failed to re-sign request")
    null
}

fun Response.serverError(): String? {
    val responseBody = body ?: return null
    val contentLength = responseBody.contentLength()

    if (contentLength == 0L) {
        return null
    }

    val source = responseBody.source()
    source.request(Long.MAX_VALUE) // Buffer the entire body.
    var buffer = source.buffer
    val headers = headers

    if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
        var gzippedResponseBody: GzipSource? = null
        try {
            gzippedResponseBody = GzipSource(buffer.clone())
            buffer = okio.Buffer()
            buffer.writeAll(gzippedResponseBody)
        } finally {
            gzippedResponseBody?.close()
        }
    }

    val charset: Charset = responseBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
    return buffer.clone().readString(charset)
}
