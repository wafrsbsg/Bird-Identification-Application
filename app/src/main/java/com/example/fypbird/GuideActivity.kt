package com.example.fypbird

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class GuideActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<GuideModel>()
    private lateinit var adapter: GuideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = GuideAdapter(mList,this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<GuideModel>()
            for (i in mList) {
                if (i.guidename.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mList.add(GuideModel("ABBOTTS BABBLER", R.drawable.abbotts_babbler))
        mList.add(GuideModel("ABBOTTS BOOBY", R.drawable.abbotts_booby))
        mList.add(GuideModel("ABYSSINIAN GROUND HORNBILL", R.drawable.abyssinian_ground_hornbill))
        mList.add(GuideModel("AFRICAN CROWNED CRANE", R.drawable.african_crowned_crane))
        mList.add(GuideModel("AFRICAN EMERALD CUCKOO", R.drawable.african_emerald_cuckoo))
    }

    fun backGuide(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}