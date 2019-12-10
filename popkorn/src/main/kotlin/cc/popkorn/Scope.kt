package cc.popkorn

/**
 * Enum defining available Scopes in PopKorn
 * BY_APP -> Instance will be created only once, for hence this instance will live forever
 * BY_USE -> Instance will be created if no one is using it, meaning that instances will live as long as others are using it
 * BY_NEW -> Instance will be created every time is needed, so won't live at all
 *
 * @author Pau Corbella
 * @since 1.0.0
 */
enum class Scope {
    BY_APP,
    BY_USE,
    BY_NEW
}