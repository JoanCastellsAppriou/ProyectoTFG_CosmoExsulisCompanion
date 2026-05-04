package com.adarun.cosmoexsuliscompanion.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonLoader {

    inline fun <reified T> loadList(context: Context, fileName: String): List<T> {
        Log.d("SEED", "Procesando Fichero $fileName")

        val json = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(json, type)
    }
}