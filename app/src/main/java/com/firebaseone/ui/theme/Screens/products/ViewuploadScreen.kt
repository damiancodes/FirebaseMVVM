package com.firebaseone.ui.theme.Screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.firebaseone.data.productviewmodel
import com.firebaseone.model.Upload
import com.firebaseone.naavigation.ROUTE_UPDATE_PRODUCT

@Composable
fun ViewUploadsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val productRepository = productviewmodel(navController, context)

    val emptyUploadState = remember { mutableStateOf(Upload("", "", "", "", "")) }
    val emptyUploadsListState = remember { mutableStateListOf<Upload>() }

    val uploads = productRepository.viewUploads(emptyUploadState, emptyUploadsListState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Uploads",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uploads) { upload ->
                UploadItem(
                    name = upload.name,
                    quantity = upload.quantity,
                    price = upload.price,
                    imageUrl = upload.imageUrl,
                    id = upload.id,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

@Composable
fun UploadItem(
    name: String,
    quantity: String,
    price: String,
    imageUrl: String,
    id: String,
    navController: NavHostController,
    productRepository: productviewmodel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "Qty: $quantity", color = Color.Gray)
            Text(text = "Price: $price", color = Color.Gray)

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { productRepository.deleteProduct(id) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = { navController.navigate("$ROUTE_UPDATE_PRODUCT/$id") },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Update")
                }
            }
        }
    }
}
