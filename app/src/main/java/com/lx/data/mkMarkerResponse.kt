package com.lx.data


import com.google.gson.annotations.SerializedName

data class mkMarkerResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("mkId")
        val mkId: String,
        @SerializedName("mkNo")
        val mkNo: Int,
        @SerializedName("mkX")
        val mkX: Double,
        @SerializedName("mkY")
        val mkY: Double
    )
}