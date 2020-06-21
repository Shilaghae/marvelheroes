package com.withplum.yourheroes.main

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.withplum.yourheroes.R
import com.withplum.yourheroes.base.OnHeroSelectedListener
import com.withplum.yourheroes.main.MySquadAdapter.MySquadViewHolder
import com.withplum.yourheroes.util.getCroppedBitmap
import kotlinx.android.synthetic.main.mysquad_item.view.*

class MySquadAdapter(val listener: OnHeroSelectedListener) : RecyclerView.Adapter<MySquadViewHolder>() {

    private var mySquad: List<UiHero> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    inner class MySquadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setHeroSquad(uiHero: UiHero) {
            if (uiHero.circleImage is UiHeroImage.Data) {
                Picasso.get()
                        .load(uiHero.circleImage.bitmap)
                        .placeholder(R.drawable.hero_circle)
                        .error(R.drawable.hero_circle)
                        .transform(object : Transformation {
                            override fun key(): String {
                                return uiHero.id
                            }

                            override fun transform(source: Bitmap?): Bitmap {
                                return source?.getCroppedBitmap()!!
                            }
                        })
                        .into(itemView.mysquad_item_image)
            }
            itemView.mysquad_item_name.text = uiHero.name
            itemView.setOnClickListener { listener.onHeroSelected(uiHero.id) }
        }
    }

    override fun getItemId(position: Int): Long {
        return mySquad[position].id.toLong()
    }

    fun setMySquad(hs: List<UiHero>) {
        mySquad = hs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySquadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mysquad_item, parent, false)
        return MySquadViewHolder(view)
    }

    override fun getItemCount() = mySquad.size

    override fun onBindViewHolder(holder: MySquadViewHolder, position: Int) {
        val hero = mySquad[position]
        holder.setHeroSquad(hero)
    }
}