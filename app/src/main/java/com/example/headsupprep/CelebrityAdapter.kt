package com.example.headsupprep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupprep.databinding.CelebrityRowBinding

class CelebrityAdapter(private val celebrities: ArrayList<CelebrityItem>): RecyclerView.Adapter<CelebrityAdapter.CelebrityViewHolder>() {
    class CelebrityViewHolder(val binding: CelebrityRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebrityViewHolder {
        return CelebrityViewHolder(
            CelebrityRowBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CelebrityViewHolder, position: Int) {
        val celebrity = celebrities[position]
        holder.binding.apply {
            tvName.text = celebrity.name
            tvTaboo1.text = celebrity.taboo1
            tvTaboo2.text = celebrity.taboo2
            tvTaboo3.text = celebrity.taboo3
        }
    }

    override fun getItemCount() = celebrities.size
}