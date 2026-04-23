package com.adarun.cosmoexsuliscompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.adarun.cosmoexsuliscompanion.data.database.AppDatabase
import com.adarun.cosmoexsuliscompanion.data.model.*
import com.adarun.cosmoexsuliscompanion.data.model.enums.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "DB_TEST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Si necesitas Compose UI después, usarás setContent { ... }
        // Para la prueba, no llamamos a setContentView.

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)

            // ─── 1. Crear una Instancia ───
            val instance = Instance(name = "Campaña de prueba", createdAt = LocalDateTime.now())
            val instanceId = db.instanceDao().insert(instance).toInt()
            Log.d(TAG, "Instancia creada con ID: $instanceId")

            // ─── 2. Crear Equipamiento ───
            val swordId = db.equipmentDao().insert(
                Equipment(
                    name = "Espada larga",
                    type = EquipmentType.WEAPON,
                    linkedStat = StatType.STRENGTH,
                    diceCount = 2,
                    strengthMod = 2
                )
            ).toInt()

            val shieldId = db.equipmentDao().insert(
                Equipment(
                    name = "Escudo de roble",
                    type = EquipmentType.ARMOR,
                    resilienceMod = 3,
                    gritMod = 1
                )
            ).toInt()
            Log.d(TAG, "Equipos: espada=$swordId, escudo=$shieldId")

            // ─── 3. Crear un Personaje ───
            val character = Character(
                instanceId = instanceId,
                name = "Aragorn",
                baseStats = Stats(strength = 18, agility = 15, resilience = 16),
                weaponId = swordId,
                armorId = shieldId
            )
            val charId = db.characterDao().insert(character).toInt()
            Log.d(TAG, "Personaje creado con ID: $charId")

            // ─── 4. Crear Acciones ───
            val slashId = db.actionDao().insert(
                Action(
                    name = "Tajo",
                    skill = StatType.AGILITY,
                    reqLevel = 1,
                    diceThrows = 2,
                    type = ActionType.DAMAGE,
                    allowedTarget = TargetType.OTHER,
                    targetSkill = StatType.STRENGTH,
                    selfPhysStrain = 0,
                    otherPhysStrain = null
                )
            ).toInt()

            val healId = db.actionDao().insert(
                Action(
                    name = "Curar heridas leves",
                    skill = StatType.CONVICTION,
                    reqLevel = 2,
                    diceThrows = 1,
                    type = ActionType.HEALING,
                    allowedTarget = TargetType.BOTH,
                    selfMentStrain = 1
                )
            ).toInt()
            Log.d(TAG, "Acciones: slash=$slashId, heal=$healId")

            // ─── 5. Asignar acciones al personaje ───
            db.characterActionXrefDao().insert(CharacterActionXref(charId, acId = slashId))
            db.characterActionXrefDao().insert(CharacterActionXref(charId, acId = healId))
            Log.d(TAG, "Acciones asignadas al personaje")

            // ─── 6. Crear un Combate y añadir salvados ───
            val combatId = db.combatInstanceDao().insert(
                CombatInstance(instanceId = instanceId)
            ).toInt()

            db.characterCombatSaveDao().insert(
                CharacterCombatSave(
                    charId = charId,
                    combatId = combatId,
                    health = 100,
                    temper = 50,
                    physStrain = 0,
                    mentStrain = 0,
                    modifiedStats = Stats(strength = 20, agility = 15, resilience = 16)
                )
            )
            Log.d(TAG, "Combate creado con ID: $combatId y save para personaje")

            // ═══════ VERIFICACIONES ═══════

            val instWithChars = db.instanceDao().getInstanceWithCharacters(instanceId)
            Log.d(TAG, "Instance '${instWithChars?.instance?.name}' tiene ${instWithChars?.characters?.size} personajes")

            val charWithEquip = db.characterDao().getCharacterWithEquipment(charId)
            Log.d(TAG, "Personaje '${charWithEquip?.character?.name}' equipa: arma='${charWithEquip?.weapon?.name}', armadura='${charWithEquip?.armor?.name}'")

            val charWithSaves = db.characterDao().getCharacterWithCombatSaves(charId)
            Log.d(TAG, "Personaje tiene ${charWithSaves?.combatSaves?.size} saves de combate")
            charWithSaves?.combatSaves?.forEach { save ->
                Log.d(TAG, "  Save: salud=${save.health}, temper=${save.temper}, modStr=${save.modifiedStats.strength}")
            }

            val combatWithP = db.combatInstanceDao().getCombatWithParticipants(combatId)
            Log.d(TAG, "Combate $combatId tiene ${combatWithP?.participants?.size} participantes")

            val charWithActions = db.characterDao().getCharacterWithActions(charId)
            Log.d(TAG, "Personaje tiene ${charWithActions?.actions?.size} acciones:")
            charWithActions?.actions?.forEach { action ->
                Log.d(TAG, "  - ${action.name} (${action.type}) -> objetivo: ${action.allowedTarget}")
            }

            Log.d(TAG, "¡PRUEBA COMPLETADA CON ÉXITO!")
        }
    }
}