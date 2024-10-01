package com.example.comicandroid.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.comicandroid.PageCurlView
import com.example.comicandroid.R
import com.example.comicandroid.adapter.PageComicAdapter
import com.example.comicandroid.databinding.ActivityComicBinding
import com.example.comicandroid.heightStatusBar
import com.example.comicandroid.model.Comic
import kotlinx.coroutines.launch

class ComicActivity : AppCompatActivity() {
    private var pageComicAdapter: PageComicAdapter? = null

    private var binding: ActivityComicBinding? = null

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComicBinding.inflate(layoutInflater, binding?.root, false)
        setContentView(binding?.root)

        setUpFullScreen()

        val comic = intent.getParcelableExtra<Comic>(MainActivity.OBJECT_COMIC)
        comic?.let { comic ->
            binding?.viewController?.setOnTouchListener { _, _ -> true }
            binding?.viewController?.isVisible = false
            binding?.imgIntro?.setImageResource(comic.coverPhoto)
            binding?.root?.post {
                binding?.viewStatusBar?.layoutParams?.height = heightStatusBar
                showControllerComic()
                val comicView = PageCurlView(this@ComicActivity)
                comicView.setOnCustomActionListener { isVisibleController ->
                    if(isVisibleController) {
                        binding?.rcvPageComic?.scrollToPosition(comicView.mIndex)
                    }
                    binding?.viewController?.translateViewY(isVisibleController)
                }
                comicView.setOnChangeIndexPage { indexPage ->
                    binding?.txtIndex?.text = "Trang ${indexPage + 1}"
                    pageComicAdapter?.setIndexSelected(indexPage)
                }
                binding?.viewComic?.addView(comicView)
                comicView.GetListImageBitmap(
                    this@ComicActivity,
                    comic.nameHeader,
                    comic.sizePage
                )

                pageComicAdapter = PageComicAdapter()
                pageComicAdapter?.comics = comicView.mPages
                pageComicAdapter?.onClickItem = {
                    binding?.rcvPageComic?.scrollToPosition(it)
                    comicView.actionScrollTo(it)
                }
                binding?.rcvPageComic?.adapter = pageComicAdapter

                binding?.back?.setOnClickListener {
                    binding?.rcvPageComic?.scrollToPosition(
                        pageComicAdapter?.selectPosition?.minus(
                            1
                        ) ?: 0
                    )
                    comicView.actionBackView()
                }

                binding?.next?.setOnClickListener {
                    binding?.rcvPageComic?.scrollToPosition(
                        pageComicAdapter?.selectPosition?.plus(1) ?: 0
                    )
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