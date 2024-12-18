import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

data class itemsList(
    var id : String,
    var description : String,
    val created : LocalDateTime
)

fun retrieveList() : List<itemsList>{
    val i = LocalDateTime.now()
    return listOf<itemsList>(
        itemsList("dauduiwhuih", "Damn", i)
    );
}

