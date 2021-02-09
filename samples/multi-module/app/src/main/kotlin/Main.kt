package cc.popkorn.samples.multiModule.app

import cc.popkorn.injecting
import cc.popkorn.samples.multiModule.presentation.UserViewModel

fun main() = App().run()

class App {
    private val userViewModel: UserViewModel by injecting()

    fun run() {
        val user = userViewModel.loadUser()
        println(user)
    }
}
