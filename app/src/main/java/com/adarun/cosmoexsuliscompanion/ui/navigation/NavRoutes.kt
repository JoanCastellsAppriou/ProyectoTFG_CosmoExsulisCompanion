package com.adarun.cosmoexsuliscompanion.ui.navigation

sealed class NavRoutes (val route: String) {
    object InstanceList : NavRoutes ("instance_list")

    object InstanceDetail : NavRoutes ("instance_detail/{instanceId}") {
        fun creationRoute (id: Int) = "instance_detail/$id"
    }

    object CreateCharacter : NavRoutes ("create_character/{instanceId}") {
        fun creationRoute (id: Int) = "create_character/$id"
    }

    object CharacterDetail: NavRoutes ("character_detail/{characterId}") {
        fun creationRoute (characterId: Int) = "character_detail/$characterId"
    }
}