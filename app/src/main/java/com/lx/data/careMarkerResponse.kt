package com.lx.data


import com.google.gson.annotations.SerializedName

data class careMarkerResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("careId")
        val careId: String,
        @SerializedName("careNo")
        val careNo: Int,
        @SerializedName("careX")
        val careX: Double,
        @SerializedName("careY")
        val careY: Double
    )
}