package com.example.btvn3.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btvn3.DetailActivity
import com.example.btvn3.R
import com.example.btvn3.models.AlbumType
import com.example.btvn3.models.Comment
import com.example.btvn3.models.Datum
import com.example.btvn3.models.MediaDatum
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.android.synthetic.main.album_4_photo_layout.view.*
import java.security.AccessController.getContext

class DatumAdapter : RecyclerView.Adapter<DatumAdapter.DatumViewHolder> {
    private lateinit var context: Context
    private var listDatum = mutableListOf<Datum>()

    private val TYPE_HEAD = 10

    constructor(context: Context, listDatum: MutableList<Datum>) {
        this.context = context
        this.listDatum = listDatum
    }

    inner class DatumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // header
        lateinit var profileImage: ImageView
        lateinit var displayName: TextView
        lateinit var createTime: TextView

        // footer
        lateinit var reactCount: TextView
        lateinit var commentCount: TextView
        lateinit var shareCount: TextView

        // status
        lateinit var status: TextView

        // photo
        lateinit var image: ImageView

        // video
        lateinit var video: StyledPlayerView

        // album
        lateinit var album2PhotoLayout: LinearLayout
        lateinit var album3PhotoLayout: LinearLayout
        lateinit var album4PhotoLayout: LinearLayout
        lateinit var album5PhotoLayout: LinearLayout
        lateinit var albumMutiPhotoLayout: LinearLayout

        lateinit var firstPic: ImageView
        lateinit var secondPic: ImageView
        lateinit var thirdPic: ImageView
        lateinit var fourthPic: ImageView
        lateinit var fifthPic: ImageView
        lateinit var remainingPhoto: TextView

        // link: hien thi richLinkPreview

        // recyclerView list comment
        lateinit var recyclerViewComment: RecyclerView
    }

    fun initView(holder: DatumViewHolder) {
        // status
        holder.status = holder.itemView.findViewById(R.id.txt_status)

        // header
        holder.profileImage = holder.itemView.findViewById(R.id.iv_profile_image_datum_item)
        holder.displayName = holder.itemView.findViewById(R.id.txt_display_name_datum_item)
        holder.createTime = holder.itemView.findViewById(R.id.txt_create_time_datum_item)

        // footer
        holder.reactCount = holder.itemView.findViewById(R.id.txt_reaction_count)
        holder.commentCount = holder.itemView.findViewById(R.id.txt_comment_count)
        holder.shareCount = holder.itemView.findViewById(R.id.txt_share_count)

        // recyclerView comment
        holder.recyclerViewComment = holder.itemView.findViewById(R.id.rcv_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatumViewHolder {
        if (viewType == TYPE_HEAD) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_rcv_layout, parent, false)
            return DatumViewHolder(view)
        } else if (viewType == AlbumType.STATUS) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.datum_type_status_layout, parent, false)
            return DatumViewHolder(view)
        } else if (viewType == AlbumType.PHOTO) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.datum_type_photo_layout, parent, false)
            return DatumViewHolder(view)
        } else if (viewType == AlbumType.VIDEO) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.datum_type_video_layout, parent, false)
            return DatumViewHolder(view)
        } else if (viewType == AlbumType.ALBUM) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.datum_type_album_layout, parent, false)
            return DatumViewHolder(view)
        } else {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.datum_type_link_layout, parent, false)
            return DatumViewHolder(view)
        }
    }

    fun setTwoComments(rcvComment: RecyclerView, adapter: CommentAdapter) {
        var layoutManager: LinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcvComment.layoutManager = layoutManager
        rcvComment.adapter = adapter
    }

    fun setHeaderData(datum: Datum, holder: DatumViewHolder) {
        context?.let { Glide.with(it).load(datum.user.avatar).into(holder.profileImage) }
        holder.displayName.text = datum.user.display_name
        var createTime = TimeAgo.using(datum.create_time.toLong())
        holder.createTime.text = createTime
    }

    fun setFooterData(datum: Datum, holder: DatumViewHolder) {
        holder.reactCount.text = datum.counts.react_count.toString()
        holder.commentCount.text = datum.counts.comment_count.toString() + " Bình luận"
        holder.shareCount.text = datum.counts.share_count.toString() + " Chia sẻ"
    }

    override fun onBindViewHolder(holder: DatumViewHolder, position: Int) {
        if (position > 0) {
            var datum: Datum = listDatum[position - 1]
            var postType = datum.post_type


            if (postType == 1) {
                // set status
                initView(holder)
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                if (datum.content != null) {
                    holder.status.text = datum.content
                } else {
                    holder.status.visibility = View.GONE
                }

                // set two comment
                if (datum.comments.size > 0) {
                    var listC = mutableListOf<Comment>()
                    listC.add(datum.comments.get(0))
                    var manager: LinearLayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    holder.recyclerViewComment.layoutManager = manager
                    var adapter: CommentAdapter = CommentAdapter(context, listC)
                    holder.recyclerViewComment.adapter = adapter
                }
            } else if (postType == 2) {
                initView(holder)
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                // set status
                if (datum.content != null) {
                    holder.status.text = datum.content
                }

                // set two comment
                if (datum.comments.size >= 2) {
                    var listC = mutableListOf<Comment>()
                    listC.add(datum.comments.get(0))
                    var adapter: CommentAdapter = CommentAdapter(context, listC)
                    setTwoComments(holder.recyclerViewComment, adapter)
                }

                // set image
                holder.image = holder.itemView.findViewById(R.id.img_photo)
                if (datum.mediaData.size > 0) {
                    var mediaDatum = datum.mediaData.get(0)
                    holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
                    context?.let { Glide.with(it).load(mediaDatum.src).into(holder.image) }
                }
            } else if (postType == 3) {
                initView(holder)
                setHeaderData(datum, holder)
                setFooterData(datum, holder)
                // set status
                if (datum.content != null) {
                    holder.status.text = datum.content
                    // set video
                    if (datum.mediaData.size > 0) {
                        var mediaDatum = datum.mediaData.get(0)
                        var url = mediaDatum.src
                        var simpleExoPlayer: SimpleExoPlayer? =
                            context?.let { SimpleExoPlayer.Builder(it).build() }
                        holder.video = holder.itemView.findViewById(R.id.plv_video)
                        holder.video.player = simpleExoPlayer

                        var mediaItem: MediaItem = MediaItem.fromUri(url)
                        with(simpleExoPlayer) {
                            this?.addMediaItem(mediaItem)
                        }

                        if (simpleExoPlayer != null) {
                            simpleExoPlayer.prepare()
                        }
                    }
                }
                // set two comment
                if (datum.comments.size >= 2) {
                    var listC = mutableListOf<Comment>()
                    listC.add(datum.comments.get(0))
                    var adapter: CommentAdapter = CommentAdapter(context, listC)
                    setTwoComments(holder.recyclerViewComment, adapter)
                }
            } else if (postType == 4) {
                initView(holder)
                setHeaderData(datum, holder)
                setFooterData(datum, holder)

                // set status
                if (datum.content != null) {
                    holder.status.text = datum.content
                } else {
                    holder.status.visibility = View.GONE
                }

                var listMediaDatum = datum.mediaData
                var size = listMediaDatum.size

                holder.album2PhotoLayout =
                    holder.itemView.findViewById(R.id.inc_album_2_photo_layout)
                holder.album3PhotoLayout =
                    holder.itemView.findViewById(R.id.inc_album_2_photo_layout)
                holder.album4PhotoLayout =
                    holder.itemView.findViewById(R.id.inc_album_2_photo_layout)
                holder.album5PhotoLayout =
                    holder.itemView.findViewById(R.id.inc_album_2_photo_layout)
                holder.albumMutiPhotoLayout =
                    holder.itemView.findViewById(R.id.inc_album_2_photo_layout)

                if (size == 2) {

                    holder.album2PhotoLayout.visibility = View.VISIBLE
                    holder.album3PhotoLayout.visibility = View.GONE
                    holder.album4PhotoLayout.visibility = View.GONE
                    holder.album5PhotoLayout.visibility = View.GONE
                    holder.albumMutiPhotoLayout.visibility = View.GONE

                    holder.firstPic = holder.itemView.findViewById(R.id.two_photo_first_pic)
                    holder.secondPic = holder.itemView.findViewById(R.id.two_photo_second_pic)
                    Glide.with(context).load(listMediaDatum[0].src).into(holder.firstPic)
                    Glide.with(context).load(listMediaDatum[1].src).into(holder.secondPic)
                } else if (size == 3) {
                    holder.album3PhotoLayout.visibility = View.VISIBLE
                    holder.album2PhotoLayout.visibility = View.GONE
                    holder.album4PhotoLayout.visibility = View.GONE
                    holder.album5PhotoLayout.visibility = View.GONE
                    holder.albumMutiPhotoLayout.visibility = View.GONE

                    holder.firstPic = holder.itemView.findViewById(R.id.three_photo_first_pic)
                    holder.secondPic = holder.itemView.findViewById(R.id.three_photo_second_pic)
                    holder.thirdPic = holder.itemView.findViewById(R.id.three_photo_third_pic)
                    Glide.with(context).load(listMediaDatum[0].src).into(holder.firstPic)
                    Glide.with(context).load(listMediaDatum[1].src).into(holder.secondPic)
                    Glide.with(context).load(listMediaDatum[2].src).into(holder.thirdPic)
                } else if (size == 4) {
                    holder.album4PhotoLayout.visibility = View.VISIBLE
                    holder.album2PhotoLayout.visibility = View.GONE
                    holder.album4PhotoLayout.visibility = View.GONE
                    holder.album5PhotoLayout.visibility = View.GONE
                    holder.albumMutiPhotoLayout.visibility = View.GONE

                    holder.firstPic = holder.itemView.findViewById(R.id.four_photo_first_pic)
                    holder.secondPic = holder.itemView.findViewById(R.id.four_photo_second_pic)
                    holder.thirdPic = holder.itemView.findViewById(R.id.four_photo_third_pic)
                    holder.fourthPic = holder.itemView.findViewById(R.id.four_photo_fourth_pic)

                    Glide.with(context).load(listMediaDatum[0].src).into(holder.firstPic)
                    Glide.with(context).load(listMediaDatum[1].src).into(holder.secondPic)
                    Glide.with(context).load(listMediaDatum[2].src).into(holder.thirdPic)
                    Glide.with(context).load(listMediaDatum[3].src).into(holder.fourthPic)
                } else if (size == 5) {
                    holder.album5PhotoLayout.visibility = View.VISIBLE
                    holder.album3PhotoLayout.visibility = View.GONE
                    holder.album4PhotoLayout.visibility = View.GONE
                    holder.album2PhotoLayout.visibility = View.GONE
                    holder.albumMutiPhotoLayout.visibility = View.GONE

                    holder.firstPic = holder.itemView.findViewById(R.id.five_photo_first_pic)
                    holder.secondPic = holder.itemView.findViewById(R.id.five_photo_second_pic)
                    holder.thirdPic = holder.itemView.findViewById(R.id.five_photo_third_pic)
                    holder.fourthPic = holder.itemView.findViewById(R.id.five_photo_fourth_pic)
                    holder.fifthPic = holder.itemView.findViewById(R.id.five_photo_fifth_pic)

                    Glide.with(context).load(listMediaDatum[0].src).into(holder.firstPic)
                    Glide.with(context).load(listMediaDatum[1].src).into(holder.secondPic)
                    Glide.with(context).load(listMediaDatum[2].src).into(holder.thirdPic)
                    Glide.with(context).load(listMediaDatum[3].src).into(holder.fourthPic)
                    Glide.with(context).load(listMediaDatum[4].src).into(holder.fifthPic)
                } else if (size > 5) {
                    holder.albumMutiPhotoLayout =
                        holder.itemView.findViewById(R.id.inc_album_multiple_photo_layout)
                    holder.albumMutiPhotoLayout.visibility = View.VISIBLE
                    holder.album3PhotoLayout.visibility = View.GONE
                    holder.album4PhotoLayout.visibility = View.GONE
                    holder.album5PhotoLayout.visibility = View.GONE
                    holder.album2PhotoLayout.visibility = View.GONE

                    holder.firstPic = holder.itemView.findViewById(R.id.multi_photo_first_pic)
                    holder.secondPic = holder.itemView.findViewById(R.id.multi_photo_second_pic)
                    holder.thirdPic = holder.itemView.findViewById(R.id.multi_photo_third_pic)
                    holder.fourthPic = holder.itemView.findViewById(R.id.multi_photo_fourth_pic)
                    holder.fifthPic = holder.itemView.findViewById(R.id.multi_photo_fifth_pic)
                    holder.remainingPhoto =
                        holder.itemView.findViewById(R.id.txt_remaining_photo_number)

                    Glide.with(context).load(listMediaDatum[0].src).into(holder.firstPic)
                    Glide.with(context).load(listMediaDatum[1].src).into(holder.secondPic)
                    Glide.with(context).load(listMediaDatum[2].src).into(holder.thirdPic)
                    Glide.with(context).load(listMediaDatum[3].src).into(holder.fourthPic)
                    Glide.with(context).load(listMediaDatum[4].src).into(holder.fifthPic)
                    holder.remainingPhoto.text = "+${size - 4}"
                }
                // set two comment
                if (datum.comments.size >= 2) {
                    var listC = mutableListOf<Comment>()
                    listC.add(datum.comments.get(0))
                    var adapter = CommentAdapter(context, listC)
                    setTwoComments(holder.recyclerViewComment, adapter)
                }
            }

            holder.itemView.setOnClickListener {
                var intent = Intent(context, DetailActivity::class.java)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return listDatum.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEAD
        }
        var datum = listDatum[position - 1]
        var postType = datum.post_type
        if (postType == 1) {
            return AlbumType.STATUS
        } else if (postType == 2) {
            return AlbumType.PHOTO
        } else if (postType == 3) {
            return AlbumType.VIDEO
        } else if (postType == 4) {
            return AlbumType.ALBUM
        } else return AlbumType.LINK
    }
}