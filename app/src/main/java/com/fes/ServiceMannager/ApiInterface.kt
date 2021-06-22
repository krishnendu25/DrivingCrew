package com.fes.hoori.controller


import com.fes.Model.ReponseModel.*
import com.fes.ServiceMannager.URLConstants.Companion.AllcarListing
import com.fes.ServiceMannager.URLConstants.Companion.aboutUs
import com.fes.ServiceMannager.URLConstants.Companion.carBookedDetails
import com.fes.ServiceMannager.URLConstants.Companion.carBookingByRider
import com.fes.ServiceMannager.URLConstants.Companion.carBookngListing
import com.fes.ServiceMannager.URLConstants.Companion.driverBookingByRider
import com.fes.ServiceMannager.URLConstants.Companion.driverBookngListing
import com.fes.ServiceMannager.URLConstants.Companion.driverRegistration
import com.fes.ServiceMannager.URLConstants.Companion.editDriverDetailsById
import com.fes.ServiceMannager.URLConstants.Companion.editRiderDetailsById
import com.fes.ServiceMannager.URLConstants.Companion.getDriverBookedList
import com.fes.ServiceMannager.URLConstants.Companion.getDriverCarBookedList
import com.fes.ServiceMannager.URLConstants.Companion.userDetailsById
import com.fes.ServiceMannager.URLConstants.Companion.userDriverlogin
import com.fes.ServiceMannager.URLConstants.Companion.userRegistration
import com.google.firebase.analytics.FirebaseAnalytics.Event.LOGIN
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @FormUrlEncoded
    @POST(userDriverlogin)
    fun Userdriverlogin(
        @Field("userName") userName: String?,
        @Field("userType") userType: String?,
        @Field("passWord") passWord: String?
    ): Call<ResponseBody>

    @Multipart
    @POST(driverRegistration)
    fun DriverRegistration(
        @Part("Name") Name: RequestBody ?,
        @Part("Age") Age: RequestBody ?,
        @Part("DrivingExperience") DrivingExperience: RequestBody ?,
        @Part("PresentAddress") PresentAddress: RequestBody ?,
        @Part("PhoneNumber") PhoneNumber: RequestBody ?,
        @Part("EmailId") EmailId: RequestBody ?,
        @Part("Password") Password: RequestBody ?,
        @Part("UserType") UserType: RequestBody ?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part
    ): Call<Registration_api>


    @Multipart
    @POST(userRegistration)
    fun UserRegistration(
        @Part("Name") Name: RequestBody ?,
        @Part("Age") Age: RequestBody ?,
        @Part("DrivingExperience") DrivingExperience: RequestBody ?,
        @Part("PresentAddress") PresentAddress: RequestBody ?,
        @Part("PhoneNumber") PhoneNumber: RequestBody ?,
        @Part("EmailId") EmailId: RequestBody ?,
        @Part("Password") Password: RequestBody ?,
        @Part("ConfPassword") ConfPassword: RequestBody ?,
        @Part("UserType") UserType: RequestBody ?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part
    ): Call<Registration_api>


    @FormUrlEncoded
    @POST(userDetailsById)
    fun getuserDetailsById(
        @Field("UserID") UserID: String?,
        @Field("UserType") UserType: String?
    ): Call<UserDetails_Api>


    @Multipart
    @POST(editDriverDetailsById)
    fun EditDriverDetailsById(
       
        @Part("UserID") UserID: RequestBody?,
        @Part("Name") Name: RequestBody?,
        @Part("Age") Age: RequestBody?,
        @Part("PresentAddress") PresentAddress: RequestBody?,
        @Part("PhoneNumber") PhoneNumber: RequestBody?,
        @Part("EmailId") EmailId: RequestBody?,
        @Part("UserType") UserType: RequestBody?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part,
        @Part("DrivingExperience") DrivingExperience: RequestBody?
    ): Call<EditUserDetails_api>


    @Multipart
    @POST(editRiderDetailsById)
    fun EditRiderDetailsById(
        @Part("UserID") UserID: RequestBody?,
        @Part("Name") Name: RequestBody?,
        @Part("Age") Age: RequestBody?,
        @Part("PresentAddress") PresentAddress: RequestBody?,
        @Part("PhoneNumber") PhoneNumber: RequestBody?,
        @Part("EmailId") EmailId: RequestBody?,
        @Part("UserType") UserType: RequestBody?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part,
        @Part("DrivingExperience") DrivingExperience: RequestBody?
    ): Call<EditUserDetails_api>

    @FormUrlEncoded
    @POST(carBookngListing)
    fun CarBookngListing(
        @Field("UserID") UserID: String?
    ): Call<BookedCarListing>


    @FormUrlEncoded
    @POST(carBookedDetails)
    fun carBookedDetail(
        @Field("UserID") UserID: String?,
        @Field("UserType ") Name: String?
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(AllcarListing)
    fun AllcarListingApi(
        @Field("UserID") UserID: String?,
        @Field("UserType ") Name: String?
    ): Call<AllCarList_api>


    @FormUrlEncoded
    @POST(driverBookingByRider)
    fun hitdriverBookingByRider(
        @Field("UserID") UserID: String?,
        @Field("UserType") UserType: String?,
        @Field("HaveMyCar") HaveMyCar: String?,
        @Field("CarNumber") CarNumber: String?,
        @Field("BookingCost") BookingCost: String?,
        @Field("BookingStatus") BookingStatus: String?,
        @Field("PaymentStatus") PaymentStatus: String?,
        @Field("PaymentDetails") PaymentDetails: String?,
        @Field("PickUpPoint") PickUpPoint: String?,
        @Field("EndPoint") EndPoint: String?,
        @Field("NoOfPassenger") NoOfPassenger: String?,
        @Field("StartDate") StartDate: String?,
        @Field("EndDate") EndDate: String?,
        @Field("StartTime") StartTime: String?,
        @Field("EndTime") EndTime: String?
    ): Call<ResponseBody>
    @FormUrlEncoded
    @POST(carBookingByRider)
    fun hitcarBookingByRider(
        @Field("UserID") UserID: String?,
        @Field("UserType") UserType: String?,
        @Field("CarID") CarID: String?,
        @Field("DriveBy") CarNumber: String?,
        @Field("BookingCost") BookingCost: String?,
        @Field("BookingStatus") BookingStatus: String?,
        @Field("PaymentStatus") PaymentStatus: String?,
        @Field("PaymentDetails") PaymentDetails: String?,
        @Field("SourceLocation") PickUpPoint: String?,
        @Field("EndLocation") EndPoint: String?,
        @Field("TotalPerson") NoOfPassenger: String?,
        @Field("StartDate") StartDate: String?,
        @Field("EndDate") EndDate: String?,
        @Field("StartTime") StartTime: String?,
        @Field("EndTime") EndTime: String?
    ): Call<ResponseBody>



    @GET(aboutUs)
    fun hitaboutUs(): Call<AboutUs>

    @FormUrlEncoded
    @POST(driverBookngListing)
    fun getdriverBookngListing(
        @Field("UserID") UserID: String?
    ): Call<DriverBookedListAPI>

    @FormUrlEncoded
    @POST(getDriverBookedList)
    fun tDriverBookedList(
        @Field("UserID") UserID: String?
    ): Call<DriverBookingShowDriver>

    @FormUrlEncoded
    @POST(getDriverCarBookedList)
    fun DriverCarBookedList(
        @Field("UserID") UserID: String?
    ): Call<GetCarBookingDriver>


}