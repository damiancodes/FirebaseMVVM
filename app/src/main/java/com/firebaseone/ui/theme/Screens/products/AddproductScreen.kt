package com.firebaseone.ui.theme.Screens.products

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.firebaseone.data.productviewmodel
import com.firebaseone.naavigation.ROUTE_VIEW_PRODUCT


@Composable
fun AddProductsScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var context = LocalContext.current
        Text(
            text = "Add product",
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            color = Color.Red,
            modifier = Modifier.padding(20.dp),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        var productName by remember { mutableStateOf(TextFieldValue("")) }
        var productQuantity by remember { mutableStateOf(TextFieldValue("")) }
        var productPrice by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text(text = "Product name *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productQuantity,
            onValueChange = { productQuantity = it },
            label = { Text(text = "Product quantity *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text(text = "Product price *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            //-----------WRITE THE SAVE LOGIC HERE---------------//
            var productRepository = productviewmodel(navController,context)
            productRepository.saveProduct(productName.text.trim(),productQuantity.text.trim(),
                productPrice.text)
            navController.navigate(ROUTE_VIEW_PRODUCT)


        }) {
            Text(text = "Save")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //---------------------IMAGE PICKER START-----------------------------------//

        ImagePicker(Modifier,context, navController, productName.text.trim(), productQuantity.text.trim(), productPrice.text.trim())

        //---------------------IMAGE PICKER END-----------------------------------//

    }
}

@Composable
fun ImagePicker(modifier: Modifier = Modifier, context: Context, navController: NavHostController, name:String, quantity:String, price:String) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = modifier,) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.
            getBitmap(context.contentResolver,imageUri)
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Selected image")
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), horizontalAlignment = Alignment.CenterHorizontally,) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
            ) {
                Text(
                    text = "Select Image"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                var productRepository = productviewmodel(navController,context)
                productRepository.saveProductWithImage(name, quantity, price,imageUri!!)


            }) {
                Text(text = "Upload")
            }
        }
    }
}


@Preview
@Composable
fun Addpr() {
    AddProductsScreen(rememberNavController())

}



//
//package com.firebaseone.ui.theme.Screens.products
//
//import android.content.Context
//import android.content.res.Configuration
//import android.net.Uri
//import android.provider.MediaStore
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.input.OffsetMapping
//import androidx.compose.ui.text.AnnotatedString
//import coil.compose.rememberAsyncImagePainter
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.firebaseone.data.productviewmodel
//import com.firebaseone.naavigation.ROUTE_VIEW_PRODUCT
//
//@Composable
//fun AddProductsScreen(navController: NavHostController) {
//    // Modern color scheme
//    val primaryColor = Color(0xFF6200EE)  // Purple
//    val secondaryColor = Color(0xFF03DAC6)  // Teal
//    val backgroundColor = Color(0xFFF5F5F5)  // Light gray
//    val textColor = Color(0xFF333333)  // Dark gray
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Header with modern typography
//        Text(
//            text = "Add New Product",
//            fontSize = 24.sp,
//            fontFamily = FontFamily.SansSerif,
//            color = primaryColor,
//            modifier = Modifier.padding(vertical = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//
//        var productName by remember { mutableStateOf(TextFieldValue("")) }
//        var productQuantity by remember { mutableStateOf(TextFieldValue("")) }
//        var productPrice by remember { mutableStateOf(TextFieldValue("")) }
//
//        // Form fields in a card
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                OutlinedTextField(
//                    value = productName,
//                    onValueChange = { productName = it },
//                    label = { Text("Product Name", color = textColor) },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = primaryColor,
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    OutlinedTextField(
//                        value = productQuantity,
//                        onValueChange = { productQuantity = it },
//                        label = { Text("Quantity", color = textColor) },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.weight(1f),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = primaryColor,
//                            unfocusedBorderColor = Color.Gray
//                        )
//                    )
//
//                    OutlinedTextField(
//                        value = productPrice,
//                        onValueChange = { productPrice = it },
//                        label = { Text("Price", color = textColor) },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.weight(1f),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = primaryColor,
//                            unfocusedBorderColor = Color.Gray
//                        )
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Image Picker Section
//        ImagePicker(
//            Modifier.fillMaxWidth(),
//            LocalContext.current,
//            navController,
//            productName.text.trim(),
//            productQuantity.text.trim(),
//            productPrice.text.trim()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Save button with modern styling
//        Button(
//            onClick = {
//                val productRepository = productviewmodel(navController, LocalContext.current)
//                productRepository.saveProduct(
//                    productName.text.trim(),
//                    productQuantity.text.trim(),
//                    productPrice.text.trim()
//                )
//                navController.navigate(ROUTE_VIEW_PRODUCT)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = primaryColor,
//                contentColor = Color.White
//            )
//        ) {
//            Text("Save Product", fontWeight = FontWeight.Medium)
//        }
//    }
//}
//
//@Composable
//fun ImagePicker(
//    modifier: Modifier = Modifier,
//    context: Context,
//    navController: NavHostController,
//    name: String,
//    quantity: String,
//    price: String
//) {
//    var hasImage by remember { mutableStateOf(false) }
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    val primaryColor = Color(0xFF6200EE)
//
//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = { uri ->
//            hasImage = uri != null
//            imageUri = uri
//        }
//    )
//
//    Card(
//        modifier = modifier,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (hasImage && imageUri != null) {
//                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
//                Image(
//                    bitmap = bitmap.asImageBitmap(),
//                    contentDescription = "Selected image",
//                    modifier = Modifier
//                        .size(150.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Button(
//                    onClick = { imagePicker.launch("image/*") },
//                    modifier = Modifier.weight(1f),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.LightGray,
//                        contentColor = primaryColor
//                    )
//                ) {
//                    Text("Select Image")
//                }
//
//                Button(
//                    onClick = {
//                        if (imageUri != null) {
//                            val productRepository = productviewmodel(navController, context)
//                            productRepository.saveProductWithImage(name, quantity, price, imageUri!!)
//                        }
//                    },
//                    modifier = Modifier.weight(1f),
//                    enabled = hasImage && imageUri != null,
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = primaryColor,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text("Upload Image")
//                }
//            }
//        }
//    }
//}