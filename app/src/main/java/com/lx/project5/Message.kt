package com.lx.project5

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
