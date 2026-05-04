package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.ActionDao
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType

class ActionRepository (private val dao: ActionDao) {
    fun getAll() = dao.getAll()

    fun getByCode (code: String) = dao.getByCode(code)

    fun getMultipleByCode (codes: List<String>) = dao.getMultipleByCode(codes)
    fun getAllActions() = dao.getByType(ActionType.ACTION)
    fun getAllAbilities() = dao.getByType(ActionType.ABILITY)
}