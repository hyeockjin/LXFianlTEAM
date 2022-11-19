package com.lx.project5.appdata

data class MessageData(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
