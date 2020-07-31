package com.qnet.qnetclient.loginregister_local

import android.os.Parcel
import android.os.Parcelable

data class InfoRegister(val nombre:String?,val ubicacion:String?,val horario:String?, val tipo:String?, val informacion:String?,val telefono:String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(ubicacion)
        parcel.writeString(horario)
        parcel.writeString(tipo)
        parcel.writeString(informacion)
        parcel.writeString(telefono)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoRegister> {
        override fun createFromParcel(parcel: Parcel): InfoRegister {
            return InfoRegister(parcel)
        }

        override fun newArray(size: Int): Array<InfoRegister?> {
            return arrayOfNulls(size)
        }
    }
}
