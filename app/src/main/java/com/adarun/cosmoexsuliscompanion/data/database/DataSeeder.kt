package com.adarun.cosmoexsuliscompanion.data.database

import android.content.Context
import android.util.Log
import com.adarun.cosmoexsuliscompanion.data.database.dto.*
import com.adarun.cosmoexsuliscompanion.util.JsonLoader
import androidx.core.content.edit

object DataSeeder {

    suspend fun Seed (context: Context, db: AppDatabase) {

        val prefs = context.getSharedPreferences("seed", Context.MODE_PRIVATE)
        val alreadySeeded = prefs.getBoolean("alphaContentLoaded", false)

        if (alreadySeeded) return

        Log.d("SEED", "Seeding database...")

        // ===== Equipment =====
        val equipmentDto = JsonLoader.loadList<EquipmentDto>(context, "data/equipment_alpha.json")
        val equipment = equipmentDto.map { it.toEntity() }

        db.equipmentDao().insertAll(equipment)

        // ===== Actions =====
        val actionDto = JsonLoader.loadList<ActionDto>(context, "data/actions_alpha.json")
        val actions = actionDto.map { it.toEntity() }

        db.actionDao().insertAll(actions)

        prefs.edit { putBoolean("alphaContentLoaded", true) }

        Log.d("SEED", "Seeding complete")
    }
}