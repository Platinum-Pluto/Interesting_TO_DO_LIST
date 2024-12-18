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




// Extract response text from Gemini JSON response
fun extractResponseText(json: JsonObject): String {
    return json.getAsJsonArray("candidates")
        ?.firstOrNull()
        ?.asJsonObject
        ?.getAsJsonObject("content")
        ?.getAsJsonArray("parts")
        ?.firstOrNull()
        ?.asJsonObject
        ?.get("text")?.asString ?: "No response"
}

// Function to send a request to Gemini AI and return the response text
fun getGeminiResponse(apiKey: String, prompt: String): String {
    val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey"
    val client = OkHttpClient()

    // JSON Request Body
    val json = """
        {
          "contents": [
            {
              "parts": [
                {"text": "$prompt"}
              ]
            }
          ]
        }
    """.trimIndent()

    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    // Build the HTTP request
    val request = Request.Builder()
        .url(endpoint)
        .post(requestBody)
        .build()

    // Execute the request and return the extracted response
    client.newCall(request).execute().use { response ->
        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val jsonResponse = JsonParser.parseString(responseBody).asJsonObject
            extractResponseText(jsonResponse)
        } else {
            "Error: ${response.code} - ${response.message}"
        }
    }
}





fun genai(prompt: String, apikey: String): String {
    val response = getGeminiResponse(apikey, prompt)
    return response
}



fun clickEvent(x: Int, y: Int): Int {
    return x+y
}

fun buylore(x: Int, y: Int): Int {
    return x-y
}

fun unlockLore(stories: MutableList<String>): String{
    val story = stories.random()
    stories.remove(story)
    return story
}

fun llm(description: String): String{

    return "HELLO"
}

fun title(count: Int, taskDescription: String): String{
    return if(count%10 == 0){
        llm(taskDescription)
    }
    else{
        "Congratulations!!!"
    }
}



class User(var progress:Float, var tasks: MutableList<String>, var points:Int, var currentTitle: String, var count: Int)



@Composable
@Preview

fun App() {

    //val apikey:String = System.getenv("APIKEY") ?: "Hello there!"
    val dotenv = dotenv()
    val apikey = dotenv["APIKEY"]
    var prompt = "Based on the description write a Achievement name based off of it for example The Carpenter from Germany and so on just like when game achievements when they unclock /n "
    //println(apikey)
    //access the stories from a database and return the list
    //val result = genai(prompt, apikey)
    // println(result)
    val stories = mutableListOf<String>()

    //add to stories from the database
    val history = mutableListOf<String>()
    var points: Int = 0
    history.add("Hello")
    history.add("Yellow")
    //val i = 100/history.size

    var text by remember { mutableStateOf(history[0]) }


    MaterialTheme {
        Button(onClick = {
            text = history[1]
        }) {
            Text(text)

        }
    }
}


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




