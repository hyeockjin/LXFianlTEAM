package com.lx.project5.chatting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lx.project5.MainActivity
import com.lx.project5.appdata.ChatData
import com.lx.project5.databinding.FragmentChatListBinding
// 채팅방 리스트 Firebase
class ChatListFragment : Fragment() {
    var _binding: FragmentChatListBinding? = null
    val binding get() = _binding!!

    lateinit var adapter: MemberAdapter

    lateinit var mAuth:FirebaseAuth
    lateinit var mDbRef:DatabaseReference

    lateinit var chatDataList:ArrayList<ChatData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)



        Log.v("시발","채팅화면 시작")

        //### firebase 인증 초기화, db 초기화, 리스트 초기화
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference
        chatDataList = ArrayList()

        //#### 어뎁터 생성자에 Context와 memberList 리스트 전달, 채팅방 리스트에 데이터 전달용
        adapter = MemberAdapter(activity as MainActivity, chatDataList)

        // #### RealDatabase에서 사용자 정보가져오기
        mDbRef.child("member").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // snapshot에 있는 데이터를 변수에 할당
                for(postSnapshot in snapshot.children){
                    //유저정보 / 데이터 클래스에 있는 사용자 정보 constructor
                    val currentUser = postSnapshot.getValue(ChatData::class.java)

                    // 로그인한 아이디와 db에 등록된 uid가 다를때만 내정보는 안나오고 나를 제외한 채팅방리스트가 나오게
                    if(mAuth.currentUser?.uid != currentUser?.uId ){
                        chatDataList.add(currentUser!!)
                    }
                }
                // 변경한 데이터가 화면에 적용이 되도록
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.v("시발","onDataChange : ${error}")
            }
        })
        binding.memberRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.memberRecyclerView.adapter = adapter
        return binding.root
    }
}