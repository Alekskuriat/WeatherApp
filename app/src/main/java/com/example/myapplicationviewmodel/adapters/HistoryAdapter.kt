package com.example.myapplicationviewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.data.Weather
import kotlinx.android.synthetic.main.list_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Weather> = arrayListOf()

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.recyclerViewItem.text =
                    String.format("%3d. %s t: %.0f \u2103 %.0f  %.0f м/с %.0f ммрт.ст. %s",
                        weather.id,
                        weather.city.city,
                        weather.temperature,
                        weather.humidity,
                        weather.windSpeed,
                        weather.pressure,
                        weather.condition)

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${weather.city.city}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}