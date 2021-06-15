package com.fes.hoori.controller


import com.fes.Model.ReponseModel.*
import com.fes.ServiceMannager.URLConstants.Companion.AllcarListing
import com.fes.ServiceMannager.URLConstants.Companion.carBookedDetails
import com.fes.ServiceMannager.URLConstants.Companion.carBookngListing
import com.fes.ServiceMannager.URLConstants.Companion.driverRegistration
import com.fes.ServiceMannager.URLConstants.Companion.editDriverDetailsById
import com.fes.ServiceMannager.URLConstants.Companion.editRiderDetailsById
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
        @Field("userType") fcmToken: String?,
        @Field("passWord") passWord: String?
    ): Call<Userdriverlogin_api>

    @Multipart
    @POST(driverRegistration)
    fun DriverRegistration(
        @Part("Name") Name: String?,
        @Part("Age") Age: String?,
        @Part("DrivingExperience") DrivingExperience: String?,
        @Part("PresentAddress") PresentAddress: String?,
        @Part("PhoneNumber") PhoneNumber: String?,
        @Part("EmailId") EmailId: String?,
        @Part("Password") Password: String?,
        @Part("ConfPassword") ConfPassword: String?,
        @Part("UserType") UserType: String?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part
    ): Call<Registration_api>


    @Multipart
    @POST(userRegistration)
    fun UserRegistration(
        @Part("Name") Name: String?,
        @Part("Age") Age: String?,
        @Part("DrivingExperience") DrivingExperience: String?,
        @Part("PresentAddress") PresentAddress: String?,
        @Part("PhoneNumber") PhoneNumber: String?,
        @Part("EmailId") EmailId: String?,
        @Part("Password") Password: String?,
        @Part("ConfPassword") ConfPassword: String?,
        @Part("UserType") UserType: String?,
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


    @FormUrlEncoded
    @POST(editDriverDetailsById)
    fun EditDriverDetailsById(
       
        @Field("UserID") UserID: String?,
        @Field("Name") Name: String?,
        @Field("Age") Age: String?,
        @Field("PresentAddress") PresentAddress: String?,
        @Field("PhoneNumber") PhoneNumber: String?,
        @Field("EmailId") EmailId: String?,
        @Field("UserType") UserType: String?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part
    ): Call<EditUserDetails_api>


    @FormUrlEncoded
    @POST(editRiderDetailsById)
    fun EditRiderDetailsById(
        @Field("UserID") UserID: String?,
        @Field("Name") Name: String?,
        @Field("Age") Age: String?,
        @Field("PresentAddress") PresentAddress: String?,
        @Field("PhoneNumber") PhoneNumber: String?,
        @Field("EmailId") EmailId: String?,
        @Field("UserType") UserType: String?,
        @Part ProfilePhoto: MultipartBody.Part,
        @Part AADHARPhoto: MultipartBody.Part,
        @Part DrivingLicensePhoto: MultipartBody.Part
    ): Call<EditUserDetails_api>

    @FormUrlEncoded
    @POST(carBookngListing)
    fun CarBookngListing(
        @Field("UserID") UserID: String?,
        @Field("UserType ") Name: String?
    ): Call<ResponseBody>


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

}