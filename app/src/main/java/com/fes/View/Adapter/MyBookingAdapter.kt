package com.fes.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fes.Model.ReponseModel.CarBooked
import com.fes.R

/**
 * Created by KRISHNENDU MANNA on 22,June,2021
 */
class MyBookingAdapter (private var items: List<CarBooked>, var context: Context) : RecyclerView.Adapter<MyBookingAdapter.ViewHolder> () {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.child_booking_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = items[position]
        Glide.with(context).load(i.carImage).into(holder.carImage)

        holder.bookingDate.setText("Booking Date : "+i.endDate)
        holder.bookingTime.setText("Booking Time : "+i.startTime)
        holder.bookingDistance.setText("Pickup : "+i.sourceLocation)
        holder.bookingcoste.setText( "Cost/KM : ₹"+i.bookingCost)
        holder.status.setText( "Status : "+i.bookingStatus)
        holder.parent.setOnClickListener {

        }
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        var carImage = view.findViewById<ImageView>(R.id.carImage)
        var bookingDate = view.findViewById<TextView>(R.id.bookingDate)
        var bookingTime = view.findViewById<TextView>(R.id.bookingTime)
        var bookingDistance = view.findViewById<TextView>(R.id.bookingDistance)
        var bookingcoste = view.findViewById<TextView>(R.id.bookingcoste)
        var status = view.findViewById<TextView>(R.id.status)
        var parent = view
    }
}