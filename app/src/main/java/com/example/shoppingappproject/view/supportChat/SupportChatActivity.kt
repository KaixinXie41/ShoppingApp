package com.example.shoppingappproject.view.supportChat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappproject.databinding.ActivitySupportChatBinding
import com.example.shoppingappproject.view.Other.SupportChat
import com.example.shoppingappproject.view.Other.SupportChatSenderData
import com.google.firebase.database.*

class SupportChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySupportChatBinding
    private lateinit var chatAdapter: SupportChatAdapter
    private var items:MutableList<SupportChat> = mutableListOf()
    private var ref:DatabaseReference = FirebaseDatabase.getInstance().getReference("SUPPORT_CHAT")
    private var supportChatSenderData1 = SupportChatSenderData(
        button1 ="Regarding last order",
        button2 ="Regarding payment/cashback",
        button3 ="Regarding order and delivery"
    )
    private var supportChatSenderData2 = SupportChatSenderData(
        button1 = "Want to know about order details?",
        button2 = "Want to chat with customer executive",
        button3 = "Want to call with customer executive?"
    )
    private var supportChatSenderData3 = SupportChatSenderData(
        button1 ="want to change your payment method?",
        button2 ="want to check your payment bill?",
        button3 ="want to get your cashback?"
    )
    private var supportChatSenderData4 = SupportChatSenderData(
        button1 ="want to check delivery statue?",
        button2 ="want to check your order detail?",
        button3 ="want to change your delivery address"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchNotes()
        addMoreChat()
    }




    private fun addMoreChat() {
        binding.apply {
            btnSend.setOnClickListener{
                val supportChat = SupportChat(2,edtMessage.text.toString())
                addMoreChat(supportChat)
                edtMessage.text?.clear()
            }
        }
    }

    private fun fetchNotes() {
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    items.clear()
                    for(h in snapshot.children){
                        val chat = h.getValue(SupportChat::class.java)
                        items.add(chat!!)
                    }
                    chatAdapter = SupportChatAdapter(this@SupportChatActivity, items, supportChatSenderData1)
                    binding.apply {
                        recyclerViewSupport.layoutManager =
                            LinearLayoutManager(this@SupportChatActivity)
                        recyclerViewSupport.adapter = chatAdapter
                    }
                    if(items.size == 1){
                        addSupportChat().apply {
                            chatAdapter = SupportChatAdapter(this@SupportChatActivity, items, supportChatSenderData2)
                        }
                    }
                }
            }
        })
    }

    private fun addSupportChat() {
        binding.apply {
            val str: String = "Hi this is ChatBot 001, How may I help you?"
            val supportChat = SupportChat(1, str)
            addMoreChat(supportChat)
        }

    }
    private fun addMoreChat(chat: SupportChat) {
        val noteId = ref.push().key.toString()
        ref.child(noteId).setValue(chat).addOnCompleteListener {
            fetchNotes()
        }
    }
}