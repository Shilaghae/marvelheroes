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
import com.withplum.yourheroes.main.HeroesAdapter.HeroViewHolder
import com.withplum.yourheroes.util.getCroppedBitmap
import kotlinx.android.synthetic.main.hero_item.view.*

class HeroesAdapter(val listener: OnHeroSelectedListener) : RecyclerView.Adapter<HeroViewHolder>() {

    private var heroes: List<UiHero> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setHero(uiHero: UiHero) {
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
                        .into(itemView.hero_item_image)
            }
            itemView.hero_item_name.text = uiHero.name
            itemView.setOnClickListener { listener.onHeroSelected(uiHero.id) }
        }
    }

    override fun getItemId(position: Int): Long {
        return heroes[position].id.toLong()
    }

    fun setHeroes(hs: List<UiHero>) {
        heroes = hs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item, parent, false)
        return HeroViewHolder(view)
    }

    override fun getItemCount() = heroes.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]
        holder.setHero(hero)
    }
}