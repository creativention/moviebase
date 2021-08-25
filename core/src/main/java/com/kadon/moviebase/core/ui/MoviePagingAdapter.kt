package com.kadon.moviebase.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kadon.moviebase.core.R
import com.kadon.moviebase.core.databinding.RecyclerviewLayoutMovieGridBinding
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.utils.GlideApp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MoviePagingAdapter.ListViewHolder>(MOVIE_COMPARATOR) {
    var onMovieClick: ((Movie) -> Unit)? = null

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewLayoutMovieGridBinding.bind(view)

        fun bind(movie: Movie?) {
            with(binding) {
                tvTitle.text = movie?.movieTitle
                tvDate.text = movie?.releaseDate?.let { getFormattedDate(it) }
                GlideApp.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                    .into(imageViewPoster)
                ratingBar.stepSize = 0.5f

                val vote = movie?.voteAverage?.toFloat()?:0F
                ratingBar.rating = vote / 2

                tvVote.text = String.format(
                    "%s%% of %s voters",
                    (vote * 10).toInt(),
                    movie?.voteCount
                )
            }
        }

        private fun getFormattedDate(s: String) : String {
            return try {
                val date = LocalDate.parse(s, DateTimeFormatter.ISO_DATE)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                date.format(formatter)
            } catch (e: DateTimeParseException) {
                "-"
            }
        }

        init {
            binding.root.setOnClickListener {
                val movieItem = getItem(bindingAdapterPosition)
                movieItem?.let { item -> onMovieClick?.invoke(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_layout_movie_grid, parent, false)
        )

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}