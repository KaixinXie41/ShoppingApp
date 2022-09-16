package com.example.shoppingappproject.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingappproject.model.remote.data.address.Address
import com.example.shoppingappproject.model.remote.data.address.AddressResponse
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.AddAddressBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.presenter.address.add.AddAddressMVP
import com.example.shoppingappproject.presenter.address.add.AddAddressPresenter
import com.example.shoppingappproject.presenter.address.get.GetAddressMVP
import com.example.shoppingappproject.presenter.address.get.GetAddressPresenter
import com.example.shoppingappproject.view.other.LoginActivity.Companion.Account_Information
import com.example.shoppingappproject.view.other.LoginActivity.Companion.USER_ID

import com.google.android.material.progressindicator.CircularProgressIndicator

class CheckoutDeliveryFragment : Fragment(),GetAddressMVP.GetAddressView,AddAddressMVP.AddAddressView {

    private lateinit var addAddressPresenter: AddAddressPresenter
    private lateinit var getAddressPresenter: GetAddressPresenter
    private lateinit var addressLArrayList: ArrayList<Address>
    private lateinit var currentView: View
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var addressRadioGroup:RadioGroup
    private lateinit var title:String
    private lateinit var address:String
    private lateinit var hashMap : HashMap<Int,Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView =view
        getAddressPresenter = GetAddressPresenter(VolleyHandler(view.context), this)
        addAddressPresenter = AddAddressPresenter(VolleyHandler(view.context), this)
        sharedPreferences = this.requireActivity().getSharedPreferences(Account_Information,AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        hashMap = HashMap()
        val userId = sharedPreferences.getString(USER_ID, "-1")

        userId?.let{
            getAddressPresenter.getAddress(userId)
        }
        addressRadioGroup = currentView.findViewById(R.id.radio_group_choose_address)
        addressRadioGroup.setOnCheckedChangeListener(){ group:RadioGroup, checkId:Int ->
            val radioButtonCheck = group.findViewById<RadioButton>(group.checkedRadioButtonId)
            radioButtonCheck?.let{
                val index = hashMap[radioButtonCheck.id]
                if(index != null){
                    title = addressLArrayList[index].title
                    address = addressLArrayList[index].address
                }
            }
        }
        val btnNext: Button = view.findViewById(R.id.btn_next_page)
        btnNext.setOnClickListener{
            editor.apply{
                putString(ADDRESS_TITLE, title)
                putString(ADDRESS, address)
            }.apply()
            (this.parentFragment as CheckoutFragment).nextPager()
        }

        val btnAdd: Button = view.findViewById(R.id.btn_add_address)
        btnAdd.setOnClickListener{
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogBinding =AddAddressBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(currentView.context).apply {
            setView(dialogBinding.root)
            setCancelable(false)
        }

        val dialog = builder.create()
        dialogBinding.apply {
            btnSave.setOnClickListener{
                val title = edtAddressTitle.text.toString()
                val address = edtAddress.text.toString()
                val userId = sharedPreferences.getString(USER_ID, "-1")
                userId?.let {
                    addAddressPresenter.addAddress(userId, title, address)

                }
                dialog.dismiss()
            }
            btnCancel.setOnClickListener{
                dialog.dismiss()
            }

        }
        dialog.show()
    }

    override fun onLoad(isLoading:Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }

    override fun setResult(message: String) {
        val userId = sharedPreferences.getString(USER_ID, "-1")
        userId?.let{
            getAddressPresenter.getAddress(userId)
        }
    }

    override fun setResult(addressResponse: AddressResponse?) {
        addressResponse?.let {
            addressLArrayList = addressResponse.addresses
            if(addressLArrayList.size >0){
                title = addressLArrayList[0].title
                address = addressLArrayList[0].address
            }else{
                title = "please add new title"
                address = "please add new address"
            }

            addressRadioGroup.removeAllViews()

            for(i in 0 until addressLArrayList.size) {
                val rdButton = RadioButton(currentView.context)
                val layout: RadioGroup.LayoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                rdButton.layoutParams = layout
                rdButton.setButtonDrawable(R.drawable.raido_btn_style)
                val spannableString = SpannableString(addressLArrayList[i].title)
                spannableString.setSpan(
                    AbsoluteSizeSpan(20, true),
                    0,
                    addressLArrayList[i].title.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                rdButton.text = spannableString
                rdButton.append("\n")
                rdButton.append(addressLArrayList[i].address)
                addressRadioGroup.addView(rdButton)
                hashMap[rdButton.id] = i
                if(i == 0){
                    addressRadioGroup.check(rdButton.id)
                }
             }
        }
    }
    companion object {
        const val ADDRESS_TITLE = "address_title"
        const val ADDRESS = "address_address"
    }
}