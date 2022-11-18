package com.lx.project5

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 채팅창 채팅 목록 Firebase
class MemberAdapter(private val context: Context, val memberList:ArrayList<Member>): RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){

    /**
     * 화면 설정
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(R.layout.member_layout,parent,false)

        return MemberViewHolder(view)
    }
    /**
     * 데이터 설정
     */
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        // 데이터 담기, 화면에 데이터 보여주기
        val currentMember = memberList[position]
        holder.nameText.text = currentMember.name

        holder.itemView.setOnClickListener{

            val intent = Intent(context,ChatActivity::class.java)

            // 채팅액티비티로 넘길 데이터담기
            intent.putExtra("name",currentMember.name)
            intent.putExtra("uId",currentMember.uId)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    class MemberViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.name_text)
    }
}