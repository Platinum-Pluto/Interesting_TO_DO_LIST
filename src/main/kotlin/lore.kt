import androidx.compose.runtime.Composable
import kotlin.random.Random

fun clickEvent(): Int {
    val count = db_count()+1
    db_UpdateCount(count)
    return count
}

fun buylore(): Int {
    var count = db_count()
    if(count >= 10){
        count--
        db_UpdateCount(count)
        return count
    }
    else{
        return count
    }

}

fun unlockLore(): Stories?{
    buylore()
    //access db stories
    val stories = db_stories()
    val rand = Random.nextInt(stories.size)
    val story1 = stories[rand]
    val id = story1.id
    db_query("DELETE FROM stories WHERE id = '$id'")
    return story1
}
