package com.lx.project5

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.project5.databinding.ReviewItemBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){
    //각 아이템에 보여질 데이터를 담고 있는것
    var items = ArrayList<ReviewSaveData>()
    var context: Context? = null

    var listener: OnReviewItemClickListener? = null
    //리싸이클러뷰가 아이템 개수가 몇 개인지 물어볼때
    override fun getItemCount(): Int = items.size
    //각 아이템의 모양이 처음 만들어 질때
    override fun onCreateViewHolder(parent : ViewGroup, viewType:Int): ReviewAdapter.ViewHolder{
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }
    //이미 만들어진 아이템의 모양에 데이터만 설정할 때
    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int){
        val item = items[position]
        holder.setItem(item)
    }
    //각 아이템의 모양을 재사용하기 위해 만들어진 것
    inner class ViewHolder(val binding: ReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        //데이터 설정
        //하나의 아이템을 위한 데이터가 전달되었을 때 화면에 어떻게 표시할 지 설정
        fun setItem(item: ReviewSaveData){
            //이미지 표시하기
            item.saveImage?.apply{
                val uri = Uri.parse("http://192.168.0.12:8001${this}")
                Glide.with(binding.reviewMemImageView).load(uri).into(binding.reviewMemImageView)
            }

            binding.reviewName.text = item.saveImage
            binding.reviewTitle1.text = item.saveReviewTitle
            binding.reviewContent1.text = item.saveReviewContent


        }

    }
}
