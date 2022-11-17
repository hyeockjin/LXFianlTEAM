package com.lx.project5

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.project5.databinding.AwrdItemBinding
import com.lx.project5.databinding.CareItemBinding


class AwrdCommentAdapter : RecyclerView.Adapter<AwrdCommentAdapter.ViewHolder>() {
    // 각 아이템에 보여질 데이터를 담고 있는 것
    var items = ArrayList<AwcData>()

    var listener: OnAwrdItemClickListener? = null

    // 리싸이클러뷰가 아이템 개수가 몇 개인지 물어볼 때
    override fun getItemCount() = items.size

    // 각 아이템의 모양이 처음 만들어질 때
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwrdCommentAdapter.ViewHolder {
        val binding = AwrdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 이미 만들어진 아이템의 모양에 데이터만 설정할 때
    override fun onBindViewHolder(holder: AwrdCommentAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    // 각 아이템의 모양을 재사용하기 위해 만들어진 것
    inner class ViewHolder(val binding: AwrdItemBinding) : RecyclerView.ViewHolder(binding.root) {



        // 하나의 아이템을 위한 데이터가 전달되었을 때 화면에 어떻게 표시할 지 설정
        fun setItem(item:AwcData) {
            // 이미지 표시하기
//            item.profile?.apply {
//                binding.profileView.setImageResource(this)
//            }

            item.careImage?.apply {
//                val uri = Uri.parse("http://172.168.10.58:8001//images/mycarrot1664852393280.jpg")
                val uri = Uri.parse("http://192.168.0.12:8001${this}")
                Glide.with(binding.awrdItemImage)         // 글라이드를 사용하는데,
                    .load(uri)                              // 이미지 파일을 읽어와서,
                    .into(binding.awrdItemImage)                  // 이미지뷰에 넣어주세요
            }
            binding.awrdItemName.text = item.careName.toString()
            binding.awrdItemComment.text = item.comment.toString()




        }
        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(this, binding.root, adapterPosition)
            }
        }

    }

}