package cc.popkorn.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cc.popkorn.InjectorController
import cc.popkorn.ParametersFactory
import cc.popkorn.popKorn

@Composable
inline fun <reified T : Any> rememberInject(environment: String? = null): T =
    remember { popKorn().inject(T::class, environment) }

@Composable
inline fun <reified T : Any> rememberCreate(
    environment: String? = null,
    noinline parameters: (ParametersFactory.Builder.() -> Unit)? = null,
): T {
    val params = parameters?.let { ParametersFactory.Builder().also(it).build() }
    return remember {
        popKorn().create(
            clazz = T::class,
            environment = environment,
            parametersFactory = params,
        )
    }
}

@Composable
val popKorn: InjectorController
    get() = remember { popKorn() }
