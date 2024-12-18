import androidx.compose.runtime.Composable
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.text.style.TextAlign
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import javax.swing.plaf.ProgressBarUI


@Composable

fun listPage(todoModel: TodoModel){
    var progress by remember { mutableStateOf(0f) }
    //val list = retrieveList()
   // val list by todoModel.todoList.collectAsState()
   // val isLoading by todoModel.isLoading
    //var list by remember { mutableStateOf(retrieveList()) }
    var i = 0
    var list = remember { mutableStateListOf<itemsList>().apply { addAll(retrieveList()) } }


    var userInput by remember { mutableStateOf("") }

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
                text = "No items yet",
                fontSize = 16.sp,
                textAlign = TextAlign.Start, // Align the text to the start
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
                if (i == 0 && userInput.isNotBlank()) {
                    check(userInput)
                    i++
                }
                if (userInput.isNotBlank()) {
                    list.add(
                        itemsList(
                            id = UUID.randomUUID().toString(),
                            description = userInput,
                            created = LocalDateTime.now()
                        )
                    )
                }
            }) {
                Text(text = "Add")
            }
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
                itemsIndexed(list) { index: Int, item: itemsList ->
                    listItems(item = item, onDelete = {
                        // todoModel.deleteTodo(item)
                        //clickEvent()
                        list.remove(item)
                        progress += 0.3f
                    })
                }
            }
        }
    }



}


@Composable
fun listItems(item: itemsList, onDelete : ()-> Unit) {
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
                text = DateTimeFormatter
                    .ofPattern("hh:mm a, dd/MM")
                    .format(item.created),
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
                imageVector = Icons.Filled.Star,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }



}


