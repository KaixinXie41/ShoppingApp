package com.example.shoppingappproject.view.home.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppingappproject.model.remote.data.productDetails.Parameters
import com.example.shoppingappproject.model.remote.data.productDetails.ProductDetailResponse
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.productDetails.Review
import com.example.shoppingappproject.presenter.productDetail.ProductDetailsMVP
import com.example.shoppingappproject.presenter.productDetail.ProductDetailsPresenter
import com.example.shoppingappproject.view.home.ParametersAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProductDetailsFragment : Fragment(), ProductDetailsMVP.ProductDetailsView {

    private lateinit var presenter: ProductDetailsPresenter
    lateinit var currentView:View
    lateinit var productId:String
    private lateinit var cartDao:CartDao
    lateinit var parametersList:ArrayList<Parameters>
    lateinit var parametersAdapter: ParametersAdapter
    lateinit var reviewList:ArrayList<Review>
    private val args : ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_products_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartDao = CartDao(view.context)
        currentView = view
        presenter = ProductDetailsPresenter(VolleyHandler(view.context), this)
        productId = args.productId
        presenter.getProductDetails(productId)

    }

    override fun setResult(productDetailResponse: ProductDetailResponse?) {
        productDetailResponse?.let {
            val product = productDetailResponse.product
            currentView?.let{
                val productDetailsName: TextView = currentView.findViewById(R.id.txt_product_details_name)
                val ratingBar: RatingBar = currentView.findViewById(R.id.ratingBar_product_details)
                val productDesc: TextView = currentView.findViewById(R.id.txt_product_details_desc)
                val imgProductViewPage: ViewPager2 = currentView.findViewById(R.id.product_detail_image_view)
                val ProductPrice: TextView = currentView.findViewById(R.id.txt_product_details_price)
                val AddToCart: TextView = currentView.findViewById(R.id.txt_add_to_cart)
                val layoutAddItem: ConstraintLayout = currentView.findViewById(R.id.layout_add_to_cart_from_product_details)
                val btnAddProduct: ImageButton = currentView.findViewById(R.id.btnPlus)
                val tvProductDetailCount: TextView = currentView.findViewById(R.id.txtProduct_count)
                val btnSubProduct: ImageButton = currentView.findViewById(R.id.btnMin)
                val recyclerViewParameters: RecyclerView = currentView.findViewById(R.id.recyclerView_parameters)
                val recyclerViewCustomerReview: RecyclerView = currentView.findViewById(R.id.recycleView_customer_review)


                productDetailsName.text = product.product_name
                ratingBar.rating = product.average_rating.toFloat()
                productDesc.text = product.description
                ProductPrice.text = product.price

                val adapter = ProductsDetailsAdapter(currentView, product.images)
                imgProductViewPage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                imgProductViewPage.adapter = adapter

                var cart = cartDao.getCartProductByProductId(product.product_id.toInt())
                if (cart != null && cart!!.count > 0) {
                    AddToCart.visibility = View.GONE
                    layoutAddItem.visibility = View.VISIBLE
                    tvProductDetailCount.text = cart!!.count.toString()

                }
                btnSubProduct.setOnClickListener {
                    if (cart != null) {
                        if (cart!!.count < 2) {
                            cart!!.cartId?.let { it1 ->
                                if (cartDao.deleteCartProduct(it1)) {
                                    Log.e(
                                        "Delete",
                                        "Delete cart id = ${cart!!.cartId} name = ${cart!!.productName} success"
                                    )
                                }

                            }
                            AddToCart.visibility = View.VISIBLE
                            layoutAddItem.visibility = View.GONE
                        } else {
                            cart!!.count = cart!!.count - 1
                            cartDao.updateCartProduct(cart!!)
                            tvProductDetailCount.text = cart!!.count.toString()
                        }
                    }
                }
                btnAddProduct.setOnClickListener {
                    if (cart != null) {
                        cart!!.count = cart!!.count + 1
                        cartDao.updateCartProduct(cart!!)
                        tvProductDetailCount.text = cart!!.count.toString()
                    }
                }
                AddToCart.setOnClickListener {
                    AddToCart.visibility = View.GONE
                    layoutAddItem.visibility = View.VISIBLE
                    val cartItem = CartProduct(
                        null,
                        product.product_name,
                        product.product_id,
                        product.description,
                        product.price.toDouble(),
                        product.category_id,
                        product.sub_category_id,
                        product.product_image_url,
                        1
                    )
                    cartItem.cartId = cartDao.addCart(cartItem)
                    if (cartItem.cartId != null && cartItem.cartId!! > 0) {
                        tvProductDetailCount.text = "1"
                        cart = cartDao.getCartProductByProductId(product.product_id.toInt())
                    }
                }
                parametersList = product.specifications
                parametersAdapter = ParametersAdapter(parametersList)
                recyclerViewParameters.layoutManager =
                    LinearLayoutManager(currentView.context)
                recyclerViewParameters.adapter = parametersAdapter

                //TODO reviewList

            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }
}

