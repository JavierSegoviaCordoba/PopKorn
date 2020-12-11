package cc.popkorn.examples.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import cc.popkorn.annotations.Injectable
import cc.popkorn.compose.android.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content(viewModel: UserViewModel = viewModel()) {
    val userName = viewModel.getUserName()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("PopKorn example")
        })
    }) {
        User(userName)
    }
}

@Composable
fun User(userName: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = userName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        )
    }
}

@Injectable
class UserViewModel : ViewModel() {
    fun getUserName() = "Pau"
}
