package com.lagradost.cloudstream3.ui.home

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.lagradost.cloudstream3.LoadResponse
import com.lagradost.cloudstream3.databinding.HomeScrollViewBinding
import com.lagradost.cloudstream3.databinding.HomeScrollViewTvBinding
import com.lagradost.cloudstream3.ui.BaseAdapter
import com.lagradost.cloudstream3.ui.ViewHolderState
import com.lagradost.cloudstream3.ui.settings.Globals.EMULATOR
import com.lagradost.cloudstream3.ui.settings.Globals.TV
import com.lagradost.cloudstream3.ui.settings.Globals.isLayout
import com.lagradost.cloudstream3.utils.UIHelper.setImage

class HomeScrollAdapter(
    fragment: Fragment
) : BaseAdapter<LoadResponse, Any>(fragment, "HomeScrollAdapter".hashCode()) {
    var hasMoreItems: Boolean = false

    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (isLayout(TV or EMULATOR)) {
            HomeScrollViewTvBinding.inflate(inflater, parent, false)
        } else {
            HomeScrollViewBinding.inflate(inflater, parent, false)
        }

        return ViewHolderState(binding)
    }

    override fun onBindContent(
        holder: ViewHolderState<Any>,
        item: LoadResponse,
        position: Int,
    ) {
        val binding = holder.view
        val itemView = holder.itemView
        val isHorizontal =
            binding is HomeScrollViewTvBinding || itemView.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val posterUrl =
            if (isHorizontal) item.backgroundPosterUrl ?: item.posterUrl else item.posterUrl
                ?: item.backgroundPosterUrl

        when (binding) {
            is HomeScrollViewBinding -> {
                binding.homeScrollPreview.setImage(posterUrl)
                binding.homeScrollPreviewTags.apply {
                    text = item.tags?.joinToString(" • ") ?: ""
                    isGone = item.tags.isNullOrEmpty()
                    maxLines = 2
                }
                binding.homeScrollPreviewTitle.text = item.name
            }

            is HomeScrollViewTvBinding -> {
                binding.homeScrollPreview.setImage(posterUrl)
            }
        }
    }
}