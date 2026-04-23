package com.adarun.cosmoexsuliscompanion.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adarun.cosmoexsuliscompanion.data.converters.Converters
import com.adarun.cosmoexsuliscompanion.data.dao.ActionDao
import com.adarun.cosmoexsuliscompanion.data.dao.CharacterActionXrefDao
import com.adarun.cosmoexsuliscompanion.data.dao.CharacterCombatSaveDao
import com.adarun.cosmoexsuliscompanion.data.dao.CharacterDao
import com.adarun.cosmoexsuliscompanion.data.dao.CombatInstanceDao
import com.adarun.cosmoexsuliscompanion.data.dao.EquipmentDao
import com.adarun.cosmoexsuliscompanion.data.dao.InstanceDao
import com.adarun.cosmoexsuliscompanion.data.model.Action
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterActionXref
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance
import com.adarun.cosmoexsuliscompanion.data.model.Equipment
import com.adarun.cosmoexsuliscompanion.data.model.Instance

@Database (
    entities = [
        Instance::class,
        Character::class,
        CombatInstance::class,
        CharacterCombatSave::class,
        Action::class,
        Equipment::class,
        CharacterActionXref::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters (Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun instanceDao(): InstanceDao
    abstract fun characterDao(): CharacterDao
    abstract fun combatInstanceDao(): CombatInstanceDao
    abstract fun characterCombatSaveDao(): CharacterCombatSaveDao
    abstract fun actionDao(): ActionDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun characterActionXrefDao(): CharacterActionXrefDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getDatabase (context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cosmoexsuli_database"
                ).build()
            }
            return INSTANCE!!
        }
    }
}