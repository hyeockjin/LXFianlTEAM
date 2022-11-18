package com.lx.project5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class ChatMessageAdapter(private val context: Context, private val messageList:ArrayList<Message>):
    // 보내는 쪽과 받는 쪽2개의 리사이클 뷰를 전달 받기 위해 RecyclerView로 정의
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 상태 구분자
    private val receive = 1
    private val send = 2

    // 보낸쪽 채팅
    class SendViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView = itemView.findViewById(R.id.send_message_text)
    }
    // 받은쪽 채팅
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recyclerMessage: TextView = itemView.findViewById(R.id.receive_message_text)
    }
    // 받는 쪽이냐 보내는 쪽이냐에 따라 어떤 ViewHolder를 선택할 것인지 정하기 위해 변수 선언
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){ // 받는 화면
            val view:View = LayoutInflater.from(context).inflate(R.layout.chat_receive, parent, false)
            ReceiveViewHolder(view)
        }else{ // 보내는 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_send,parent,false)
            SendViewHolder(view)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 현재 메시지
        val currentMessage = messageList[position]

        // 보내는  데이터
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text = currentMessage.message
        }else{// 받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.recyclerMessage.text = currentMessage.message
        }
    }
    override fun getItemViewType(position: Int): Int {
        // 메시지 값
        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
             send
        }else{
             receive
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}