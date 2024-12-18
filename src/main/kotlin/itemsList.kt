import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

data class itemsList(
    var id : Int,
    var description : String,
    var created : LocalDateTime = LocalDateTime.now()
)

fun retrieveList() : List<itemsList>{
    return listOf<itemsList>(
        itemsList(1, "Damn")
    );
}

