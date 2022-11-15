package com.lx.data


import com.google.gson.annotations.SerializedName

data class AwrTodoResponse(
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
        @SerializedName("assign_write_request_no")
        val actn: Int,
        @SerializedName("assign_write_todo_no")
        val awtn: Int,
        @SerializedName("todo_no")
        val todoNo: Int
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}