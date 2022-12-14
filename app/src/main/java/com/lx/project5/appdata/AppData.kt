package com.lx.project5.appdata

import java.util.*


class AppData {
    companion object {
        val random = Random()
        val num = random.nextInt(1001)

        var filepath: String?=null
        // 로그인 상태와 로그인한 유저의 정보
        var memberData: MemberData?=null
        // navIndex : 메인엑티비티에서 맡길때인지 돌볼때인지 구분자
        var navIndex: Int? = null
        // 경로 하나의 번호
        var routeNo: Int = num
    }
}