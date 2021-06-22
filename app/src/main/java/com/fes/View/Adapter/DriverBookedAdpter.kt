package com.fes.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fes.Model.ReponseModel.DriverBooked
import com.fes.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by KRISHNENDU MANNA on 22,June,2021
 */
class DriverBookedAdpter (private var items: List<DriverBooked>, var context: Context) : RecyclerView.Adapter<DriverBookedAdpter.ViewHolder> () {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.child_driver_booked, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = items[position]
        if (!i.driverName.isNullOrEmpty())
        holder.driverName.text = i.driverName
        if (!i.driverMobile.isNullOrEmpty())
        holder.driverPhoneNo.text = i.driverMobile
        holder.bookingStatus.text = i.bookingStatus
        holder.startLocation.text = i.sourceLocation
        holder.endLocation.text = i.destinationLocation
        holder.bookingCost.text = "₹"+i.bookingCost
        try {
            Glide.with(context).load(i.driverImage).placeholder(R.drawable.ic_drive_profile).into(holder.driverImage)
        } catch (e: Exception) {
        }
        holder.viewDriver.visibility = if (true) View.VISIBLE else View.GONE
        holder.contactView.visibility = if (true) View.VISIBLE else View.GONE
        holder.callDriver.visibility = if (true) View.VISIBLE else View.GONE
        holder.sendEmail.visibility = if (true) View.VISIBLE else View.GONE
        holder.shearDetails.visibility = if (true) View.VISIBLE else View.GONE
        holder.driverImage.visibility = if (true) View.VISIBLE else View.GONE
        holder.parent.setOnClickListener {

        }
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        var viewDriver = view.findViewById<LinearLayout>(R.id.viewDriver)
        var driverName = view.findViewById<TextView>(R.id.driverName)
        var driverPhoneNo = view.findViewById<TextView>(R.id.driverPhoneNo)
        var contactView = view.findViewById<LinearLayout>(R.id.contactView)
        var callDriver = view.findViewById<LinearLayout>(R.id.callDriver)
        var sendEmail =view.findViewById<LinearLayout>(R.id.sendEmail)
        var shearDetails = view.findViewById<LinearLayout>(R.id.shearDetails)
        var bookingStatus =view.findViewById<TextView>(R.id.booking_status)
        var startLocation = view.findViewById<TextView>(R.id.start_location)
        var endLocation = view.findViewById<TextView>(R.id.end_location)
        var bookingCost = view.findViewById<TextView>(R.id.booking_cost)
        var driverImage =view.findViewById<CircleImageView>(R.id.driverImage)
        var parent = view
    }
}