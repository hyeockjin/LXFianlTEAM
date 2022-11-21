package com.lx.project5.appdata


class AppData {
    companion object {
        var filepath: String?=null
        // 로그인 상태와 로그인한 유저의 정보
        var memberData: MemberData?=null
        // navIndex : 메인엑티비티에서 맡길때인지 돌볼때인지 구분자
        var navIndex: Int? = null
        // 메인에서 마커 선택시 생기는 펫시터, 돌보미 정보
        var cardData :CardData? =null
    }
    //유진바보
}