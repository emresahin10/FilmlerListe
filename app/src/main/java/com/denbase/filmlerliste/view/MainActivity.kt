package com.denbase.filmlerliste.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.denbase.filmlerliste.R
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}