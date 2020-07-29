package com.ian.bottomnavigation.ui.home

import android.os.Parcel
import android.os.Parcelable

data class Model (var title:String?,var descripcion:String?,var num:String?,var dist:String?, var image:String? ,var posicion:String?,var keyLocal:String?, val direccion:String?,
                  val horario :String?, val informacion:String? ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(descripcion)
        parcel.writeString(num)
        parcel.writeString(dist)
        parcel.writeString(image)
        parcel.writeString(posicion)
        parcel.writeString(keyLocal)
        parcel.writeString(direccion)
        parcel.writeString(horario)
        parcel.writeString(informacion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Model> {
        override fun createFromParcel(parcel: Parcel): Model {
            return Model(parcel)
        }

        override fun newArray(size: Int): Array<Model?> {
            return arrayOfNulls(size)
        }
    }
}