package com.lx.project5.appdata

//##### Firebase 채팅 기능 구현 test 파일
data class ChatData(
    var name: String,
    var email: String,
    var uId:String,
    var memberImage:String
){
    constructor():this("","","","")
}
