package io.hammerhead.datamodels.timeseriesData.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Stand-in for Hammerhead's `LightMode` enum.
 *
 * Since Karoo OS 1.669.2569 (12 Nov 2026) third-party apps can no longer read the sensor-service
 * APK (SELinux denies it), so the real enum cannot be loaded by reflection any more. It does not
 * have to be: a [android.os.Bundle] serializes an object's class *name* followed by its parcel
 * payload, and the receiver re-resolves that name with its own class loader. Hammerhead's
 * `LightMode` is a Parcelize enum whose parcel form is simply `writeString(name)`, so writing the
 * mode name under this identically named class makes the SensorService reconstruct its own enum.
 *
 * The fully qualified class name is the contract and must stay exactly as it is.
 */
class LightMode(private val modeName: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(modeName)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String = modeName

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<LightMode> {
            override fun createFromParcel(source: Parcel): LightMode = LightMode(source.readString() ?: "UNKNOWN")
            override fun newArray(size: Int): Array<LightMode?> = arrayOfNulls(size)
        }
    }
}
