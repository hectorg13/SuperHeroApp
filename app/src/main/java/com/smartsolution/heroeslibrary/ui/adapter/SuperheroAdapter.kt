package com.smartsolution.heroeslibrary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartsolution.heroeslibrary.R
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroItemResponse
import com.smartsolution.heroeslibrary.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroAdapter(
    private var superHeroList: List<SuperHeroItemResponse> = emptyList(),
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<SuperheroViewHolder>() {

    fun updateList(list: List<SuperHeroItemResponse>) {
        superHeroList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperheroViewHolder(layoutInflater.inflate(R.layout.item_superhero, parent, false))
    }

    override fun onBindViewHolder(viewHolder: SuperheroViewHolder, position: Int) {
        val item = superHeroList[position]
        viewHolder.renderCard(item, onItemSelected)
    }

    override fun getItemCount(): Int = superHeroList.size
}

class SuperheroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemSuperheroBinding.bind(itemView)

    fun renderCard(superHero: SuperHeroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvSuperheroName.text = superHero.name
        Picasso.get().load(superHero.superHeroImage.url).into(binding.ivSuperheroPicture)
        binding.root.setOnClickListener { onItemSelected(superHero.superHeroesId) }
    }
}