package com.denbase.filmlerliste.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denbase.filmlerliste.R
import kotlinx.android.synthetic.main.fragment_movie_detail.*

fun ImageView.downloadImage(url: String?, placeholder : CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeholder).error(R.drawable.lazy_load)

    Glide.with(context).load(url).into(this)
}

fun doPlaceHolder(context: Context) : CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}