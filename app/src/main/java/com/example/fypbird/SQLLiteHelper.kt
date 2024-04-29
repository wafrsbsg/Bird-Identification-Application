package com.example.fypbird

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLLiteHelper(contenxt: Context) :
    SQLiteOpenHelper(contenxt, DATABASE_NAME, null, DATABASE_VERSION){
        companion object{
            private const val DATABASE_VERSION = 2
            private const val DATABASE_NAME = "bird.db"
            private const val TBL_PIC = "tbl_pic"
            private const val ID = "id"
            private const val BITMAP = "bitmap"
            private const val BIRDNAME = "birdname"
        }

    //create table
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblPic = ("CREATE TABLE " + TBL_PIC + "(" + ID + " INTEGER PRIMARY KEY, "
                + BITMAP + " TEXT, " + BIRDNAME + " TEXT " +")")
        db?.execSQL(createTblPic)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PIC")
        onCreate(db)
    }

    //insert pic in bird identification function
    fun insertPic(pic: PicModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, pic.id)
        contentValues.put(BITMAP, pic.bitmap)
        contentValues.put(BIRDNAME, pic.birdname)

        val success = db.insert(TBL_PIC, null, contentValues)
        db.close()
        return success
    }

    //show pic in album function
    @SuppressLint("Range")
    fun getAllPic(): ArrayList<PicModel> {
        val picList: ArrayList<PicModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PIC"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var bitmap: String
        var birdname: String

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                bitmap = cursor.getString(cursor.getColumnIndex("bitmap"))
                birdname = cursor.getString(cursor.getColumnIndex("birdname"))

                val pic = PicModel(id = id, bitmap = bitmap, birdname = birdname)
                picList.add(pic)
            } while (cursor.moveToNext())
        }
        return picList
    }


    //delete album function pic
    fun deletePicById(id:Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_PIC, "id=$id", null)
        db.close()
        return success
    }

}