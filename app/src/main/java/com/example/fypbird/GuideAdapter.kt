package com.example.fypbird
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuideAdapter(var mList: List<GuideModel>, private val context: Context) :
    RecyclerView.Adapter<GuideAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_img : ImageView = itemView.findViewById(R.id.iv_img)
        val tv_birdname : TextView = itemView.findViewById(R.id.tv_birdname)
    }

    fun setFilteredList(mList: List<GuideModel>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        //Create new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_guide , parent , false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        //Get the contents of the element replacement view from GuideModel
        holder.iv_img.setImageResource(mList[position].guideimage)
        holder.tv_birdname.text = mList[position].guidename

        holder.itemView.setOnClickListener {
            val intent = Intent(context, GuideDetailActivity::class.java)
            intent.putExtra("birdname", holder.tv_birdname.text)
            intent.putExtra("from", "GD")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}