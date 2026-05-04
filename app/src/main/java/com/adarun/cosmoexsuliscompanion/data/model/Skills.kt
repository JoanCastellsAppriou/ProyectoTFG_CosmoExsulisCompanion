package com.adarun.cosmoexsuliscompanion.data.model

data class Skills(
    val strength: Int = 0,
    val agility: Int = 0,
    val resilience: Int = 0,
    val grit: Int = 0,
    val conviction: Int = 0,
    val logic: Int = 0
) {
    fun getLevel(): Int {
        return totalPoints() - 29
    }

    fun isValid(): Boolean{
        return totalPoints() > 29
    }

    fun totalPoints(): Int {
        return  strength +
                agility +
                resilience +
                grit +
                conviction +
                logic
    }
}