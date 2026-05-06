package com.adarun.cosmoexsuliscompanion.ui.navigation

import android.util.Log

sealed class NavRoutes (val route: String) {
    object InstanceList : NavRoutes ("instance_list")

    object InstanceDetail : NavRoutes ("instance_detail/{instanceId}") {
        fun creationRoute (id: Int) = "instance_detail/$id"
    }

    object CreateCharacter : NavRoutes ("create_character/{instanceId}?charId={charId}") {
        fun creationRoute (instance: Int, character: Int? = null): String {
            Log.d("NEW/EDIT CHAR", "Route: create_character/$instance?charId=$character")
            return  if (character != null) "create_character/$instance?charId=$character"
                    else "create_character/$instance"
        }
    }

    object CharacterDetail: NavRoutes ("character_detail/{characterId}") {
        fun creationRoute (characterId: Int) = "character_detail/$characterId"
    }
}