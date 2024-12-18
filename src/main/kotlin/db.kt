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

// the model class
data class User(val id: String, val description: String, val created: String)
data class Stories(val id: String, val story: String)

val jdbcUrl = "jdbc:mysql://localhost:3306/kotlin_db"
var users = mutableListOf<User>()
var story = mutableListOf<Stories>()
val username = "root"
val password = ""




fun db_insert(id: String, description: String, created: String){
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("INSERT INTO tasks (id, description, created) VALUES ('$id', '$description', '$created')")
    resultSet.close()
    statement.close()
    connection.close()
}


fun db_delete(id: String){
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("DELETE FROM tasks WHERE id = '$id'")
    resultSet.close()
    statement.close()
    connection.close()
}

fun db_update(id: String, description: String, created: String){
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("UPDATE tasks SET description = '$description', created = '$created' WHERE id = '$id'")
    resultSet.close()
    statement.close()
    connection.close()
}


fun db_read(): List<User>{
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM tasks")
    while (resultSet.next()) {
        users.add(User(
            id = resultSet.getString("id"),
            description = resultSet.getString("description"),
            created = resultSet.getString("created")
        ))

    }
    resultSet.close()
    statement.close()
    connection.close()
    return users
}

fun db_count(): Int{
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    println("1")
    println("Connected to the database successfully!")
    println("1")
    // Example query execution
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT count FROM user")
    val count = resultSet.getInt("count")
    resultSet.close()
    statement.close()
    connection.close()
    return count
}


fun db_UpdateCount(count: Int){
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("UPDATE user SET count = '$count'")
    resultSet.close()
    statement.close()
    connection.close()
}

fun db_query(query: String){
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(query)
    resultSet.close()
    statement.close()
    connection.close()
}

fun db_stories(): List<Stories>{
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM tasks")
    while (resultSet.next()) {
        story.add(Stories(
            id = resultSet.getString("id"),
            story = resultSet.getString("story")
        ))
    }
    resultSet.close()
    statement.close()
    connection.close()
    return story
}
