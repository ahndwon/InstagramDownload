package xyz.thingapps.instagramdownloader

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import xyz.thingapps.regram.model.ShortCodeMedia

data class Post(
    val id: String,
    val userName: String,
    val link: String,
//    val imageUrl: String,
    val caption: String,
    val media: ShortCodeMedia,
    var mediaPath: Uri? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
//        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(ShortCodeMedia::class.java.classLoader) ?: ShortCodeMedia(),
        parcel.readParcelable(Uri::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userName)
        parcel.writeString(link)
//        parcel.writeString(imageUrl)
        parcel.writeString(caption)
        parcel.writeParcelable(media, flags)
        parcel.writeParcelable(mediaPath, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}