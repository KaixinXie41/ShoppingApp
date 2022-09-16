package com.example.shoppingappproject.view.supportChat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.ViewSenderBinding
import com.example.shoppingappproject.databinding.ViewSupportChatBinding
import com.example.shoppingappproject.model.remote.OperationalCallback
import com.example.shoppingappproject.view.Other.SupportChat
import com.example.shoppingappproject.view.Other.SupportChatSenderData

class SupportChatAdapter(
    private val context: Context,
    private val chats:MutableList<SupportChat>,
    private val supportChatSenderData: SupportChatSenderData,
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var supportChatViewBinding: ViewSupportChatBinding
    private lateinit var bindingSender: ViewSenderBinding


    override fun getItemCount() = chats.size

    override fun getItemViewType(position: Int): Int {
        return chats[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == SENDER_VIEW) {
            supportChatViewBinding = ViewSupportChatBinding.inflate(layoutInflater, parent, false)
            SenderViewHolder(supportChatViewBinding.root)
        } else {
            bindingSender = ViewSenderBinding.inflate(layoutInflater, parent, false)
            ReceiverViewHolder(bindingSender.root)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (chats[position].viewType == SENDER_VIEW) {
            (holder as SenderViewHolder).bind(position)
        } else {
            (holder as ReceiverViewHolder).bind(position)
        }

        holder.itemView.setOnClickListener {

            val builder = AlertDialog.Builder(context).apply {
                setIcon(R.drawable.ic_baseline_delete_forever_24)
                setTitle(context.getString(R.string.delete))
                setMessage(context.getString(R.string.confirm_delete))
                setPositiveButton("Confirm") { dialog, _ ->
                    dialog.dismiss()
                    try {
                        if (position == chats.size) {
                            chats.removeAt(position - 1)
                            notifyItemRemoved(position - 1)
                        } else {
                            chats.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    } catch (e: Exception) {
                    }
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }

            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.show()

            true
        }

    }
    inner class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtSender: TextView = bindingSender.txtSender
        fun bind(position: Int) {
            txtSender.text = chats[position].text
        }
    }

    inner class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtSender: TextView = supportChatViewBinding.txtSupport
        private val button1: Button = supportChatViewBinding.btnMessage1
        private val button2: Button = supportChatViewBinding.btnMessage2
        private val button3: Button = supportChatViewBinding.btnMessage3

        fun bind(position: Int) {
            txtSender.text = chats[position].text
            button1.text = supportChatSenderData.button1
            button2.text = supportChatSenderData.button2
            button3.text = supportChatSenderData.button3

        }

    }

    companion object {
        const val SENDER_VIEW = 1

    }

}
