package xyz.thingapps.regram.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SharedData(
    @SerializedName("entry_data")
    val entryData: EntryData
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<EntryData>(EntryData::class.java.classLoader) ?: EntryData()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(entryData, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SharedData> {
        override fun createFromParcel(parcel: Parcel): SharedData {
            return SharedData(parcel)
        }

        override fun newArray(size: Int): Array<SharedData?> {
            return arrayOfNulls(size)
        }
    }
}

data class EntryData(
    @SerializedName("PostPage")
    val postPage: List<PostPage> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(PostPage) ?: emptyList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(postPage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntryData> {
        override fun createFromParcel(parcel: Parcel): EntryData {
            return EntryData(parcel)
        }

        override fun newArray(size: Int): Array<EntryData?> {
            return arrayOfNulls(size)
        }
    }
}

data class PostPage(
    @SerializedName("graphql")
    val graphQL: GraphQL = GraphQL()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<GraphQL>(GraphQL::class.java.classLoader) ?: GraphQL()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(graphQL, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostPage> {
        override fun createFromParcel(parcel: Parcel): PostPage {
            return PostPage(parcel)
        }

        override fun newArray(size: Int): Array<PostPage?> {
            return arrayOfNulls(size)
        }
    }
}

data class GraphQL(
    @SerializedName("shortcode_media")
    val shortCodeMedia: ShortCodeMedia = ShortCodeMedia()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<ShortCodeMedia>(ShortCodeMedia::class.java.classLoader)
            ?: ShortCodeMedia()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(shortCodeMedia, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GraphQL> {
        override fun createFromParcel(parcel: Parcel): GraphQL {
            return GraphQL(parcel)
        }

        override fun newArray(size: Int): Array<GraphQL?> {
            return arrayOfNulls(size)
        }
    }
}

data class ShortCodeMedia(
    @SerializedName("__typename")
    val typeName: String = "",
    @SerializedName("shortcode")
    val shortCode: String = "",
    @SerializedName("display_url")
    val displayUrl: String = "",
    @SerializedName("video_url")
    val videoUrl: String = "",
    @SerializedName("is_video")
    val isVideo: Boolean = false,
    @SerializedName("edge_sidecar_to_children")
    val sideCar: SideCar = SideCar(),
    @SerializedName("edge_media_to_caption")
    val captionEdges: CaptionEdge = CaptionEdge()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(SideCar::class.java.classLoader) ?: SideCar(),
        parcel.readParcelable(CaptionEdge::class.java.classLoader) ?: CaptionEdge()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(typeName)
        parcel.writeString(shortCode)
        parcel.writeString(displayUrl)
        parcel.writeString(videoUrl)
        parcel.writeByte(if (isVideo) 1 else 0)
        parcel.writeParcelable(sideCar, flags)
        parcel.writeParcelable(captionEdges, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShortCodeMedia> {
        override fun createFromParcel(parcel: Parcel): ShortCodeMedia {
            return ShortCodeMedia(parcel)
        }

        override fun newArray(size: Int): Array<ShortCodeMedia?> {
            return arrayOfNulls(size)
        }
    }
}

data class CaptionEdge(
    val edges: List<TextNode> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(TextNode) ?: emptyList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(edges)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CaptionEdge> {
        override fun createFromParcel(parcel: Parcel): CaptionEdge {
            return CaptionEdge(parcel)
        }

        override fun newArray(size: Int): Array<CaptionEdge?> {
            return arrayOfNulls(size)
        }
    }
}


data class SideCar(
    val edges: List<Edge> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Edge.CREATOR) ?: emptyList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(edges)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SideCar> {
        override fun createFromParcel(parcel: Parcel): SideCar {
            return SideCar(parcel)
        }

        override fun newArray(size: Int): Array<SideCar?> {
            return arrayOfNulls(size)
        }
    }
}

data class Edge(
    val node: Node = Node()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<Node>(Node::class.java.classLoader) ?: Node()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(node, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Edge> {
        override fun createFromParcel(parcel: Parcel): Edge {
            return Edge(parcel)
        }

        override fun newArray(size: Int): Array<Edge?> {
            return arrayOfNulls(size)
        }
    }
}

data class Node(
    @SerializedName("__typename")
    val typeName: String = "",
    @SerializedName("shortcode")
    val shortCode: String = "",
    @SerializedName("display_url")
    val displayUrl: String = "",
    @SerializedName("video_url")
    val videoUrl: String = "",
    @SerializedName("is_video")
    val isVideo: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(typeName)
        parcel.writeString(shortCode)
        parcel.writeString(displayUrl)
        parcel.writeString(videoUrl)
        parcel.writeByte(if (isVideo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Node> {
        override fun createFromParcel(parcel: Parcel): Node {
            return Node(parcel)
        }

        override fun newArray(size: Int): Array<Node?> {
            return arrayOfNulls(size)
        }
    }
}

data class TextNode(
    val node: Caption = Caption()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<Caption>(Caption::class.java.classLoader) ?: Caption()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(node, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TextNode> {
        override fun createFromParcel(parcel: Parcel): TextNode {
            return TextNode(parcel)
        }

        override fun newArray(size: Int): Array<TextNode?> {
            return arrayOfNulls(size)
        }
    }
}


data class Caption(
    val text: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Caption> {
        override fun createFromParcel(parcel: Parcel): Caption {
            return Caption(parcel)
        }

        override fun newArray(size: Int): Array<Caption?> {
            return arrayOfNulls(size)
        }
    }
}