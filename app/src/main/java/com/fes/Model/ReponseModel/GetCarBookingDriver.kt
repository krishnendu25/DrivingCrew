package com.fes.Model.ReponseModel

/**
 * Created by KRISHNENDU MANNA on 22,June,2021
 */
data class GetCarBookingDriver(
    val carBookedList: List<CarBookedf>,
    val result: String
)

data class CarBookedf(
    val TotalPerson: String,
    val bookingCost: String,
    val bookingStatus: String,
    val brandName: String,
    val carId: Any,
    val carImage: String,
    val carName: String,
    val destinationLocation: String,
    val driverId: Any,
    val driverImage: String,
    val driverName: String,
    val endDate: String,
    val endTime: String,
    val id: String,
    val paymentDetails: String,
    val pricePerKm: String,
    val sourceLocation: String,
    val startDate: String,
    val startTime: String,
    val userId: Any,
    val userImage: String,
    val userName: String
)