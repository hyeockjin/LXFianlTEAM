package com.lx.data


import com.google.gson.annotations.SerializedName

data class AwrListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("header")
    val header: Header,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("assignContent")
        val assignContent: String,
        @SerializedName("assignTitle")
        val assignTitle: String,
        @SerializedName("awrn")
        val awrn: String,
        @SerializedName("endTime")
        val endTime: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double,
        @SerializedName("memberNo")
        val memberNo: String,
        @SerializedName("startTime")
        val startTime: String,
        @SerializedName("write_time")
        val writeTime: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}