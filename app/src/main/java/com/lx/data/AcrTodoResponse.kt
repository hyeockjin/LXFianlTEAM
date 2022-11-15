package com.lx.data


import com.google.gson.annotations.SerializedName

data class AcrTodoResponse(
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
        @SerializedName("assign_choice_request_no")
        val acrn: Int,
        @SerializedName("assign_choice_todo_no")
        val actn: Int,
        @SerializedName("todo_no")
        val todoNo: Int
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}