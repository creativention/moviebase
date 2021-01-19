package com.kadon.moviebase.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadon.moviebase.core.R
import com.kadon.moviebase.core.databinding.RecyclerviewLayoutMovieGridBinding
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.utils.GlideApp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MovieRecyclerViewAdapter: RecyclerView.Adapter<MovieRecyclerViewAdapter.VH>() {
    private var movieData = ArrayList<MovieModel>()
    var onMovieClick: ((MovieModel) -> Unit)? = null

    fun setData(newMovieData: List<MovieModel>?){
        if (newMovieData!=null){
            movieData.clear()
            movieData.addAll(newMovieData)
            //notifyDataSetChanged()
            notifyItemRangeChanged(0, movieData.size)
        }
    }

    fun loadMoreData(refreshedMovieData: List<MovieModel>?){
        if (refreshedMovieData!=null){
            movieData.addAll(refreshedMovieData)
            notifyItemRangeChanged(0, movieData.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout_movie_grid, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = movieData[position]
        holder.bind(item)
    }

    override fun getItemCount() = movieData.size

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = RecyclerviewLayoutMovieGridBinding.bind(itemView)
        fun bind(movie: MovieModel) {
            with(binding){
                tvTitle.text = movie.movieTitle
                tvDate.text = movie.releaseDate?.let { getFormattedDate(it) }
                GlideApp.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(imageViewPoster)
                ratingBar.stepSize = 0.5f
                ratingBar.rating = (movie.voteAverage/2).toFloat()
                tvVote.text = String.format("%s%% of %s voters", (movie.voteAverage*10).toInt(), movie.voteCount)
            }
        }

        init {
            binding.root.setOnClickListener{
                onMovieClick?.invoke(movieData[adapterPosition])
            }
        }


    }

    private fun getFormattedDate(s: String): String {
        return try {
            val date = LocalDate.parse(s, DateTimeFormatter.ISO_DATE)
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            date.format(formatter)
        } catch (e: DateTimeParseException){
            "-"
        }
    }
}