package cc.popkorn.samples.multiModule.internal

import cc.popkorn.annotations.Injectable
import cc.popkorn.samples.multiModule.open.GetUser
import cc.popkorn.samples.multiModule.open.User

@Injectable
internal class GetUserImpl : GetUser {
    override operator fun invoke(): User = User("Javi")
}
