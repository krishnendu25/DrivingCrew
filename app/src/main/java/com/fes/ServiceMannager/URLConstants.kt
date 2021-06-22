package com.fes.ServiceMannager

import com.fes.BuildConfig


class URLConstants {
    companion object {
        const val BASE_URL_API = BuildConfig.BASE_URL
        const val driverRegistration =
            BuildConfig.BASE_URL + "registration.process.php?act=driverRegistration"
        const val userRegistration =
            BuildConfig.BASE_URL + "registration.process.php?act=userRegistration"
        const val userDetailsById = BuildConfig.BASE_URL + "users.process.php?act=userDetailsById";
        const val userDriverlogin = BuildConfig.BASE_URL + "login.process.php?act=userDriverlogin";
        const val editDriverDetailsById =
            BuildConfig.BASE_URL + "users.process.php?act=editDriverDetailsById";
        const val editRiderDetailsById =
            BuildConfig.BASE_URL + "users.process.php?act=editRiderDetailsById";
        const val carBookngListing = BuildConfig.BASE_URL + "car.process.php?act=carBookngListing"
        const val carBookedDetails = BuildConfig.BASE_URL + "car.process.php?act=carBookedDetails"
        const val AllcarListing = BuildConfig.BASE_URL + "car.process.php?act=carListing"
        const val driverBookingByRider = BuildConfig.BASE_URL + "car.process.php?act=driverBookingByRider"
        const val carBookingByRider = BuildConfig.BASE_URL + "car.process.php?act=carBookingByRider"
        const val aboutUs =BuildConfig.BASE_URL + "users.process.php?act=getAboutUsDetails"
        const val driverBookngListing  =BuildConfig.BASE_URL + "car.process.php?act=driverBookngListing"


        const val getDriverBookedList=BuildConfig.BASE_URL + "car.process.php?act=getDriverBookedList"
        const val getDriverCarBookedList=BuildConfig.BASE_URL + "car.process.php?act=getDriverCarBookedList"

    }
}