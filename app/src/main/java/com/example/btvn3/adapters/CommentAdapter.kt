package com.example.btvn3.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btvn3.databinding.CommentLayoutBinding
import com.example.btvn3.models.Comment

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private lateinit var context: Context
    private var listComment = mutableListOf<Comment>()

    constructor(context: Context, listC: MutableList<Comment>) {
        this.context = context
        this.listComment = listC
    }

    class ViewHolder(val commentLayoutBinding: CommentLayoutBinding) : RecyclerView.ViewHolder(commentLayoutBinding.root) {

        fun onBind(comment: Comment) {
            commentLayoutBinding.comment = comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val commentLayoutBinding = CommentLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(commentLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var comment = listComment[position]
        holder.onBind(comment)
        
        // set avatar
        Glide.with(context).load(comment.user.avatar).into(holder.commentLayoutBinding.ivAvatarCommentRow)

        if (comment.mediaData.size > 0) {
            var imageUrl = comment.mediaData.get(0)
            Glide.with(context).load(imageUrl).into(holder.commentLayoutBinding.ivImageCommentRow) // chuyen doan code nay sang layout
        } else {
            holder.commentLayoutBinding.ivImageCommentRow.visibility = View.GONE
        }
    }

    override fun getItemCount() = listComment.size


}