package com.withplum.yourheroes.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import java.security.MessageDigest
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    init {
        System.loadLibrary(API_SECRET_FILE)
    }

    private external fun getBatman(): String
    private external fun getBruceWayne(): String

    override fun intercept(chain: Chain): Response {
        val batman = getBatman()
        val bruce = getBruceWayne()
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val builder = chain.request().url.newBuilder()
        val url = builder.addQueryParameter(API_KEY, bruce)
                .addQueryParameter("hash", "$timestamp$batman$bruce".toMD5())
                .addQueryParameter("ts", timestamp)
                .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

private const val API_KEY = "apikey"
private const val API_SECRET_FILE = "secret"