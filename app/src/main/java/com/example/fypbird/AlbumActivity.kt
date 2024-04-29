package com.example.fypbird

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: SQLLiteHelper
    private lateinit var recyclerView: RecyclerView

    private var adapter: AlbumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        sqliteHelper = SQLLiteHelper(this)

        recyclerView = findViewById(R.id.ir_photo)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AlbumAdapter(this)

        recyclerView.adapter = adapter

        val picList = sqliteHelper.getAllPic()
        adapter?.addItems(picList)

        adapter?.setOnClickDeleteItem {
            deletePic(it.id)
        }


    }

    //delete pic
    private fun deletePic(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _ ->
            sqliteHelper.deletePicById(id)
            val picList = sqliteHelper.getAllPic()
            adapter?.addItems(picList)
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    fun backAlbum(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}