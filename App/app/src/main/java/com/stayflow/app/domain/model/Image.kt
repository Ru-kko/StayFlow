package com.stayflow.app.domain.model

import java.util.UUID

data class Image(
    val imageId: UUID,
    val contentType: String,
    val url: String,
    val name: String,
)