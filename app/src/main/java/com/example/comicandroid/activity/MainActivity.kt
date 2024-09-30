package com.example.comicandroid.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.comicandroid.adapter.AdvertiseAdapter
import com.example.comicandroid.OverlapItemDecoration
import com.example.comicandroid.R
import com.example.comicandroid.adapter.ComicAdapter
import com.example.comicandroid.databinding.ActivityMainBinding
import com.example.comicandroid.model.Comic

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    companion object {
        const val OBJECT_COMIC = "OBJECT_COMIC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater, binding?.root, false)
        setContentView(binding?.root)

        setUpFullScreen()

        initAdvertisement()
        initListComic()
    }

    private fun initListComic() {
        val adapterComic = ComicAdapter()
        adapterComic.comics = listOf(
            Comic("Doraemon Tập 1", "doraemon", 19, R.drawable.doraemon0),
            Comic("Thám Tử Lừng Danh Conan", "conan", 19, R.drawable.conan0),
            Comic("One Piece Tập 1", "onepiece", 51, R.drawable.onepiece0)
        )
        adapterComic.onClickItem = { comic ->
            val intent = Intent(this@MainActivity, ComicActivity::class.java)
            intent.putExtra(OBJECT_COMIC, comic)
            startActivity(intent)
        }
        binding?.rcvComics?.adapter = adapterComic
    }

    private fun initAdvertisement() {
        binding?.rcvAdvertise?.adapter = AdvertiseAdapter()
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding?.rcvAdvertise)

        // Set ItemDecoration to add overlap/margin between items
        binding?.rcvAdvertise?.addItemDecoration(
            OverlapItemDecoration(
                resources.getDimensionPixelSize(R.dimen.over_lap_left),
                resources.getDimensionPixelSize(R.dimen.over_lap_right),
                isNewRelease = false
            )
        )
    }

    private fun setUpFullScreen() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }
}