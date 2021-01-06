package cc.popkorn.samples.desktop.compose

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cc.popkorn.annotations.Injectable
import cc.popkorn.compose.rememberCreate
import cc.popkorn.compose.rememberInject

fun main() {
    Window {
        MaterialTheme {
            Content()
        }
    }
}

@Composable
fun Content(greeting: Greeting = rememberInject(), greetingWithArgs: GreetingWithArgs = rememberCreate { add("Hi") }) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("PopKorn")
        })
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = greeting.hello,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = greetingWithArgs.greeting,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Injectable
class Greeting {

    val hello = "Hello"
}

@Injectable
class GreetingWithArgs(someGreeting: String) {

    val greeting = someGreeting
}
