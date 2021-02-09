package cc.popkorn.samples.multiModule.presentation

import cc.popkorn.annotations.Injectable
import cc.popkorn.samples.multiModule.open.GetUser
import cc.popkorn.samples.multiModule.open.User

@Injectable
class UserViewModel(private val getUser: GetUser) {

    fun loadUser(): User = getUser()
}
