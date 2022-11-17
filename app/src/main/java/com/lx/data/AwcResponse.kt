package com.lx.data


import com.google.gson.annotations.SerializedName

data class AwcResponse(
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
        @SerializedName("awcn")
        val awcn: Int,
        @SerializedName("awrn")
        val awrn: Int,
        @SerializedName("careNo")
        val careNo: Int,
        @SerializedName("coment")
        val comment: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}