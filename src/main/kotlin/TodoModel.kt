import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoModel {
    // Using StateFlow for list of todos
    private val _todoList = MutableStateFlow<List<itemsList>>(emptyList())
    val todoList: StateFlow<List<itemsList>> = _todoList.asStateFlow()

    // If you need a simple state, you can also use mutableStateOf
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getAllTodos(): List<itemsList> {
        return _todoList.value
    }

    fun addTodo(todo: itemsList) {
        val currentList = _todoList.value.toMutableList()
        currentList.add(todo)
        _todoList.value = currentList
    }

    fun deleteTodo(todo: itemsList) {
        val currentList = _todoList.value.toMutableList()
        currentList.remove(todo)
        _todoList.value = currentList
    }
}

