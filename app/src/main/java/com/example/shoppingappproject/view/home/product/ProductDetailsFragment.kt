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
import com.example.shoppingappproject.model.remote.data.productdetails.Specification
import com.example.shoppingappproject.model.remote.data.productdetails.ProductDetailResponse
import com.example.shoppingappproject.R
import com.example.shoppingappproject.model.local.CartDao
import com.example.shoppingappproject.model.local.CartProduct
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.productdetails.Image
import com.example.shoppingappproject.model.remote.data.productdetails.Review
import com.example.shoppingappproject.presenter.productdetail.ProductDetailsMVP
import com.example.shoppingappproject.presenter.productdetail.ProductDetailsPresenter
import com.example.shoppingappproject.view.home.ParametersAdapter
import com.example.shoppingappproject.view.home.ReviewAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProductDetailsFragment : Fragment(), ProductDetailsMVP.ProductDetailsView {

    private lateinit var presenter: ProductDetailsPresenter
    lateinit var currentView:View
    lateinit var productId:String
    private lateinit var cartDao:CartDao
    lateinit var specificationList:ArrayList<Specification>
    lateinit var reviewList:ArrayList<Review>
    lateinit var parametersAdapter: ParametersAdapter
    lateinit var reviewAdapter:ReviewAdapter
    lateinit var imageList:ArrayList<Image>
    lateinit var Imageadapter : ProductsDetailsAdapter
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
            currentView.let{
                Log.e("product", "$product")
                val productDetailsName: TextView = currentView.findViewById(R.id.txt_product_details_name)
                val ratingBar: RatingBar = currentView.findViewById(R.id.ratingBar_product_details)
                val productDesc: TextView = currentView.findViewById(R.id.txt_product_details_desc)
                val productPrice: TextView = currentView.findViewById(R.id.txt_product_details_price)
                val addToCart: TextView = currentView.findViewById(R.id.txt_add_to_cart)
                val layoutAddItem: ConstraintLayout = currentView.findViewById(R.id.layout_add_to_cart_from_product_details)
                val btnAddProduct: ImageButton = currentView.findViewById(R.id.btnPlus)
                val tvProductDetailCount: TextView = currentView.findViewById(R.id.txtProduct_count)
                val btnSubProduct: ImageButton = currentView.findViewById(R.id.btnMin)
                val recyclerViewParameters: RecyclerView = currentView.findViewById(R.id.recyclerView_parameters)
                val recyclerViewCustomerReview: RecyclerView = currentView.findViewById(R.id.recycleView_customer_review)
                val recyclerViewImage:RecyclerView = currentView.findViewById(R.id.recyclerView_product_details_image)

                productDetailsName.text = product.product_name
                ratingBar.rating = product.average_rating.toFloat()
                productDesc.text = product.description
                productPrice.text = product.price


                var cart = cartDao.getCartProductByProductId(product.product_id.toInt())
                if (cart != null && cart.count > 0) {
                    addToCart.visibility = View.GONE
                    layoutAddItem.visibility = View.VISIBLE
                    tvProductDetailCount.text = cart.count.toString()

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
                            addToCart.visibility = View.VISIBLE
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
                addToCart.setOnClickListener {
                    addToCart.visibility = View.GONE
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
                specificationList = product.specifications
                parametersAdapter = ParametersAdapter(specificationList)
                recyclerViewParameters.layoutManager = LinearLayoutManager(currentView.context)
                recyclerViewParameters.adapter = parametersAdapter

                reviewList = product.reviews
                reviewAdapter = ReviewAdapter(reviewList)
                recyclerViewCustomerReview.layoutManager = LinearLayoutManager(currentView.context)
                recyclerViewCustomerReview.adapter = reviewAdapter

                imageList = product.images
                Imageadapter = ProductsDetailsAdapter(currentView.context,imageList)
                recyclerViewImage.layoutManager = LinearLayoutManager(currentView.context,RecyclerView.HORIZONTAL, false)
                recyclerViewImage.adapter = Imageadapter



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

