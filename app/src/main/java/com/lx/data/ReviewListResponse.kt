package com.lx.data


import com.google.gson.annotations.SerializedName

data class ReviewListResponse(
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
        @SerializedName("memberReviewNo")
        val memberReviewNo: String,
        @SerializedName("memberNo")
        val memberNo: String,
        @SerializedName("careNo")
        val careNo: String,
        @SerializedName("star")
        val star: String,
        @SerializedName("reviewTitle")
        val reviewTitle: String,
        @SerializedName("reviewContent")
        val reviewContent: String

    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}