package com.example.fypbird

import android.content.Context
import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AlbumAdapter(private val context: Context) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){
    private  var picList: ArrayList<PicModel> = ArrayList()
    private var onClickDeleteItem: ((PicModel) -> Unit)? = null

    fun addItems(items: ArrayList<PicModel>) {
        this.picList = items
        notifyDataSetChanged()
    }

    fun setOnClickDeleteItem(callback:(PicModel)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= AlbumViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.list_album, parent, false)
    )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        //Get the contents of the element replacement view from PicModel
        val pic = picList[position]
        holder.bindView(pic)
        holder.btn_delete.setOnClickListener{
            onClickDeleteItem?.invoke(pic)
        }
        val imageByteArray: ByteArray = Base64.decode(picList[position].bitmap, Base64.DEFAULT)
        //Use Glide to load photo
        Glide.with(context).asBitmap().load(imageByteArray).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, GuideDetailActivity::class.java)
            intent.putExtra("birdname", pic.birdname)
            intent.putExtra("from", "AB")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return picList.size
    }

    class AlbumViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var birdname = view.findViewById<TextView>(R.id.tv_birdname)
        var image = view.findViewById<ImageView>(R.id.iv_img)
        var btn_delete = view.findViewById<Button>(R.id.btn_delete)

        fun bindView(pic:PicModel){
            birdname.text = pic.birdname
            //bitmap.text = pic.bitmap

            //val imageBytes = Base64.decode(pic.bitmap, 0)
            //val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        }


    }
}