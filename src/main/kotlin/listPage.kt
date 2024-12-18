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
import androidx.compose.runtime.*
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
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime



@Composable

fun listPage(todoModel: TodoModel){
    val list = retrieveList()
   // val list by todoModel.todoList.collectAsState()
   // val isLoading by todoModel.isLoading


    var userInput by remember { mutableStateOf("") }

    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(value = userInput, onValueChange = {
                userInput = it
            })
            Button(onClick = {/*TODO*/}){
                Text(text = "Add")
            }
        }

        LazyColumn(
            content = {
                itemsIndexed(list){
                    index: Int, item: itemsList ->
                    Text(text = item.toString())
                }
            }
        )
    }

    
}


@Composable
fun listItems(item: itemsList) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
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
        IconButton(onClick = { }){
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }



}


