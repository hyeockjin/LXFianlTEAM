package com.lx.data


import com.google.gson.annotations.SerializedName

data class LastInsertResponse(
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
        @SerializedName("LAST_INSERT_ID()")
        val lASTINSERTID: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}