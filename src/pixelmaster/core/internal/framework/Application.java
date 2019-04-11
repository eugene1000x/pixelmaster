
package pixelmaster.core.internal.framework;


import pixelmaster.core.api.framework.*;

import java.util.*;


public final class Application
{
	private static Workbench workbench = new Workbench();
	private static ControlElementRegistry controlElementRegistry = new ControlElementRegistry();
	
	
	public static void main(String[] args)
	{
		Application.workbench.loadMainGui();
		Application.initCoreAndModules();
		Application.workbench.initControlElements(
			Application.controlElementRegistry.getMenubar(),
			Application.controlElementRegistry.getToolbar()
		);
	}
	
	private static void initCoreAndModules()
	{
		new Core().init(Application.controlElementRegistry, Application.workbench);
		
		Set <IModule> modules = Application.getModules();
		
		for (IModule module: modules)
		{
			module.init(Application.controlElementRegistry, Application.workbench);
		}
	}
	
	/**
	 * Modules are returned in no particular order.
	 */
	private static Set <IModule> getModules()
	{
		Set <IModule> modules = new HashSet <IModule> ();
		
		modules.add(new pixelmaster.modules.binarization.framework.Module());
		modules.add(new pixelmaster.modules.filters.framework.Module());
		modules.add(new pixelmaster.modules.spherical_wave.framework.Module());
		modules.add(new pixelmaster.modules.transform.framework.Module());
		
		return modules;
	}
}
