package com.anupam.covid19;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DistrictAdapter(
    private val context: Context,
    districtItemArrayList: ArrayList<States>
) : RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {
    private val districtItemArrayList: ArrayList<States>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.news_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val coronaItem: States = districtItemArrayList[position]
        val state: String = coronaItem.states
        val confirm: String = coronaItem.confirm
        val active: String = coronaItem.active
        val recover: String = coronaItem.recover
        val death: String = coronaItem.death
        holder.state.text = state
        holder.confirm.text = confirm
        holder.active.text = active
        holder.recovered.text = recover
        holder.death.text = death
    }

    override fun getItemCount(): Int {
        return districtItemArrayList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
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

    init {
        this.districtItemArrayList = districtItemArrayList
    }
}