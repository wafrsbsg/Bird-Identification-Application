package com.example.fypbird

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Home"
    }
    fun goToIdentification(view: View) {
        val intent = Intent(this, IdentificationActivity::class.java)
        startActivity(intent)
    }

    fun goToAlbum(view: View) {
        val intent = Intent(this, AlbumActivity::class.java)
        startActivity(intent)
    }

    fun goToGuide(view: View) {
        val intent = Intent(this, GuideActivity::class.java)
        startActivity(intent)
    }


}