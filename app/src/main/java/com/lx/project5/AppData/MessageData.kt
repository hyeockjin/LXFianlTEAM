package com.lx.project5.AppData

data class MessageData(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
