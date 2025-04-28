package com.firebaseone.ui.theme.Screens.products

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.firebaseone.data.productviewmodel
import com.firebaseone.model.Product
import com.firebaseone.naavigation.ROUTE_UPDATE_PRODUCT

@Composable
fun ViewProductsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val productRepository = productviewmodel(navController, context)
    val emptyProductState = remember { mutableStateOf(Product("", "", "", "")) }
    val emptyProductsListState = remember { mutableStateListOf<Product>() }

    val products = productRepository.viewProducts(emptyProductState, emptyProductsListState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "All Products",
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.primary
        )
    }


        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(
                    name = product.name,
                    quantity = product.quantity,
                    price = product.price,
                    id = product.id,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }


@Composable
fun ProductItem(
    name: String,
    quantity: String,
    price: String,
    id: String,
    navController: NavHostController,
    productRepository: productviewmodel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Quantity: $quantity", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $price", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(
                    onClick = { productRepository.deleteProduct(id) },
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = "Delete", color = MaterialTheme.colorScheme.onError)
                }
                ElevatedButton(
                    onClick = { navController.navigate(ROUTE_UPDATE_PRODUCT + "/$id") }
                ) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewProductsPreview() {
    ViewProductsScreen(navController = rememberNavController())
}
