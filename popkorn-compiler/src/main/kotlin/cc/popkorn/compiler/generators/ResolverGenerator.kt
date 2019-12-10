package cc.popkorn.compiler.generators

import cc.popkorn.RESOLVER_SUFFIX
import cc.popkorn.compiler.PopKornException
import cc.popkorn.compiler.models.DefaultImplementation
import cc.popkorn.compiler.utils.isInternal
import cc.popkorn.compiler.utils.splitPackage
import cc.popkorn.core.Resolver
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Class to generate Resolver files based on interfaces of @Injectable and @InjectableProvider classes
 *
 * @author Pau Corbella
 * @since 1.0.0
 */
internal class ResolverGenerator(private val directory: File) {

    // Writes a resolver from an element
    fun write(element:TypeElement, classes:List<DefaultImplementation>) : String {
        val resolverCode = getResolverCode(element, classes)

        val file = element.getResolverFile(resolverCode)
        file.writeTo(directory)
        return "${file.packageName}.${file.name}"
    }

    private fun getResolverCode(element:TypeElement, classes:List<DefaultImplementation>) : CodeBlock {
        val environments = classes.getAvailableEnvironments()

        val codeBlock = CodeBlock.builder()
        if (environments.isEmpty()) { //If no environments are defined, return the default constructor
            codeBlock.add("return ${classes.getDefaultImplementation(element)}::class")
        } else {
            codeBlock.add("return when(environment){\n")
            environments.forEach { env ->
                codeBlock.add("    \"$env\" -> ${classes.getImplementation(element, env)}::class\n")
            }
            codeBlock.add("    else -> ${classes.getDefaultImplementation(element)}::class\n")
            codeBlock.add("}\n")
        }

        return codeBlock.build()
    }





    private fun List<DefaultImplementation>.getAvailableEnvironments(): List<String> {
        val list = map { it.environments }
            .flatten()
            .filterNotNull()
            .sorted()

        return list.takeIf { it.size == it.distinct().size } ?: throw PopKornException("Environment must be unique among ${this.map { it.element }.joinToString()}")
    }

    private fun List<DefaultImplementation>.getDefaultImplementation(element:TypeElement) : TypeElement {
        val elements = filter { it.environments.contains(null) }

        if (elements.isEmpty()) throw PopKornException("Default Injectable not found for $element: ${this.map { it.element }.joinToString()}")
        return elements.singleOrNull()?.element ?: throw PopKornException("$element has more than one class default Injectable: ${this.map { it.element }.joinToString()}")
    }


    private fun List<DefaultImplementation>.getImplementation(element:TypeElement, environment:String) : TypeElement {
        val elements = filter { it.environments.contains(environment) }

        return when (elements.size){
            0 -> getDefaultImplementation(element)
            1 -> elements.single().element
            else -> throw PopKornException("$element has more than one class for environment $environment: ${this.map { it.element }.joinToString()}")
        }
    }




    private fun TypeElement.getResolverFile(creationCode:CodeBlock) : FileSpec {
        val filePackage = "${getGenerationName()}_$RESOLVER_SUFFIX"

        val producerOf = WildcardTypeName.producerOf(asClassName())

        val create = FunSpec.builder("resolve")
            .addParameter("environment", String::class.asTypeName().copy(nullable = true))
            .addModifiers(KModifier.OVERRIDE)
            .returns(KClass::class.asClassName().parameterizedBy(producerOf))
            .addCode(creationCode)
            .build()

        val pack = filePackage.splitPackage()
        return FileSpec.builder(pack.first, pack.second)
            .addType(
                TypeSpec.classBuilder(pack.second)
                    .apply { if (isInternal()) addModifiers(KModifier.INTERNAL) }
                    .addSuperinterface(Resolver::class.asClassName().parameterizedBy(asClassName()))
                    .addFunction(create)
                    .build()
            )
            .build()
    }



    private fun TypeElement.getGenerationName() : String{
        val parent = enclosingElement?.takeIf { it.kind == ElementKind.INTERFACE || it.kind == ElementKind.CLASS }
        return if (parent==null){ //If the class its on its own
            toString()
        }else{
            "${parent}_${simpleName}"
        }
    }


}