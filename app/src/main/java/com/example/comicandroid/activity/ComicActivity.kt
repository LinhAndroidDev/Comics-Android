package com.example.comicandroid.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.comicandroid.PageCurlView
import com.example.comicandroid.R
import com.example.comicandroid.adapter.PageComicAdapter
import com.example.comicandroid.databinding.ActivityComicBinding
import com.example.comicandroid.heightStatusBar
import com.example.comicandroid.model.Comic

class ComicActivity : AppCompatActivity() {

    private var binding: ActivityComicBinding? = null

    @SuppressLint("SetTextI18n")
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
                binding?.viewStatusBar?.layoutParams?.height = heightStatusBar
                showControllerComic()
                val comicView = PageCurlView(this@ComicActivity)
                comicView.setOnCustomActionListener { isVisibleController ->
                    binding?.txtIndex?.text = "Trang ${comicView.mIndex + 1}"
                    binding?.viewController?.translateViewY(isVisibleController)
                }
                comicView.setOnChangeIndexPage { indexPage ->
                    binding?.txtIndex?.text = "Trang $indexPage"
                }
                binding?.viewComic?.addView(comicView)
                comicView.GetListImageBitmap(
                    this@ComicActivity,
                    comic.nameHeader,
                    comic.sizePage
                )

                val pageComicAdapter = PageComicAdapter()
                pageComicAdapter.comics = comicView.mPages
                binding?.rcvPageComic?.adapter = pageComicAdapter

                binding?.back?.setOnClickListener {
                    comicView.actionBackView()
                }

                binding?.next?.setOnClickListener {
                    comicView.actionNextView()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showControllerComic() {
        binding?.let { binding ->
            Glide.with(this).load(R.drawable.ic_next_comic).into(binding.next)
            Glide.with(this).load(R.drawable.ic_next_comic).into(binding.back)
            binding.viewController.isVisible = true
            binding.viewController.translateViewY(true)
            binding.txtIndex.text = "Trang 1"
        }
    }

    private fun View.translateViewY(inView: Boolean) {
        this.post {
            val start = if (inView) this.height else 0f
            val end = if (inView) 0f else this.height
            val translationY = ObjectAnimator.ofFloat(
                binding?.viewController,
                "translationY",
                start.toFloat(),
                end.toFloat()
            )
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