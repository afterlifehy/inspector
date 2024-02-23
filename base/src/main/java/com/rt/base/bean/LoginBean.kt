package com.rt.base.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class LoginBean(
    val loginMap: LoginMap,
    val personInfo: PersonInfo
)

class LoginMap

data class PersonInfo(
    val department: String,
    val manageStreets: List<Street>,
    val name: String,
    val phone: String,
    val picturePath: String
)

open class Street() : RealmObject(){
    @PrimaryKey
    var streetNo: String = ""
    var parkingType: String = ""
    var streetName: String = ""
}