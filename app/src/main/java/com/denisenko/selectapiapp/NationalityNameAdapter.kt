package com.denisenko.selectapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NationalityNameAdapter(val names: Array<NationalizeModel>) :
    RecyclerView.Adapter<NationalityNameAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView? = null
        var countryTextView: TextView? = null

        init {
            nameTextView = itemView.findViewById(R.id.name)
            countryTextView = itemView.findViewById(R.id.country)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (names[position].country.isEmpty())
            holder.nameTextView?.text = holder.itemView.context.getString(R.string.unknown_name)
        else{
            holder.nameTextView?.text = names[position].name
            for (n in 0 until names[position].country.size)
                holder.countryTextView?.append(names[position].country[n].country_id + ":  " + names[position].country[n].probability + "\n")
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}