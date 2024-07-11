package com.botsheloramela.informedeye.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Source(
    val id: String,
    val name: String
): Parcelable