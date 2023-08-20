@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage






data class Element(
    val name1: String,
    val name2: String,
    val imageUrl: String
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ElementScreen()
            }
        }
    }
}



@Composable
fun ElementCard(element: Element) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
                CoilImage(
                data = element.imageUrl,
                contentDescription = "Element Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Número de receta: ${element.name1}")
            Text(text = "Nombre: ${element.name2}")

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ElementScreen() {
    var name1 by remember { mutableStateOf("") }
    var name2 by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var elements by remember { mutableStateOf(listOf<Element>()) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val itemList = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)


    ) {
        TextField(
            value = name1,
            onValueChange = { name1 = it },
            label = { Text("Número de receta") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = name2,
            onValueChange = { name2 = it },
            label = { Text("Nombre de la receta") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL de la imagen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                if (name1.isNotBlank() && name2.isNotBlank() && imageUrl.isNotBlank()) {
                    elements = elements + Element(name1, name2, imageUrl)
                    name1 = ""
                    name2 = ""
                    imageUrl = ""
                    keyboardController?.hide()
                    itemList.add(name1)
                    itemList.add(name2)
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
        ) {
            Text(text = "Agregar elemento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(elements) { element ->
                ElementCard(element)
            }
        }

    }
}



