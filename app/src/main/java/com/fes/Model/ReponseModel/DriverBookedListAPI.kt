package com.fes.Model.ReponseModel

/**
 * Created by KRISHNENDU MANNA on 22,June,2021
 */
data class DriverBookedListAPI(
    val driverBookedList: List<DriverBooked>,
    val result: String
)

data class DriverBooked(
    val CarNumber: String,
    val HaveMyCar: String,
    val TotalPerson: String,
    val bookingCost: String,
    val bookingStatus: String,
    val destinationLocation: String,
    val driverEmail: String,
    val driverExperience: Any,
    val driverId: Any,
    val driverImage: String,
    val driverMobile: String,
    val driverName: String,
    val endDate: String,
    val endTime: String,
    val id: String,
    val paymentDetails: String,
    val sourceLocation: String,
    val startDate: String,
    val startTime: String,
    val userEmail: String,
    val userId: Any,
    val userImages: String,
    val userMobile: String,
    val userName: String
)