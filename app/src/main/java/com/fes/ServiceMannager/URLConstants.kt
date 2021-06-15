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
        const val userDriverlogin = BuildConfig.BASE_URL + "users.process.php?act=userDriverlogin";
        const val editDriverDetailsById =
            BuildConfig.BASE_URL + "users.process.php?act=editDriverDetailsById";
        const val editRiderDetailsById =
            BuildConfig.BASE_URL + "users.process.php?act=editRiderDetailsById";
        const val carBookngListing = BuildConfig.BASE_URL + "car.process.php?act=carBookngListing"
        const val carBookedDetails = BuildConfig.BASE_URL + "car.process.php?act=carBookedDetails"
        const val AllcarListing = BuildConfig.BASE_URL + "car.process.php?act=carListing"

    }
}