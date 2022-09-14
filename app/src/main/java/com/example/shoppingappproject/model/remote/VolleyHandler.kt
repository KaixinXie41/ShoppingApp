package com.example.shoppingappproject.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shoppingappproject.model.remote.data.address.AddressResponse
import com.example.shoppingappproject.model.remote.data.order.Get.OrderResponse
import com.example.shoppingappproject.model.remote.data.productDetails.ProductDetailResponse
import com.example.shoppingappproject.model.remote.Constants.ADD_ADDRESS_END_POINT
import com.example.shoppingappproject.model.remote.data.user.User
import com.example.shoppingappproject.model.remote.Constants.BASE_URL
import com.example.shoppingappproject.model.remote.Constants.CATEGORY_END_POINT
import com.example.shoppingappproject.model.remote.Constants.GET_ADDRESS_END_POINT
import com.example.shoppingappproject.model.remote.Constants.GET_ORDERS_END_POINT
import com.example.shoppingappproject.model.remote.Constants.GET_ORDER_DETAIL_END_POINT
import com.example.shoppingappproject.model.remote.Constants.LOGIN_END_POINT
import com.example.shoppingappproject.model.remote.Constants.PLACE_ORDER_END_POINT
import com.example.shoppingappproject.model.remote.Constants.PRODUCTS_END_POINT
import com.example.shoppingappproject.model.remote.Constants.PRODUCT_DETAIL_END_POINT
import com.example.shoppingappproject.model.remote.Constants.REGISTRATION
import com.example.shoppingappproject.model.remote.Constants.SEARCH_PRODUCTS_END_POINT
import com.example.shoppingappproject.model.remote.Constants.SUB_CATEGORY_END_POINT
import com.example.shoppingappproject.model.remote.data.category.CategoryResponse
import com.example.shoppingappproject.model.remote.data.order.Place.PlaceOrderRequest
import com.example.shoppingappproject.model.remote.data.order.orderDetail.OrderDetailResponse
import com.example.shoppingappproject.model.remote.data.products.ProductsResponse
import com.example.shoppingappproject.model.remote.data.subcategory.SubCategoryResponse
import com.google.gson.Gson
import org.json.JSONObject

class VolleyHandler (private val context:Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)


    //Registration Part
    fun userRegistration(user: User, callback: OperationalCallback): String {
        val url = "$BASE_URL$REGISTRATION"
        val data = JSONObject()
        var message: String? = null

        data.put("full_name", user.fullName)
        data.put("mobile_no", user.mobileNo)
        data.put("email_id", user.emailId)
        data.put("password", user.password)

        val request = JsonObjectRequest(Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                Log.i("tag", message.toString())
                val status = response.getInt("status")
                Log.e("tag", "message is $message")
                if (status == 0) {
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }
            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i("tag", "${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }


    //Login Part
    fun userLogin(user: User, callback: OperationalCallback): String {
        val url = "$BASE_URL$LOGIN_END_POINT"
        val data = JSONObject()
        var message: String? = null

        data.put("email_id", user.emailId)
        data.put("password", user.password)

        val request = JsonObjectRequest(Request.Method.POST, url, data, { response: JSONObject ->
            message = response.getString("message")
            Log.i("tag", message.toString())
            val status = response.getInt("status")
            Log.e("tag", "message is $message")
            if (status == 0) {
                val userResponse = response.getJSONObject("user")
                user.userID = userResponse.getString("user_id")
                user.fullName = userResponse.getString("full_name")
                user.mobileNo = userResponse.getString("mobile_no")
                callback.onSuccess(message.toString())
            } else {
                callback.onFailure(message.toString())
            }
        }, { error: VolleyError ->
            error.printStackTrace()
            Log.i("tag", "${error.printStackTrace()}")
            callback.onFailure(message.toString())
        })
        requestQueue.add(request)
        return message.toString()
    }

    //Logout Part
    fun userLogout(email: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$LOGIN_END_POINT"
        val data = JSONObject()
        var message: String? = null
        data.put("email_id", email)
        val request = JsonObjectRequest(Request.Method.POST, url, data, {
                response: JSONObject ->
            message = response.getString("message")
            Log.i("tag", message.toString())
            val status = response.getInt("status")
            Log.e("tag", "message is $message")
            if (status == 0) {
                callback.onSuccess(message.toString())
            } else {
                callback.onFailure(message.toString())
            }
        }, { error: VolleyError ->
            error.printStackTrace()
            Log.i("tag", "${error.printStackTrace()}")
            callback.onFailure(message.toString())
        })
        requestQueue.add(request)
        return message.toString()
    }

    //Category Part
    fun getCategoryByApi(callback: OperationalCallback): String {
        val url = "$BASE_URL$CATEGORY_END_POINT"
        val message: String? = null

        val request = object : StringRequest(Method.GET, url, Response.Listener {
            val gson = Gson()
            val categoryResponse = gson.fromJson(it.toString(), CategoryResponse::class.java)
            callback.onSuccess(categoryResponse)
            Log.e("tag", it.toString())
        }, {
            Log.e("tag", it.toString())
        }) {}
        requestQueue.add(request)
        return message.toString()
    }


    //SubCategory Part

    fun getSubCategoryFromApi(categoryId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$SUB_CATEGORY_END_POINT" + "category_id=$categoryId"
        Log.e("url", url)
        val message: String? = null


        val request = object : StringRequest(Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val subCategoryResponse =
                    gson.fromJson(it.toString(), SubCategoryResponse::class.java)
                callback.onSuccess(subCategoryResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }) {
        }
        requestQueue.add(request)
        return message.toString()
    }

    //Products Part

    fun getProductFromApi(subCategoryId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$PRODUCTS_END_POINT${subCategoryId}"
        Log.e("url", url)
        val message: String? = null

        val request = object : StringRequest(Method.GET, url, Response.Listener {
            val gson = Gson()
            val productsResponse = gson.fromJson(it.toString(), ProductsResponse::class.java)
            callback.onSuccess(productsResponse)
            Log.e("tag", it.toString())
        }, {
            Log.e("tag", it.toString())
        }) {
        }
        requestQueue.add(request)
        return message.toString()
    }

    fun searchProductFromApi(query: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$SEARCH_PRODUCTS_END_POINT${query}"
        Log.e("url", url)
        val message: String? = null
        val request = object : StringRequest(Method.GET, url, Response.Listener
        {
            val gson = Gson()
            val productsResponse = gson.fromJson(it.toString(), ProductsResponse::class.java)
            callback.onSuccess(productsResponse)
            Log.e("tag", it.toString())
        }, {
            Log.e("tag", it.toString())
        }) {}
        requestQueue.add(request)
        return message.toString()
    }

    //ProductDetails Part
    fun getProductsDetailFromApi(productId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$PRODUCT_DETAIL_END_POINT" + productId
        Log.e("url", url)
        val message: String? = null

        val request = object: StringRequest(Method.GET, url, Response.Listener {
                val gson = Gson()
                val subCategoryResponse = gson.fromJson(it.toString(), ProductDetailResponse::class.java)
                callback.onSuccess(subCategoryResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }
    //Address Part
    fun addAddressToAPI(userId: String, title: String, address: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$ADD_ADDRESS_END_POINT"
        val data = JSONObject()
        var message: String? = null

        data.put("user_id", userId)
        data.put("title", title)
        data.put("address", address)

        val request = JsonObjectRequest(Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                Log.i("tag", message.toString())
                val status = response.getInt("status")
                Log.e("tag", "message is $message")
                if(status == 0){
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }

            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i("tag", "${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }

    fun getAddressFromApi(userId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$GET_ADDRESS_END_POINT" + userId
        Log.e("url", url)
        val message: String? = null


        val request = object: StringRequest(Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val subCategoryResponse = gson.fromJson(it.toString(), AddressResponse::class.java)
                callback.onSuccess(subCategoryResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

    //Order Part
    fun placeOrderToAPI(placeOrderRequest: PlaceOrderRequest, callback: OperationalCallback): String {
        val url = "$BASE_URL$PLACE_ORDER_END_POINT"
        var message: String? = null

        val gson = Gson()
        val data = JSONObject(gson.toJson(placeOrderRequest))
        Log.e("order", data.toString())
        val request = JsonObjectRequest(Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                Log.i("tag", message.toString())
                val status = response.getInt("status")
                Log.e("tag", "message is $message")
                if(status == 0){
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }

            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i("tag", "${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }

    fun getOrdersFromApi(userId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$GET_ORDERS_END_POINT" + userId
        Log.e("url", url)
        val message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val orderResponse = gson.fromJson(it.toString(), OrderResponse::class.java)
                callback.onSuccess(orderResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

    fun getOrderDetailFromApi(orderId: String, callback: OperationalCallback): String {
        val url = "$BASE_URL$GET_ORDER_DETAIL_END_POINT" + "order_id="+orderId

        Log.e("url", url)
        val message: String? = null

        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val orderDetailResponse = gson.fromJson(it.toString(), OrderDetailResponse::class.java)
                callback.onSuccess(orderDetailResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }




}