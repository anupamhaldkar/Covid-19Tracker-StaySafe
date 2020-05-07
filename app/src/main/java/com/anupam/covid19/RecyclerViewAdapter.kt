package com.anupam.covid19

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anupam.covid19.RecyclerViewAdapter.RecyclerViewHolder
import java.util.*

class RecyclerViewAdapter internal constructor(
    private val context: Context,
    private val coronaItemArrayList: ArrayList<States>
) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.news_items, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val coronaItem = coronaItemArrayList[position]
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
        return coronaItemArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) :
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
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StatesActivity::class.java)
                intent.putExtra("state", state.text.toString())
                intent.putExtra("confirm", confirm.text.toString())
                intent.putExtra("recovered", recovered.text.toString())
                intent.putExtra("death", death.text.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

}