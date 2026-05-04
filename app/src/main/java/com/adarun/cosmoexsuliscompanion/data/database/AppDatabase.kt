package com.adarun.cosmoexsuliscompanion.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adarun.cosmoexsuliscompanion.data.converters.Converters
import com.adarun.cosmoexsuliscompanion.data.dao.*
import com.adarun.cosmoexsuliscompanion.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    version = 4,
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
        private var _instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            return _instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()

                _instance = instance

                CoroutineScope(Dispatchers.IO).launch {
                    DataSeeder.Seed(context, instance)
                }

                instance
            }
        }
    }
}