package com.jasmeet.roadcastAssign.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import java.util.Locale

object Utils {

    fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double, context: Context): String {
        var strAdd = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current location address", strReturnedAddress.toString())
            } else {
                Log.w("My Current location address", "No Address returned!")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strAdd
    }

    fun getImageLinkWithSize(imgPath: String?, size: ImgSize): String {

        if (imgPath == null)
            return "https://static.vecteezy.com/system/resources/previews/005/337/799/original/icon-image-not-found-free-vector.jpg"

        return "https://image.tmdb.org/t/p/${size.name.toLowerCase(androidx.compose.ui.text.intl.Locale.current)}/$imgPath"

    }

}

enum class ImgSize {
    W300,
    W780,
    W1280,
    Original
}