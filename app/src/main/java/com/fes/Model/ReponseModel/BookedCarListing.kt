package com.fes.Model.ReponseModel

/**
 * Created by KRISHNENDU MANNA on 22,June,2021
 */
data class BookedCarListing(
    val carBookedList: List<CarBooked>,
    val result: String
)

data class CarBooked(
    val TotalPerson: String,
    val bookingCost: String,
    val bookingStatus: String,
    val brandName: String,
    val carId: String,
    val carImage: String,
    val carName: String,
    val destinationLocation: String,
    val endDate: String,
    val endTime: String,
    val id: String,
    val paymentDetails: String,
    val pricePerKm: String,
    val sourceLocation: String,
    val startDate: String,
    val startTime: String,
    val userId: String,
    val userName: String
)