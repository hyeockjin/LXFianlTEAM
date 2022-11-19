package com.lx.project5.appData

data class MessageData(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
