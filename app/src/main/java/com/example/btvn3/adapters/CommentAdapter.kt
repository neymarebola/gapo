package com.example.btvn3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btvn3.R
import com.example.btvn3.models.Comment
import com.github.marlonlom.utilities.timeago.TimeAgo

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    lateinit var context: Context
    private var listComment = mutableListOf<Comment>()

    constructor(context: Context, listC: MutableList<Comment>) {
        this.context = context
        listComment = listC
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImage: ImageView = itemView.findViewById(R.id.iv_avatar_comment_row)
        var displayName: TextView = itemView.findViewById(R.id.txt_display_name_comment_row)
        var content: TextView = itemView.findViewById(R.id.txt_content_comment_row)
        var image: ImageView = itemView.findViewById(R.id.iv_image_comment_row)
        var createTime: TextView = itemView.findViewById(R.id.txt_create_time_comment_row)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.comment_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var comment = listComment[position]
        Glide.with(context).load(comment.user.avatar).into(holder.profileImage);
        holder.displayName.text = comment.user.display_name
        holder.content.text = comment.content
        if (!comment.mediaData.isEmpty()) {
            Glide.with(context).load(comment.mediaData[0].toString()).into(holder.image)
        } else {
            holder.image.visibility = View.GONE
        }
        val time = TimeAgo.using(comment.create_time.toLong())
        holder.createTime.text = time
    }

    override fun getItemCount(): Int {
        return listComment.size
    }
}