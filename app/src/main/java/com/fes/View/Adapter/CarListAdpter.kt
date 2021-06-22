package com.fes.View.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fes.Model.ReponseModel.Car
import com.fes.R
import com.fes.View.UI.Activity.Rider.CarBookingForm_ByRider
import java.util.*

/**
 * Created by KRISHNENDU MANNA on 16,June,2021
 */
class CarListAdpter (private var items: List<Car>, var context: Context) : RecyclerView.Adapter<CarListAdpter.ViewHolder> () {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.child_car_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = items[holder.adapterPosition]
        holder.brandName!!.text = i.brandName
        holder.carName.text = i.carName
        holder.pricePerKm.text = "₹"+i.pricePerKm+"/Km"
        Glide.with(context).load(i.carImage).placeholder(R.drawable.car1).into(holder.carImage)
        holder.carImage.visibility = if (true) View.VISIBLE else View.GONE
       holder.rootViewCardView!!.setCardBackgroundColor(getRandomColor());
        holder.details.setOnClickListener {

        }
        holder.booknow.setOnClickListener {
            var intent =Intent(context, CarBookingForm_ByRider::class.java)
            intent.putExtra("brandName", i.brandName)
            intent.putExtra("carImage", i.carImage)
            intent.putExtra("carName",i.carName)
            intent.putExtra("id",i.id)
            intent.putExtra("pricePerKm","₹"+i.pricePerKm+"/Km")
        context.startActivity(intent)
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        var rootViewCardView = view.findViewById<CardView>(R.id.rootViewCardView)
        var brandName = view.findViewById<TextView>(R.id.brandName)
        var carName  = view.findViewById<TextView>(R.id.carName)
        var pricePerKm = view.findViewById<TextView>(R.id.pricePerKm)
        var carImage = view.findViewById<ImageView>(R.id.carImage)
        var details = view.findViewById<TextView>(R.id.details)
        var booknow = view.findViewById<TextView>(R.id.booknow)
        var parent = view
    }
}