package com.smilekbear.portfolio.controller

import com.smilekbear.portfolio.dto.PresignResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.UUID

@RestController
@RequestMapping("/api/upload")
class UploadController (
    @Value("\${app.s3.bucket}") private val bucket: String,
    @Value("\${app.s3.region}") private val region: String,
    @Value("\${app.s3.prefix}") private val defaultPrefix: String,
){

    private val preSigner: S3Presigner by lazy {
        S3Presigner.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }

    @GetMapping("/presign")
    fun presign(
        @RequestParam contentType : String,
        @RequestParam(required = false) fileName: String?,
        @RequestParam(required = false, defaultValue = "") folder: String,
        @RequestParam(required = false, defaultValue = "600") expiresInSeconds: Long,
    ): ResponseEntity<PresignResponse> {
        val safeExpires = expiresInSeconds.coerceIn(60, 3600)

        val key = buildKey(
            folder = folder,
            defaultPrefix = defaultPrefix,
            fileName = fileName,
            contentType = contentType,
        )

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
//            .acl(ObjectCannedACL.PUBLIC_READ)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(safeExpires))
            .putObjectRequest(putObjectRequest)
            .build()
        val presigned: PresignedPutObjectRequest = preSigner.presignPutObject(presignRequest)

        val publicUrl = "https://${bucket}.s3.${region}.amazonaws.com/${key}"

        return ResponseEntity.ok(
            PresignResponse(
                key = key,
                uploadUrl = presigned.url().toString(),
                publicUrl = publicUrl,
                method = "PUT",
                contentType = contentType,
                expiresInSeconds = safeExpires,
            )
        )
    }

    private fun buildKey(
        folder: String,
        defaultPrefix: String,
        fileName: String?,
        contentType: String,
    ): String {
        val cleanFolder = folder.trim().trim('/').takeIf { it.isNotBlank() }
        val cleanDefaultPrefix = defaultPrefix.trim().trim('/').takeIf { it.isNotBlank() }

        val prefix = listOfNotNull(cleanDefaultPrefix, cleanFolder)
            .joinToString("/")
            .takeIf { it.isNotBlank() }

        val ext = guessExtension(fileName, contentType)
        val uuid = UUID.randomUUID().toString()

        return if(prefix.isNullOrBlank()){
            "$uuid$ext"
        }else{
            "$prefix/$uuid$ext"
        }
    }

    private fun guessExtension(
        fileName: String? = null,
        contentType: String,
    ):String {
        val fromName = fileName
            ?.trim()
            ?.substringAfterLast(".", missingDelimiterValue = "")
            ?.lowercase()
            ?.takeIf { it.isNotBlank() }

        if(fromName != null){
            return ".$fromName"
        }

        return  when (contentType.lowercase()){
            "image/png" -> ".image/png"
            "image/jpeg" , ".image/jpg"-> ".jpg"
            "image/webp" -> ".webp"
            "image/gif" -> ".gif"
            "image/svg+xml" -> ".svg"
            else -> ""
        }
    }
}