package xyz.thingapps.instagramdownloader

import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.jsoup.nodes.Document
import xyz.thingapps.regram.model.SharedData

object InstagramParser {
    const val TAG = "InstagramParser"
    const val TYPE_GRAPH_IMAGE = "GraphImage"
    const val TYPE_GRAPH_VIDEO = "GraphVideo"
    const val TYPE_GRAPH_SIDE_CAR = "GraphSidecar"

    fun getPost(
        url: String,
        onSuccess: ((Post) -> Unit)? = null,
        onFail: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
//        JSoupApi.create().getDocument(url).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ document ->
//                try {
//                    onSuccess?.invoke(getPostFromDocument(url, document));
//
////                    getMediaFromScript(document)
//                } catch (exception: IndexOutOfBoundsException) {
//                    onFail?.invoke()
//                } catch (exception: IllegalAccessException) {
//                    onFail?.invoke()
//                }
//
//            }, { e ->
//                onError?.invoke(e)
//                Log.e(InstagramParser::class.java.name, "error ", e)
//            }).addTo(disposeBag)
    }

    fun getPostFromDocument(
        url: String?,
        document: Document
    ): Post {
        var userName = ""

        document.select("meta").forEach { element ->
            when (element.attr("property")) {
                "og:description" -> {
                    val description = element.attr("content")
                    userName = getUserNameFromDescription(description)
                }
            }
        }

        val postPage = getMediaFromScript(document)?.entryData?.postPage

        if (postPage.isNullOrEmpty()) throw IllegalAccessException()

        val media =
            postPage[0].graphQL.shortCodeMedia

        val link = url ?: ""

        val temp = link.split("/p/")
        val id = if (temp.size > 1) temp[1].split("/")[0] else {
            link.split("/tv/")[1].split("/")[0]
        }
//        val id = link.split("/p/")[1].split("/")[0]
        val caption = media.captionEdges.edges[0].node.text
        Log.d("RegramViewModel", "userName : $userName")
        Log.d("RegramViewModel", "link : $link")
        Log.d("RegramViewModel", "caption : $caption")
        Log.d("RegramViewModel", "id : $id")
        Log.d("RegramViewModel", "media : $media")
        return Post(id, userName, link, caption, media)
    }

    private fun getUserNameFromDescription(description: String): String {
        val pattern = Regex("(@)\\w+")
        val result = pattern.find(description)
        result?.let {
            Log.d(TAG, "match result : ${it.value}")
        }
        return result?.value ?: ""
    }

    fun getMediaFromScript(
        document: Document
    ): SharedData? {
        document.select("script").forEach { script ->
            val type = script.attr("type")
            if (type == "text/javascript" && script.data().startsWith("window._sharedData")) {
                val sharedDataJson =
                    script.data().removePrefix("window._sharedData = ").removeSuffix(";")
                Log.d(TAG, "script : $sharedDataJson")
                return GsonBuilder()
                    .setLenient()
                    .create().fromJson(sharedDataJson, SharedData::class.java)
            }
        }
        return null
    }
}