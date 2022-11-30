package com.example.btvn3.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.example.btvn3.DetailActivity
import com.example.btvn3.databinding.*
import com.example.btvn3.models.Comment
import com.example.btvn3.models.PostType
import com.example.btvn3.models.Datum
import com.example.btvn3.views.DetailActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.album_2_photo_layout.view.*
import kotlinx.android.synthetic.main.album_3_photo_layout.view.*
import kotlinx.android.synthetic.main.album_4_photo_layout.view.*
import kotlinx.android.synthetic.main.album_5_photo_layout.view.*
import kotlinx.android.synthetic.main.album_multiple_photo_layout.view.*


class DatumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private lateinit var context: Context
    private var listDatum = mutableListOf<Datum>()

    private val TYPE_HEAD = 10

    constructor(context: Context, listDatum: MutableList<Datum>) {
        this.context = context
        this.listDatum = listDatum
    }

    class HeaderRcvViewHolder(val headerRcvLayoutBinding: HeaderRcvLayoutBinding) :
        RecyclerView.ViewHolder(headerRcvLayoutBinding.root) {

    }

    class StatusViewHolder(val statusTypeLayoutBinding: StatusTypeLayoutBinding) :
        RecyclerView.ViewHolder(statusTypeLayoutBinding.root) {
        fun onBind(datum: Datum) {
            statusTypeLayoutBinding.datum = datum
        }
    }

    class PhotoViewHolder(val photoTypeLayoutBinding: PhotoTypeLayoutBinding) :
        RecyclerView.ViewHolder(photoTypeLayoutBinding.root) {

    }

    class VideoViewHolder(val videoTypeLayoutBinding: VideoTypeLayoutBinding) :
        RecyclerView.ViewHolder(videoTypeLayoutBinding.root) {

    }

    class AlbumViewHolder(val albumTypeLayoutBinding: AlbumTypeLayoutBinding) :
        RecyclerView.ViewHolder(albumTypeLayoutBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (viewType == PostType.STATUS) {
            val statusTypeLayoutBinding = StatusTypeLayoutBinding.inflate(inflater, parent, false)
            return StatusViewHolder(statusTypeLayoutBinding)
        } else if (viewType == PostType.PHOTO) {
            val photoTypeLayoutBinding = PhotoTypeLayoutBinding.inflate(inflater, parent, false)
            return PhotoViewHolder(photoTypeLayoutBinding)
        } else if (viewType == PostType.VIDEO) {
            val videoTypeLayoutBinding = VideoTypeLayoutBinding.inflate(inflater, parent, false)
            return VideoViewHolder(videoTypeLayoutBinding)
        } else if (viewType == PostType.ALBUM) {
            val albumTypeLayoutBinding = AlbumTypeLayoutBinding.inflate(inflater, parent, false)
            return AlbumViewHolder(albumTypeLayoutBinding)
        } else {
            val headerRcvLayoutBinding = HeaderRcvLayoutBinding.inflate(inflater, parent, false)
            return HeaderRcvViewHolder(headerRcvLayoutBinding)
        }
    }

    fun setHeaderData(datum: Datum, holder: RecyclerView.ViewHolder) {
        if (datum.post_type == PostType.STATUS) {
            var statusViewHolder = holder as StatusViewHolder
//            var avatar = datum.user.avatar
            statusViewHolder.statusTypeLayoutBinding.incHeaderLayout.datum = datum
//            context?.let { Glide.with(it).load(datum.user.avatar).into(
//                holder.statusTypeLayoutBinding.incHeaderLayout.ivProfileImageDatumItem) }
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtDisplayNameDatumItem.text = datum.user.display_name
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtCreateTimeDatumItem.text = TimeAgo.using(datum.create_time as Long)
        } else if (datum.post_type == PostType.PHOTO) {
            var photoViewHolder = holder as PhotoViewHolder
            // var avatar = datum.user.avatar
            photoViewHolder.photoTypeLayoutBinding.incHeaderLayout.datum = datum
//            context?.let { Glide.with(it).load(datum.user.avatar).into(
//                holder.statusTypeLayoutBinding.incHeaderLayout.ivProfileImageDatumItem) }
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtDisplayNameDatumItem.text = datum.user.display_name
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtCreateTimeDatumItem.text = TimeAgo.using(datum.create_time as Long)
        } else if (datum.post_type == PostType.VIDEO) {
            var videoViewHolder = holder as VideoViewHolder
            videoViewHolder.videoTypeLayoutBinding.incHeaderLayout.datum = datum
//            var avatar = datum.user.avatar
//            context?.let { Glide.with(it).load(datum.user.avatar).into(
//                holder.statusTypeLayoutBinding.incHeaderLayout.ivProfileImageDatumItem) }
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtDisplayNameDatumItem.text = datum.user.display_name
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtCreateTimeDatumItem.text = TimeAgo.using(datum.create_time as Long)
        } else if (datum.post_type == PostType.ALBUM) {
            var albumViewHolder = holder as AlbumViewHolder
            albumViewHolder.albumTypeLayoutBinding.incHeaderLayout.datum = datum
//            var avatar = datum.user.avatar
//            context?.let { Glide.with(it).load(datum.user.avatar).into(
//                holder.statusTypeLayoutBinding.incHeaderLayout.ivProfileImageDatumItem) }
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtDisplayNameDatumItem.text = datum.user.display_name
//            holder.statusTypeLayoutBinding.incHeaderLayout.txtCreateTimeDatumItem.text = TimeAgo.using(datum.create_time as Long)
        }
    }

    fun setFooterData(datum: Datum, holder: RecyclerView.ViewHolder) {
        when (datum.post_type) {
            PostType.STATUS -> {
                holder as StatusViewHolder
                holder.statusTypeLayoutBinding.incFooterLayout.datum = datum
            }
            PostType.PHOTO -> {
                holder as PhotoViewHolder
                holder.photoTypeLayoutBinding.incFooterLayout.datum = datum
            }
            PostType.VIDEO -> {
                holder as VideoViewHolder
                holder.videoTypeLayoutBinding.incFooterLayout.datum = datum
            }
            PostType.ALBUM -> {
                holder as AlbumViewHolder
                holder.albumTypeLayoutBinding.incFooterLayout.datum = datum
            }
        }
    }

    fun setTwoComments(datum: Datum, holder: RecyclerView.ViewHolder) {
        var listC = mutableListOf<Comment>()
        if (!datum.comments.isEmpty()) {
            listC.add(datum.comments[0])
        }

        var layoutManager: LinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var commentRec: RecyclerView
        when (datum.post_type) {
            PostType.STATUS -> {
                holder as StatusViewHolder
                commentRec = holder.statusTypeLayoutBinding.rcvComment
                commentRec.layoutManager = layoutManager
                var adapter = CommentAdapter(context, listC)
                commentRec.adapter = adapter
            }
            PostType.PHOTO -> {
                holder as PhotoViewHolder
                commentRec = holder.photoTypeLayoutBinding.rcvComment
                commentRec.layoutManager = layoutManager
                var adapter = CommentAdapter(context, listC)
                commentRec.adapter = adapter
            }
            PostType.VIDEO -> {
                holder as VideoViewHolder
                commentRec = holder.videoTypeLayoutBinding.rcvComment
                commentRec.layoutManager = layoutManager
                var adapter = CommentAdapter(context, listC)
                commentRec.adapter = adapter
            }
            PostType.ALBUM -> {
                holder as AlbumViewHolder
                commentRec = holder.albumTypeLayoutBinding.rcvComment
                commentRec.layoutManager = layoutManager
                var adapter = CommentAdapter(context, listC)
                commentRec.adapter = adapter
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            var datum: Datum = listDatum[position - 1]
            var postType = datum.post_type

            if (postType == 1) {
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                // set status
                if (datum.content != null) {
                    (holder as StatusViewHolder).onBind(datum)
                } else {
                    (holder as StatusViewHolder).statusTypeLayoutBinding.txtStatus.visibility =
                        View.GONE
                }

                // set two comment
                if (datum.comments.size > 0) {
                    var listC = mutableListOf<Comment>()
                    listC.add(datum.comments.get(0))
                    var manager: LinearLayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                setTwoComments(datum, holder)

            } else if (postType == 2) {
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                // set status
                var photoViewHolder = holder as PhotoViewHolder
                if (datum.content != null) {
                    photoViewHolder.photoTypeLayoutBinding.txtStatus.text = datum.content
                }

                // set image
                if (datum.mediaData.size > 0) {
                    var mediaDatum = datum.mediaData.get(0)
                    photoViewHolder.photoTypeLayoutBinding.imgPhoto.scaleType =
                        ImageView.ScaleType.CENTER_CROP
                    context?.let {
                        Glide.with(it).load(mediaDatum.src)
                            .into(photoViewHolder.photoTypeLayoutBinding.imgPhoto)
                    }
                }

                setTwoComments(datum, holder)

            } else if (postType == 3) {
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                // set status
                var videoViewHolder = holder as VideoViewHolder
                if (datum.content != null) {
                    videoViewHolder.videoTypeLayoutBinding.txtStatus.text = datum.content
                    // set video
                    if (datum.mediaData.size > 0) {
                        var mediaDatum = datum.mediaData.get(0)
                        var url = mediaDatum.src
                        var simpleExoPlayer: SimpleExoPlayer? =
                            context?.let { SimpleExoPlayer.Builder(it).build() }
                        holder.videoTypeLayoutBinding.plvVideo.player = simpleExoPlayer

                        var mediaItem: MediaItem = MediaItem.fromUri(url)
                        with(simpleExoPlayer) {
                            this?.addMediaItem(mediaItem)
                        }

                        if (simpleExoPlayer != null) {
                            simpleExoPlayer.prepare()
                        }
                    }
                }

                setTwoComments(datum, holder)

            } else if (postType == 4) {
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                var albumViewHolder = holder as AlbumViewHolder

                // set status
                if (datum.content != null) {
                    albumViewHolder.albumTypeLayoutBinding.txtStatus.text = datum.content
                } else {
                    albumViewHolder.albumTypeLayoutBinding.txtStatus.visibility = View.GONE
                }

                var listMediaDatum = datum.mediaData
                var size = listMediaDatum.size

                if (size == 2) {
                    holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.visibility = View.VISIBLE
                    holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.visibility = View.GONE

                    var img1 =
                        holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.two_photo_first_pic
                    var img2 =
                        holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.two_photo_second_pic
                    Glide.with(context).load(listMediaDatum[0].src).into(img1)
                    Glide.with(context).load(listMediaDatum[1].src).into(img2)
                } else if (size == 3) {
                    holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.visibility = View.VISIBLE
                    holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.visibility = View.GONE

                    var img1 =
                        holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.three_photo_first_pic
                    var img2 =
                        holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.three_photo_second_pic
                    var img3 =
                        holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.three_photo_third_pic
                    Glide.with(context).load(listMediaDatum[0].src).into(img1)
                    Glide.with(context).load(listMediaDatum[1].src).into(img2)
                    Glide.with(context).load(listMediaDatum[1].src).into(img3)
                } else if (size == 4) {
                    holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.visibility = View.VISIBLE
                    holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.visibility = View.GONE

                    var img1 =
                        holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.four_photo_first_pic
                    var img2 =
                        holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.four_photo_second_pic
                    var img3 =
                        holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.four_photo_third_pic
                    var img4 =
                        holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.four_photo_fourth_pic
                    Glide.with(context).load(listMediaDatum[0].src).into(img1)
                    Glide.with(context).load(listMediaDatum[1].src).into(img2)
                    Glide.with(context).load(listMediaDatum[1].src).into(img3)
                    Glide.with(context).load(listMediaDatum[1].src).into(img4)
                } else if (size == 5) {
                    holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.visibility = View.VISIBLE
                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.visibility = View.GONE

                    var img1 =
                        holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.five_photo_first_pic
                    var img2 =
                        holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.five_photo_second_pic
                    var img3 =
                        holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.five_photo_third_pic
                    var img4 =
                        holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.five_photo_fourth_pic
                    var img5 =
                        holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.five_photo_fifth_pic
                    Glide.with(context).load(listMediaDatum[0].src).into(img1)
                    Glide.with(context).load(listMediaDatum[1].src).into(img2)
                    Glide.with(context).load(listMediaDatum[1].src).into(img3)
                    Glide.with(context).load(listMediaDatum[1].src).into(img4)
                    Glide.with(context).load(listMediaDatum[1].src).into(img5)
                } else if (size > 5) {
                    holder.albumTypeLayoutBinding.incAlbum2PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum3PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum4PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbum5PhotoLayout.visibility = View.GONE
                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.visibility =
                        View.VISIBLE

                    var img1 =
                        holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.multi_photo_first_pic
                    var img2 =
                        holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.multi_photo_second_pic
                    var img3 =
                        holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.multi_photo_third_pic
                    var img4 =
                        holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.multi_photo_fourth_pic
                    var img5 =
                        holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.multi_photo_fifth_pic

                    Glide.with(context).load(listMediaDatum[0].src).into(img1)
                    Glide.with(context).load(listMediaDatum[1].src).into(img2)
                    Glide.with(context).load(listMediaDatum[1].src).into(img3)
                    Glide.with(context).load(listMediaDatum[1].src).into(img4)
                    Glide.with(context).load(listMediaDatum[1].src).into(img5)

                    holder.albumTypeLayoutBinding.incAlbumMultiplePhotoLayout.txt_remaining_photo_number.text =
                        "+${size - 4}"
                }

                setTwoComments(datum, holder)
            }

        }

        //
        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDatum.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEAD
        }
        var datum = listDatum[position - 1]
        var postType = datum.post_type
        if (postType == 1) {
            return PostType.STATUS
        } else if (postType == 2) {
            return PostType.PHOTO
        } else if (postType == 3) {
            return PostType.VIDEO
        } else if (postType == 4) {
            return PostType.ALBUM
        } else return PostType.LINK
    }

    fun setData(listP: List<Datum>) {
        listP.shuffled()
        this.listDatum = listP as MutableList<Datum>
        notifyDataSetChanged()
    }
}


