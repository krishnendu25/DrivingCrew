package com.fes.Model.ReponseModel

/**
 * Created by KRISHNENDU MANNA on 15,June,2021
 */
data class AllCarList_api(
    val carList: List<Car>,
    val result: String
)

data class Car(
    val brandName: String,
    val carImage: String,
    val carName: String,
    val id: String,
    val pricePerKm: String
)