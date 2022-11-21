package com.lx.project5.appdata

class CardData {
    var cardName:String? = null
    var cardScope:String? = null
    var cardAddress:String? = null
    var cardDitail:String? = null
    var cardTag:String? = null
    var cardImage:String? = null

    fun doCard(cardData: CardData) {
        when(cardData.cardName){
            "qwe@gmail.com"->
                cardScope = "★★★★"
            "qwer@gmail.com"-> cardScope = "★★"
        }
        when(cardData.cardName){
            "qwe@gmail.com" ->
                cardAddress = "서울특별시 강남구 언주로 7"
            "qwer@gmail.com" ->
                cardAddress = "서울특별시 강남구 언주로 7"
        }
        when(cardData.cardName){
            "qwe@gmail.com" ->
                cardDitail = ""
            "qwer@gmail.com" ->
                cardDitail = ""
        }
        when(cardData.cardTag){
            "qwe@gmail.com" ->
                cardTag = ""
            "qwer@gmail.com" ->
                cardTag = ""
        }
        when(cardData.cardImage){
            "qwe@gmail.com" ->
                cardImage= "R.drawable.@@@@@.png"
            "qwer@gmail.com" ->
                cardImage= ""
        }
    }
}
