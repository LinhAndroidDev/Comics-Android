package com.example.comicandroid

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.comicandroid.databinding.ActivityComicBinding
import com.example.comicandroid.model.Comic

class ComicActivity : AppCompatActivity() {

    private var binding: ActivityComicBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComicBinding.inflate(layoutInflater, binding?.root, false)
        setContentView(binding?.root)

        setUpFullScreen()

        val comic = intent.getParcelableExtra<Comic>(MainActivity.OBJECT_COMIC)
        comic?.let { comic ->
            binding?.viewController?.isVisible = false
            binding?.imgIntro?.setImageResource(comic.coverPhoto)
            binding?.root?.post {
                binding?.viewController?.isVisible = true
                binding?.viewController?.translateViewY(true)
                val comicView = PageCurlView(this@ComicActivity)
                comicView.setOnCustomActionListener { isVisibleController ->
                    binding?.viewController?.translateViewY(isVisibleController)
                }
                val layoutParams = ViewGroup.MarginLayoutParams(screenWidth, screenHeight)
                layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.margin_top)
                binding?.viewComic?.addView(comicView, layoutParams)
                comicView.GetListImageBitmap(
                    this@ComicActivity,
                    comic.nameHeader,
                    comic.sizePage
                )

                binding?.back?.setOnClickListener {
                    comicView.actionBackView()
                }

                binding?.next?.setOnClickListener {
                    comicView.actionNextView()
                }
            }
        }
    }

    private fun View.translateViewY(inView: Boolean) {
        this.post {
            val start = if(inView) this.height else 0f
            val end = if(inView) 0f else this.height
            val translationY = ObjectAnimator.ofFloat(binding?.viewController, "translationY", start.toFloat(), end.toFloat())
            translationY.duration = 500 // Thời gian dịch chuyển là 1 giây
            translationY.start() // Bắt đầu animation
        }
    }

    private fun setUpFullScreen() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }
}