package xyz.thingapps.instagramdownloader

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log

object InstagramUtil {
    const val INSTAGRAM_PREFIX = "https://www.instagram.com/"
    const val INSTAGRAM_URL = "https://www.instagram.com"
    const val INSTAGRAM_PACKAGE = "com.instagram.android"

    @JvmStatic
    fun showOnInstagram(activity: Activity, link: String) {
        if (link.isEmpty()) {
            openInstagram(activity)
            return
        }

        try {
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            intent.setPackage("com.instagram.android")
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(InstagramUtil::class.java.name, e.message ?: "")
        }
    }

    @JvmStatic
    fun openInstagram(activity: Activity) {
        showOnInstagram(activity, INSTAGRAM_URL)
    }
}