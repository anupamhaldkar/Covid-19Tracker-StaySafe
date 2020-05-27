package com.ahdev2020.covid19

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahdev2020.covid_19.R
import java.util.*

class DistrictAdapter(private val context: Context, private val districtItemArrayList: ArrayList<States>) : RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coronaItem = districtItemArrayList[position]
        val state = coronaItem.states
        val confirm = coronaItem.confirm
        val active = coronaItem.active
        val recover = coronaItem.recover
        val death = coronaItem.death
        holder.state.text = state
        holder.confirm.text = confirm
        holder.active.text = active
        holder.recovered.text = recover
        holder.death.text = death
    }

    override fun getItemCount(): Int {
        return districtItemArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var state: TextView
        var confirm: TextView
        var active: TextView
        var recovered: TextView
        var death: TextView

        init {
            state = itemView.findViewById(R.id.stateName)
            confirm = itemView.findViewById(R.id.confirmNo)
            active = itemView.findViewById(R.id.activeNo)
            recovered = itemView.findViewById(R.id.recoveredNo)
            death = itemView.findViewById(R.id.deathNo)
        }
    }

}