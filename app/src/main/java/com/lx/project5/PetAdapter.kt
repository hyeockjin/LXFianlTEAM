package com.lx.project5

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.project5.databinding.DogItemBinding

class PetAdapter : RecyclerView.Adapter<PetAdapter.ViewHolder>(){
    //각 아이템에 보여질 데이터를 담고 있는것
    var items = ArrayList<PetData>()
    var context: Context? = null

    var listener: OnPetItemClickListener? = null
    //리싸이클러뷰가 아이템 개수가 몇 개인지 물어볼때
    override fun getItemCount(): Int = items.size
    //각 아이템의 모양이 처음 만들어 질때
    override fun onCreateViewHolder(parent : ViewGroup, viewType:Int): PetAdapter.ViewHolder{
        val binding = DogItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }
    //이미 만들어진 아이템의 모양에 데이터만 설정할 때
    override fun onBindViewHolder(holder: PetAdapter.ViewHolder, position: Int){
        val item = items[position]
        holder.setItem(item)
    }
    //각 아이템의 모양을 재사용하기 위해 만들어진 것
    inner class ViewHolder(val binding: DogItemBinding):RecyclerView.ViewHolder(binding.root){
        //데이터 설정
        //하나의 아이템을 위한 데이터가 전달되었을 때 화면에 어떻게 표시할 지 설정
        fun setItem(item: PetData){
            //이미지 표시하기
            item.dogImage?.apply{
                val uri = Uri.parse("http://172.30.1.3:8001${this}")
                Glide.with(binding.gridPictureView).load(uri).into(binding.gridPictureView)
            }

            //강아지 이름
            binding.dogNameOutput.text = item.dogName
            //강아지 나이
            binding.dogAgeOutput.text = item.dogAge.toString()
            //강아지 성별
            binding.dogGenderOutput.text = item.dogGender
            //견종
            binding.dogTypeOutput.text = item.dogBreed

        }
        init {
            binding.root.setOnClickListener{
                listener?.onItemClick(this,binding.root,adapterPosition)
            }
        }
    }
}
