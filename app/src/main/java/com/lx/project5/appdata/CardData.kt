package com.lx.project5.appdata

import com.lx.project5.R

class CardData {
    var cardName:String? = null
    var cardEmail:String? = null
    var cardScope:String? = null
    var cardAddress:String? = null
    var cardDetail:String? = null
    var cardTag:String? = null
    var cardImage:Int? = null
    var cardName1:String? = null
    var cardTitle:String? = null
    var cardContent:String? = null
    var cardDog:String? = null
    var cardImage1:Int? = null

    fun doCard(cardData: CardData) {
        when(cardData.cardEmail){
            "qwe@gmail.com"->
                cardName = "김혁진"
            "qwer@gmail.com"-> cardName = "김현문"
            "example@gmail.com"-> cardName = "이기현"
            "gurwls123450@gmail.com"-> cardName = "정유진"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com"->
                cardName1 = "김혁진"
            "qwer@gmail.com"-> cardName1 = "김현문"
            "example@gmail.com"-> cardName1 = "이기현"
            "gurwls123450@gmail.com"-> cardName1 = "정유진"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com"->
                cardScope = "★★★★"
            "qwer@gmail.com"-> cardScope = "★★"
            "example@gmail.com"-> cardScope = "★★★★★"
            "gurwls123450@gmail.com"-> cardScope = "★★★"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardAddress = "경기도 안양시 동안구"
            "example@gmail.com"-> cardAddress = "서울특별시 강남구 언주로 7"
            "qwer@gmail.com" ->
                cardAddress = "정주시 상당구 용암동"
            "gurwls123450@gmail.com"-> cardAddress = "서울특별시 동작구 국사봉길 143"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardDetail = ""
            "example@gmail.com"-> cardDetail = ""
            "qwer@gmail.com" ->
                cardDetail = ""
            "gurwls123450@gmail.com"-> cardDetail = ""
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardDog = "용혁문"
            "example@gmail.com"-> cardDog = "용혁문"
            "qwer@gmail.com" ->
                cardDog = "용혁문"
            "gurwls123450@gmail.com"-> cardDog = "용혁문"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardTitle = "안녕"
            "example@gmail.com"-> cardTitle = "강아지"
            "qwer@gmail.com" ->
                cardTitle = "얍"
            "gurwls123450@gmail.com"-> cardTitle = "고양이"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardContent = "안녕"
            "example@gmail.com"-> cardContent = "멍멍"
            "qwer@gmail.com" ->
                cardContent = "얍"
            "gurwls123450@gmail.com"-> cardContent = "야옹"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardTag = ""
            "example@gmail.com" ->
                cardTag = ""
            "qwer@gmail.com" ->
                cardTag = ""
            "gurwls123450@gmail.com" ->
                cardTag = ""

        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardImage= R.drawable.profile_1
            "gurwls123450@gmail.com" ->
                cardImage= R.drawable.profile_2
            "example@gmail.com" ->
                cardImage= R.drawable.profile_3
            "qwer@gmail.com" ->
                cardImage= R.drawable.profile_4
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardImage1= R.drawable.profile_1
            "gurwls123450@gmail.com" ->
                cardImage1= R.drawable.profile_2
            "example@gmail.com" ->
                cardImage1= R.drawable.profile_3
            "qwer@gmail.com" ->
                cardImage1= R.drawable.profile_4
        }
    }
}
