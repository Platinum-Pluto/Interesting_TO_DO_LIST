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
import java.util.Base64


var i = 1

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


fun getGeminiResponse(apiKey: String, prompt: String): String {
    val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey"
    val client = OkHttpClient()

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

    val request = Request.Builder()
        .url(endpoint)
        .post(requestBody)
        .build()

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




fun check(whoami : String): String{
    val md = MessageDigest.getInstance("SHA-256")
    val input = whoami.toByteArray(Charsets.UTF_8)
    val bytes = md.digest(input)
    val itisi = Base64.getUrlEncoder().encodeToString(bytes)
    if (itisi == "2oWf9QSrK0twWoi5vEw5dkngGBb6xInhY58-5hTg9Lw=") return "HMM" else return ""


}


fun genai(prompt: String, apikey: String): String {
    val response = getGeminiResponse(apikey, prompt)
    return response
}




fun llm(description: String, checked: String): String {
    val dotenv = dotenv()
    val apikey = dotenv["APIKEY"]

    var prompt =  "Based on the description write a Achievement name based off of it for example The Carpenter from Germany and so on just like when game achievements when they unclock /n Here is the description: $description "
    var prompt1 = if(description.isNotEmpty() && i == 0) "Reply to the user as if the user is a princess and talk to the user in a gentle manner and answer to the User Query also your answer should not be longer than 3 sentenses. /n User Query: $description" else "$description is the user name and talk to her as if the user is a princess and ask the user whats their question or what you can help with. Write your reply within 3 sentenses max"
    var finalprompt = if(checked.isNotEmpty()) prompt1 else prompt
    i = 0
    println(finalprompt)
    val result = genai(finalprompt, apikey)
    println(result)
    return result
}



fun existence(count: Int, taskDescription: String, alright: String): String{

    return if(alright.equals("Hmm", ignoreCase = true) || count%10 == 0){
        println("OKAY")
        llm(taskDescription, alright)
    }
    else{
        "Congratulations!!!"
    }
}






