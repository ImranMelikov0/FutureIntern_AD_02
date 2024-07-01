package com.imranmelikov.futureintern_ad_02.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class User(val name:String,val password:String):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}