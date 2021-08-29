package com.kadon.moviebase.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kadon.moviebase.R
import com.kadon.moviebase.core.domain.model.Movie
import com.kadon.moviebase.core.utils.ColorFactor
import com.kadon.moviebase.core.utils.GlideApp
import com.kadon.moviebase.core.utils.K
import com.kadon.moviebase.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val movieId = bundle.getLong(K.MOVIE_ID)
            val from = bundle.getString(K.EXTRA_FROM)
            initViewModel(movieId, from)
            obserViewModel()
        }

    }

    private fun obserViewModel() {
        viewModel.movie.observe(this, {
            bindToView(it)
        })
    }

    private fun bindToView(movie: Movie?) {
        movie?.let { it ->
            supportActionBar?.title = it.movieTitle
            GlideApp.with(this)
                .asBitmap()
                .load("${K.IMAGE_BACKDROP_BASE}${it.backdropPath}")
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        convertToBitmap(binding.imageViewBackDrop)?.let {
                            resource?.let { it1 -> createPaletteAsync(it1) }
                        }
                        return false
                    }
                })
                .placeholder(R.drawable.img_bg)
                .into(binding.imageViewBackDrop)

            GlideApp.with(this)
                .load("${K.IMAGE_BASE}${it.posterPath}")
                .placeholder(R.drawable.img_bg)
                .into(binding.includeContent.imageViewPosterDetail)

            ("User Score " + (it.voteAverage * 10) + "%").also {
                binding.includeContent.tvUserScore.text = it
            }
            binding.includeContent.tvOriginalTitle.text = it.originalTitle
            binding.includeContent.tvOverview.text = it.overview
            binding.includeContent.tvReleaseDate.text = it.releaseDate

            var isMovieFavorite = movie.isFavorite
            setMovieFavorite(isMovieFavorite)
            binding.includeContent.mbFavorite.setOnClickListener {
                isMovieFavorite = !isMovieFavorite!!

                observeFavorite(movie, isMovieFavorite!!)

            }

            binding.includeContent.ratingBar2.rating = (movie.voteAverage / 2).toFloat()

        }
    }

    private fun initViewModel(movieId: Long, from: String?) {
        viewModel.getMovieDetail(movieId, from)
    }

    private fun observeFavorite(movieDetail: Movie, isMovieFavorite: Boolean) {
        movieDetail.isFavorite = isMovieFavorite
        viewModel.insertMovie(movieDetail)
        setMovieFavorite(isMovieFavorite)
    }

    private fun setToolbarColor(palette: Palette?) {
        // Generate the palette and get the vibrant swatch
        val vibrantSwatch = palette?.vibrantSwatch
        val lightVibrantSwatch = palette?.lightVibrantSwatch
        /*val dominantSwatch = palette?.dominantSwatch
        val mutedSwatch = palette?.darkMutedSwatch*/

        with(binding.toolbarLayoutMain) {
            setContentScrimColor(
                vibrantSwatch?.rgb
                    ?: ContextCompat.getColor(context, R.color.design_default_color_on_primary)
            )
            setExpandedTitleColor(
                lightVibrantSwatch?.rgb
                    ?: ContextCompat.getColor(context, R.color.design_default_color_on_primary)
            )
            setCollapsedTitleTextColor(
                ColorFactor.darkenColor(
                    vibrantSwatch?.rgb
                        ?: ContextCompat.getColor(context, R.color.design_default_color_on_primary),
                    0.4f
                )
            )
        }
    }

    private fun createPaletteAsync(bitmap: Bitmap) {
        val builder = Palette.Builder(bitmap)
        builder.generate { palette: Palette? ->
            //access palette instance here
            setToolbarColor(palette)
        }
    }

    private fun convertToBitmap(imageView: ImageView): Bitmap? {
        var drawable: BitmapDrawable? = null
        if (imageView.drawable != null) {
            drawable = imageView.drawable as BitmapDrawable
        }
        return drawable?.bitmap
    }

    private fun setMovieFavorite(movieFavorite: Boolean?) {
        if (movieFavorite == true) {
            binding.includeContent.mbFavorite.icon =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_24, theme)
        } else {
            binding.includeContent.mbFavorite.icon = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_favorite_border_24,
                theme
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        setSupportActionBar(null)
    }
}