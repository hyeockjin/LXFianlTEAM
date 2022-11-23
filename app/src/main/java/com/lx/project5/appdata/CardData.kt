package com.lx.project5.appdata

import com.lx.project5.R

class CardData {
    var cardName:String? = null
    var cardEmail:String? = null
    var cardScope:String? = null
    var cardAddress:String? = null
    var cardDitail:String? = null
    var cardTag:String? = null
    var cardImage:Int? = null

    fun doCard(cardData: CardData) {
        when(cardData.cardEmail){
            "qwe@gmail.com"->
                cardName = "김혁진"
            "qwer@gmail.com"-> cardName = "김현문"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com"->
                cardScope = "★★★★"
            "qwer@gmail.com"-> cardScope = "★★"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardAddress = "서울특별시 강남구 언주로 7"
            "qwer@gmail.com" ->
                cardAddress = "서울특별시 강남구 언주로 7"
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardDitail = ""
            "qwer@gmail.com" ->
                cardDitail = ""
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardTag = ""
            "qwer@gmail.com" ->
                cardTag = ""
        }
        when(cardData.cardEmail){
            "qwe@gmail.com" ->
                cardImage= R.drawable.cardimage1
            "qwer@gmail.com" ->
                cardImage= R.drawable.cardimage2
        }
    }
}
