
package pixelmaster.core.api.framework;


/**
 * <p>
 * This interface represents module of the application.
 * Implementing class must be named <code>Module</code> and must be
 * placed in package {@link pixelmaster.modules}<code>.[module_name].framework</code>.
 * Implementing class must be conrete (not abstract) and must have
 * public constructor <code>Module()</code> with no arguments. It will
 * be used to create instance of the module.
 * </p>
 */
public interface IModule
{
	/**
	 * Initialization routine that is called during application start-up.
	 * Core provides instances of <code>IControlElementRegistry</code>
	 * and <code>IWorkbench</code> interfaces. Note that modules must not
	 * use the registry object after this method has finished execution.
	 *
	 * @param registry Allows module to add control elements in main window.
	 * 		Any calls of methods of this object after this method has finished execution result in undefined behaviour.
	 * @param workbench Provides interface to workbench.
	 */
	public void init(IControlElementRegistry registry, IWorkbench workbench);
}
