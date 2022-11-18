package com.lx.project5

//##### Firebase 채팅 기능 구현 test 파일
data class Member(
    var name: String,
    var email: String,
    var uId:String
){
    constructor():this("","","")
}
