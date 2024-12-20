import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.observeOn
import java.awt.Font
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import javax.swing.plaf.ProgressBarUI



import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay



import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun boxText(ok: String) {
    // State to manage visibility
    var isVisible by remember { mutableStateOf(false) }

    // Transition for managing animations
    val transition = updateTransition(targetState = isVisible, label = "Visibility Transition")

    // Alpha animation for fade-in and fade-out
    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500) },
        label = "Alpha Animation"
    ) { state -> if (state) 1f else 0f }

    // Horizontal scale animation for expansion and contraction
    val scaleX by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 500, easing = FastOutSlowInEasing) },
        label = "ScaleX Animation"
    ) { state -> if (state) 1f else 0f }

    // Delay before showing and for hiding
    LaunchedEffect(Unit) {
        delay(1000) // Delay before opening
        isVisible = true // Trigger the opening animation

        delay(5000) // Box is visible for 5 seconds
        isVisible = false // Trigger the closing animation
    }

    // Render the box if visible or during transition
    if (isVisible || alpha > 0f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center) // Center the box on the screen
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scaleX, // Scale horizontally
                        scaleY = 1f, // No vertical scaling
                        alpha = alpha
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                        ),
                        shape = RoundedCornerShape(12.dp) // Rounded corners for the box
                    )
                    .padding(horizontal = 32.dp, vertical = 16.dp) // Padding for the box
            ) {
                Text(
                    text = ok,
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }
    }
}





@Composable

fun listPage(todoModel: TodoModel){
    var progress by remember { mutableStateOf(0f) }
    //val list = retrieveList()
   // val list by todoModel.todoList.collectAsState()
   // val isLoading by todoModel.isLoading
    //var list by remember { mutableStateOf(retrieveList()) }
    var i = 0
    var list = remember { mutableStateListOf<User>().apply { addAll(retrieveList()) } }
    var ach by remember { mutableStateOf("") }
    var counter by remember { mutableStateOf(0) }
    var areyou by remember { mutableStateOf("") }
    var userInput by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var created by remember { mutableStateOf("") }
    var anytext by remember { mutableStateOf("sDWEDW") }
    var showBox by remember { mutableStateOf(false) } // Control boxText visibility
    var showAnimation by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(1.dp)
    ) {
        // Use Row to position Text and CircularProgressIndicator side by side
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensures proper spacing
        ) {
            Text(
                //text = "No items yet",
                text = db_title(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center, // Align the text to the start
                modifier = Modifier.weight(1f) // Let Text take up remaining horizontal space
            )

            CircularProgressIndicator(
                progress = progress,
                color = Color.Red,
                strokeWidth = 5.dp,
                modifier = Modifier // Adjust size and placement
                    .size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensures proper spacing
        ){
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (showAnimation) {
                    LaunchedEffect(Unit) {
                        delay(6500)
                        showAnimation = false
                    }
                    boxText(message)
                }
            }
        }

        // Use Row to position Text and CircularProgressIndicator side by side


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                },
                placeholder = { Text("Enter Challenge") }
            )
            Button(onClick = {
                //boxText(anytext)
                if (areyou.equals("") && userInput.isNotBlank()) {
                    areyou = check(userInput)
                    println(areyou)
                  //  i = 1
                   // println(i)
                }
                if (userInput.isNotBlank()) {
                    id = UUID.randomUUID().toString()
                    description = userInput
                    created = LocalDateTime.now().toString()

                    list.add(
                        User(
                            id = id,
                            description = description,
                            created =created
                        )
                    )
                    db_insert(id, description, created)


                }
            }) {
                Text(text = "Add")
            }
        }
        if (showBox) {
            boxText(anytext) // Show the animation box
        }

        if (list.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No items yet",
                fontSize = 16.sp
            )
        } else {
            LazyColumn {
                itemsIndexed(list) { index: Int, item: User ->
                    listItems(item = item, onDelete = {
                        //todoModel.deleteTodo(item)
                        counter = clickEvent()

                        ach = existence(counter, item.description, areyou)
                        println(ach)
                        println(areyou)
                        db_title_update(ach)
                        db_delete(item.id)
                        list.remove(item)
                        progress += 0.2f
                        if(progress >= 1f){
                            val damn = db_stories().random()
                            message = damn.story.toString()
                            showAnimation = true // Trigger animation
                            progress = 0f
                        }else{
                            showAnimation = false // Trigger animation
                        }
                    })
                }
            }
        }
    }

}


@Composable
fun listItems(item: User, onDelete : ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colors.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                // Using proper date formatting
                text = item.created,
                fontSize = 12.sp,
                // Using Compose's FontFamily instead of AWT Font
                fontFamily = FontFamily.Serif,
                color = Color.LightGray
            )

            Text(
                text = item.description,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )

        }
        IconButton(onClick = onDelete){

            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }




}


