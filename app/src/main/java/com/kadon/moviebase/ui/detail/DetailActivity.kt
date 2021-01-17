package com.kadon.moviebase.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kadon.moviebase.R

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE_DATA = "extraMovieData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}