package com.qnet.qnetclient.appusuario.ui.settings

import android.os.Parcel
import android.os.Parcelable

data class SettingsModel(var name: String?, var email: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SettingsModel> {
        override fun createFromParcel(parcel: Parcel): SettingsModel {
            return SettingsModel(parcel)
        }

        override fun newArray(size: Int): Array<SettingsModel?> {
            return arrayOfNulls(size)
        }
    }
}