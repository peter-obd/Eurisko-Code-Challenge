package database

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(private val firstname: String, private val lastname: String, private val email: String, private val password: String) : Parcelable{
    override fun toString(): String {
        return """$firstname $lastname"""
    }
}