/**
 * Precompiled [core-module-config.gradle.kts][Core_module_config_gradle] script plugin.
 *
 * @see Core_module_config_gradle
 */
public
class CoreModuleConfigPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Core_module_config_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
