import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import io.github.cdimascio.dotenv.dotenv
import java.security.MessageDigest
import java.util.*

import java.sql.DriverManager



fun main() = application {

    val todomodel = TodoModel()
    Window(
        onCloseRequest = ::exitApplication,
        title = "TODO LIST"
    ){
        MaterialTheme{
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                listPage(todomodel)
            }
        }
    }


    //  Window(onCloseRequest = ::exitApplication) {
    //   App()
    //  }
}
