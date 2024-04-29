package com.example.fypbird

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuideDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_detail)

        val tv_guidebirdname = findViewById<TextView>(R.id.tv_guidename)
        val tv_describe = findViewById<TextView>(R.id.tv_describe)
        val iv_guidebirdimg = findViewById<ImageView>(R.id.iv_guideimg)
        val btn_back = findViewById<Button>(R.id.btn_back)

        val birdname = intent.getStringExtra("birdname")
        val from = intent.getStringExtra("from")

        tv_guidebirdname!!.text = birdname

        if (birdname == "ABBOTTS BABBLER"){
            tv_describe!!.text = "Abbott's babbler (Malacocincla abbotti) is a " +
                    "species of bird in the family Pellorneidae. " +
                    "It is widely distributed along the Himalayas in South Asia and extending " +
                    "into the forests of Southeast Asia. " +
                    "They are short-tailed and stout birds which forage in pairs in " +
                    "dense undergrowth close to the ground and their presence is indicated by their distinctive calls."
            iv_guidebirdimg.setImageResource(R.drawable.abbotts_babbler)
        } else if (birdname == "ABBOTTS BOOBY"){
            tv_describe!!.text = "Abbott's booby (Papasula abbotti) is an " +
                    "endangered seabird of the sulid family, which includes gannets " +
                    "and boobies. It is a large booby and is placed within its own " +
                    "monotypic genus. It was first identified from a specimen collected " +
                    "by William Louis Abbott, who discovered it on Assumption Island in 1892."
            iv_guidebirdimg.setImageResource(R.drawable.abbotts_booby)
        } else if (birdname == "ABYSSINIAN GROUND HORNBILL"){
            tv_describe!!.text = "The Abyssinian ground hornbill or northern ground hornbill " +
                    "(Bucorvus abyssinicus) is an African bird, found north of the equator, " +
                    "and is one of two species of ground hornbill. It is the second largest " +
                    "species of African hornbill, only surpassed by the slightly larger southern ground hornbill."
            iv_guidebirdimg.setImageResource(R.drawable.abyssinian_ground_hornbill)
        } else if (birdname == "AFRICAN CROWNED CRANE"){
            tv_describe!!.text = "African crowned crane is a bird " +
                    "in the crane family, Gruidae. It is found in eastern " +
                    "and southern Africa, and is the national bird of Uganda."
            iv_guidebirdimg.setImageResource(R.drawable.african_crowned_crane)
        } else if (birdname == "AFRICAN EMERALD CUCKOO"){
            tv_describe!!.text = "The African emerald cuckoo is sexually " +
                    "dimorphic. The males have a green back and head with a " +
                    "yellow breast. Females are barred green and brown on their " +
                    "backs and green and white on their breasts. The African emerald " +
                    "cuckoo can also be identified by its call, a four-note whistle " +
                    "with the mnemonic device of “Hello Ju-dy.”"
            iv_guidebirdimg.setImageResource(R.drawable.african_emerald_cuckoo)
        }

        btn_back.setOnClickListener {
            if (from == "ID") {
                val intent = Intent(this, IdentificationActivity::class.java)
                startActivity(intent)
            } else if (from == "AB") {
                val intent = Intent(this, AlbumActivity::class.java)
                startActivity(intent)
            } else if (from == "GD") {
                val intent = Intent(this, GuideActivity::class.java)
                startActivity(intent)
            }
        }
        }
    }