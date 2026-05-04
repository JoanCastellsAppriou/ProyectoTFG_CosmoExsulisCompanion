package com.adarun.cosmoexsuliscompanion.data.converters

import androidx.room.TypeConverter
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.model.enums.EquipmentType
import com.adarun.cosmoexsuliscompanion.data.model.enums.SkillType
import com.adarun.cosmoexsuliscompanion.data.model.enums.TargetType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    // TargetType =======================================================================
    @TypeConverter
    fun fromTargetTypeList (value: List<TargetType>): String {
        return value.joinToString(separator = ",") {it.name}
    }

    @TypeConverter
    fun toTargetTypeList (value: String): List<TargetType> {
        return if (value.isBlank()) emptyList()
        else value.split(",").map { TargetType.valueOf(it) }
    }
    //===================================================================================

    // Enums ============================================================================
    @TypeConverter
    fun fromActionType (value: ActionType): String = value.name
    @TypeConverter
    fun toActionType (value: String): ActionType = ActionType.valueOf(value)

    @TypeConverter
    fun fromSkillType (value: SkillType): String = value.name
    @TypeConverter
    fun toSkillType (value: String): SkillType = SkillType.valueOf(value)

    @TypeConverter
    fun fromEquipmentType(value: EquipmentType): String = value.name
    @TypeConverter
    fun toEquipmentType(value: String): EquipmentType = EquipmentType.valueOf(value)
    //===================================================================================

    // Fechas ===========================================================================
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.format(formatter)
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it, formatter) }
    //===================================================================================
}