package com.lx.data


import com.google.gson.annotations.SerializedName

data class simpleMarkerResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double
    )
}