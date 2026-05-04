package com.adarun.cosmoexsuliscompanion.data.database.dto

import android.util.Log
import com.adarun.cosmoexsuliscompanion.data.model.*
import com.adarun.cosmoexsuliscompanion.data.model.enums.*

fun EquipmentDto.toEntity(): Equipment {
    Log.d("SEED", "Procesando Equipo $name")
    return Equipment(
        equipmentCode = equipmentCode,
        name = name,
        type = EquipmentType.valueOf(type),
        linkedStat = linkedStat?.let { SkillType.valueOf(it) },
        diceCount = diceCount,
        strengthMod = strengthMod,
        agilityMod = agilityMod,
        resilienceMod = resilienceMod,
        gritMod = gritMod,
        convictionMod = convictionMod,
        logicMod = logicMod
    )
}

fun ActionDto.toEntity(): Action {
    Log.d("SEED", "Procesando Acción $name")
    return Action(
        actionCode = actionCode,
        name = name,
        skill = SkillType.valueOf(skill),
        reqLevel = reqLevel,
        diceThrows = diceThrows,
        type = ActionType.valueOf(type),
        behavior = BehaviorType.valueOf(behavior),
        allowedTarget = TargetType.valueOf(allowedTarget),
        thenEffect = thenEffect,
        refActionCode = refActionCode,
        targetSkill = targetSkill?.let { SkillType.valueOf(it) },
        selfPhysStrain = selfPhysStrain,
        selfMentStrain = selfMentStrain,
        otherPhysStrain = otherPhysStrain,
        otherMentStrain = otherMentStrain
    )
}