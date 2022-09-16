#About ShoppingApp
It's a basic function shoppingApp which using Api under MVP architecture with Kotlin language by Android Studio Software.

#This App contain below function:
1. Add/Sub product cout.
   
   |—— Set button clickListener on ProducAadapter and check current count number and choose update or delete method by CartDao
 
2. Put product into shopping cart.  
   |—— At CartAdapter get product by productId throw CartDao when cartFragment created  will get all cart product by arrayList   
   
3. Create and save Address by user.  
   
   |—— When customer choose AddAddress Button, show dialogView, at dialogBuilder set Save button ClickListener to save EditText value
   
4. Create new order by shopping cart check out systems.
   
   |—— Check out Systems include 4 diffrent function at below:
       
       |——Check out cart product list
          
          |—— Using cartDao and recyclerView to show the list of cart item and caiculator total amount and customer able to add or delete products.
       
       |——Check out Delivery option screen
          
          |—— Show adress option list and offer add new address button and and pass this data to Summary screen
       
       |——Check out Payment option screen
         
         |—— Show payment method by radiouGroup Function and pass this data to Summary screen
      
      |—— Check out Summary sceen
         
         |—— Show order details and using Confirm & Place Order button to send this order to Order Fragment
       
5. Searching product by search function.
   
   |—— Using product presenter to get product information then replace fragment to currentView by setReult. 
   
6. Register/Login/Logout Function
   
   |—— Register: check customer type in information valid or not then using presenter to apply user information by Api at VolleyHandler and getting calBack by                          callBackOperational interface, if success, intent to Login Activity.
  
  |—— Login:    submit user type in email and password throw Volleyhandle and get callBack by callBackOperational interface, if success, intent to MainActivity and                    passing user data.
   
   |—— Logout:   logout function is one navigation drawer menu option allow user to click it and go back to Login Activity.
   
   //Todo: Support Chat

#Build with

- [Kotlin](https://kotlinlang.org/) 

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) 

- [GSON](https://github.com/google/gson)

- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) 

- [Glide](https://github.com/bumptech/glide) 

- [Android Navigation Components] (https://developer.android.com/guide/navigation/navigation-getting-started)

#Project Structure 

com.example.shoppingappprject    

|—— model

    |—— local
    
    |—— remote
    
       |—— data
          
       |—— api
       
|—— presenter

|—— ui


#These are the preview of this App：

![screenshot_login](https://user-images.githubusercontent.com/112971217/190646617-ec4f17a7-b669-426e-b397-45e58e37f40f.png)
![screenshot_register](https://user-images.githubusercontent.com/112971217/190646620-141c3999-f1d6-442a-a7d4-61a9dcde8123.png)
![screenshot_homepage](https://user-images.githubusercontent.com/112971217/190646621-c472a843-169f-47df-90ae-0275ad393947.png)
![screenshot_product](https://user-images.githubusercontent.com/112971217/190646623-bcb5da6f-db16-4f72-a951-a66793500bf1.png)
![screen_product_details](https://user-images.githubusercontent.com/112971217/190646627-be1d2f57-c139-4c08-9be0-ec0346a7beda.png)
![screenshot_cartlist](https://user-images.githubusercontent.com/112971217/190646628-d5e9f5aa-19e1-4492-8dca-7b71be10a1e7.png)
![screenshot_add_address](https://user-images.githubusercontent.com/112971217/190646629-6dc84ba9-d78e-4f5e-90c5-46c9c7157ecb.png)
![screenshot_orderlist](https://user-images.githubusercontent.com/112971217/190646653-5baf676f-d30d-497d-b335-ba24ee955b8d.png)
![screenshot_orderdetails](https://user-images.githubusercontent.com/112971217/190646656-271b283f-6cdd-47bd-8c42-5cbbc5b3c54e.png)
![screenshot_payment_option](https://user-images.githubusercontent.com/112971217/190646657-4b0de220-4200-4995-b8df-398091437809.png)
![screenshot_order_confirm](https://user-images.githubusercontent.com/112971217/190646659-fdb6489f-a9c6-4387-9003-684cfa66435f.png)
