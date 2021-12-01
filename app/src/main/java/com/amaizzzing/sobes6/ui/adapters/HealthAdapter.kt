package com.amaizzzing.sobes6.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaizzzing.sobes6.R
import com.amaizzzing.sobes6.databinding.DateItemBinding
import com.amaizzzing.sobes6.databinding.HealthItemBinding
import com.amaizzzing.sobes6.services.image.IImageLoader
import com.amaizzzing.sobes6.ui.adapters.BaseViewHolder.DateViewHolder
import com.amaizzzing.sobes6.ui.adapters.BaseViewHolder.HealthViewHolder
import com.amaizzzing.sobes6.ui.models.HealthModel
import com.amaizzzing.sobes6.ui.models.IColorStrategy

class HealthAdapter(
    private val imageBackgroundLoader: IImageLoader<ViewGroup>,
    var healthList: List<Any>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int =
        when(healthList[position]) {
            is HealthModel -> R.layout.health_item
            else -> R.layout.date_item
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.health_item -> {
                HealthViewHolder(
                    HealthItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    imageBackgroundLoader
                )
            }
            else -> {
                DateViewHolder(
                    DateItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = healthList[position]

        when(holder) {
            is HealthViewHolder -> {
                item as HealthModel
                with(holder) {
                    setTime(item.time)
                    setTopDavl(item.top)
                    setBottomDavl(item.bottom)
                    setHeartbeat(item.heartbeat)
                    setBackground(item.colorStrategy)
                }
            }
            is DateViewHolder -> {
                holder.setDate(item.toString())
            }
        }
    }

    override fun getItemCount(): Int = healthList.size
}

sealed class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    class HealthViewHolder(
        private val binding: HealthItemBinding,
        private val imageBackgroundLoader: IImageLoader<ViewGroup>
        ): com.amaizzzing.sobes6.ui.adapters.BaseViewHolder(binding.root){
        fun setTime(time: String) {
            binding.time.text = time
        }
        fun setTopDavl(topDavl: String) {
            binding.topDavlItem.text = topDavl
        }
        fun setBottomDavl(bottomDavl: String) {
            binding.bottomDavlItem.text = bottomDavl
        }
        fun setHeartbeat(heartbeat: String){
            binding.heartbeatItemText.text = heartbeat
        }
        fun setBackground(colorStrategy: IColorStrategy) {
            imageBackgroundLoader.loadInto(
                colorStrategy.getColorBackground(),
                binding.mainLayoutItem
            )
        }
    }
    class DateViewHolder(private val binding: DateItemBinding): com.amaizzzing.sobes6.ui.adapters.BaseViewHolder(binding.root){
        fun setDate(date: String) {
            binding.dateItem.text = date
        }
    }
}