package com.example.ifoodclone.helper

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.example.ifoodclone.models.FoodModel
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class TinyDB(context: Context) {

    private var preferences: SharedPreferences? = null
    private var DEFAULT_APP_IMAGEDATA_DIRECTORY: String? = null
    private var lastImagePath = ""

    init {
        preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }


    fun getImage(path: String?): Bitmap? {
        var bitmapFromPath: Bitmap? = null
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmapFromPath
    }

    fun getSavedImagePath(): String? {
        return lastImagePath
    }

    fun putImage(theFolder: String?, theImageName: String?, theBitmap: Bitmap?): String? {

        if (theFolder == null || theImageName == null || theBitmap == null) return null
        DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder
        val mFullPath: String = setupFullPath(theImageName)
        if (mFullPath != "") {
            lastImagePath = mFullPath
            saveBitmap(mFullPath, theBitmap)
        }
        return mFullPath
    }

    private fun saveBitmap(fullPath: String?, bitmap: Bitmap?): Boolean {
        if (fullPath == null || bitmap == null) return false
        var fileCreated = false
        var bitmapCompressed: Boolean
        var streamClosed = false
        val imageFile = File(fullPath)
        if (imageFile.exists()) if (!imageFile.delete()) return false
        try {
            fileCreated = imageFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(imageFile)
            bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            bitmapCompressed = false
        } finally {
            if (out != null) {
                try {
                    out.flush()
                    out.close()
                    streamClosed = true
                } catch (e: IOException) {
                    e.printStackTrace()
                    streamClosed = false
                }
            }
        }
        return fileCreated && bitmapCompressed && streamClosed
    }


    private fun setupFullPath(imageName: String): String {
        val mFolder = File(Environment.getExternalStorageState(), DEFAULT_APP_IMAGEDATA_DIRECTORY
                ?: "Null")

        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.e("ERROR", "Failed to setup folder")
                return ""
            }
        }
        return mFolder.path + '/' + imageName
    }

    private fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    fun putImageWithFullPath(fullPath: String?, theBitmap: Bitmap?): Boolean {
        return !(fullPath == null || theBitmap == null) && saveBitmap(fullPath, theBitmap)
    }

    // Getters
    fun getInt(key: String?): Int? {
        return preferences?.getInt(key, 0)
    }

    fun getListInt(key: String?): ArrayList<Int> {
        val myList = TextUtils.split(preferences?.getString(key, ""), "‚‗‚")
        val arrayToList = ArrayList(Arrays.asList(*myList))
        val newList = ArrayList<Int>()
        for (item in arrayToList) newList.add(item.toInt())
        return newList
    }

    fun getLong(key: String?): Long? {
        return preferences?.getLong(key, 0)
    }

    fun getFloat(key: String?): Float? {
        return preferences?.getFloat(key, 0f)
    }

    fun getDouble(key: String?): Double? {
        val number: String? = getString(key)
        return try {
            number?.toDouble()
        } catch (e: NumberFormatException) {
            return 0.0
        }
    }

    private fun getString(key: String?): String? {
        return preferences?.getString(key, "")
    }

    fun getListDouble(key: String?): ArrayList<Double> {
        val myList = TextUtils.split(preferences?.getString(key, ""), "‚‗‚")
        val arrayToList = ArrayList(listOf(*myList))
        val newList = ArrayList<Double>()
        for (item in arrayToList) newList.add(item.toDouble())
        return newList
    }

    fun getListLong(key: String?): ArrayList<Long> {
        val myList = TextUtils.split(preferences?.getString(key, ""), "‚‗‚")
        val arrayToList = ArrayList(listOf(*myList))
        val newList = ArrayList<Long>()
        for (item in arrayToList) newList.add(item.toLong())
        return newList
    }

    private fun getListString(key: String?): ArrayList<String>? {
        return ArrayList(listOf(*TextUtils.split(preferences?.getString(key, ""), "‚‗‚")))
    }

    fun getBoolean(key: String?): Boolean {
        return preferences!!.getBoolean(key, false)
    }

    fun getListBoolean(key: String?): ArrayList<Boolean>? {
        val myList = getListString(key)
        val newList = ArrayList<Boolean>()
        for (item in myList!!) {
            newList.add(item == "true")
        }
        return newList
    }

    fun getListObject(key: String?): ArrayList<FoodModel>? {
        val gson = Gson()
        val objStrings = getListString(key)
        val playerList: ArrayList<FoodModel> = ArrayList<FoodModel>()
        for (jObjString in objStrings!!) {
            val player: FoodModel = gson.fromJson(jObjString, FoodModel::class.java)
            playerList.add(player)
        }
        return playerList
    }

    fun <T> getObject(key: String?, classOfT: Class<T>?): T {
        val json = getString(key)
        return Gson().fromJson(json, classOfT) ?: throw NullPointerException()
    }

    // Put methods


    fun putInt(key: String?, value: Int) {
        checkForNullKey(key)
        preferences?.edit()?.putInt(key, value)?.apply()
    }


    fun putListInt(key: String?, intList: ArrayList<Int>) {
        checkForNullKey(key)
        val myIntList = intList.toTypedArray()
        preferences?.edit()?.putString(key, TextUtils.join("‚‗‚", myIntList))?.apply()
    }

    private fun checkForNullKey(key: String?) {
        if (key == null) {
            throw java.lang.NullPointerException()
        }
    }

    fun putListObject(key: String?, playerList: ArrayList<FoodModel>) {
        checkForNullKey(key)
        val gson = Gson()
        val objStrings = ArrayList<String>()
        for (player in playerList) {
            objStrings.add(gson.toJson(player))
        }
        putListString(key, objStrings)
    }

    private fun putListString(key: String?, stringList: ArrayList<String>) {
        checkForNullKey(key)
        val myStringList = stringList.toTypedArray()
        preferences?.edit()?.putString(key, TextUtils.join("‚‗‚", myStringList))?.apply()
    }

}
