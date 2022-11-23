package com.lx.data


import com.google.gson.annotations.SerializedName

data class RouteTrackingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("point")
        val point: Point,
        @SerializedName("route_no")
        val routeNo: Int,
        @SerializedName("routetracking_no")
        val routetrackingNo: Int
    ) {
        data class Point(
            @SerializedName("x")
            val x: Double,
            @SerializedName("y")
            val y: Double
        )
    }
}