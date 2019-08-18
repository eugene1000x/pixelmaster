
package eugenejonas.pixelmaster.core.internal.framework;


import eugenejonas.pixelmaster.core.api.framework.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;


/**
 * This class is responsible for registering modules' and core's control
 * elements and callbacks, creating main window's menubar and toolbar.
 */
public final class ControlElementRegistry implements IControlElementRegistry
{
	private static class MenuStructure implements Iterable <List <IMenuItemAction> >
	{
		private List <List <IMenuItemAction> > menus = new ArrayList <List <IMenuItemAction> > ();
		
		
		private MenuStructure()
		{
			//nothing
		}
		
		private void addMenuItem(IMenuItemAction action)
		{
			Iterator <List <IMenuItemAction> > it = this.menus.iterator();
			
			List <IMenuItemAction> menu = null;
			boolean doCreateNewMenu = true;
			
			// find where to insert the menu item - there must be no duplicate menu titles
			while (it.hasNext())
			{
				menu = it.next();
				String menuTitle = menu.get(0).getMenuTitle();
				
				if (menuTitle.equals(action.getMenuTitle()))
				{
					doCreateNewMenu = false;
					break;
				}
			}
			

			if (doCreateNewMenu == true)		// there is no menu with such title yet - create new menu
			{
				menu = new ArrayList <IMenuItemAction> ();
				menu.add(action);
				this.menus.add(menu);
			}
			else		// found a menu with this title
			{
				menu.add(action);
			}
		}
		
		@Override
		public Iterator <List <IMenuItemAction> > iterator()
		{
			return this.menus.iterator();
		}
	}
	
	
	/**
	 * Menu items of core and all modules.
	 */
	private MenuStructure menuStructure = new MenuStructure();
	
	/**
	 * Toolbar buttons of core and all modules.
	 */
	private List <IToolbarButtonAction> toolbarButtons = new ArrayList <IToolbarButtonAction> ();
	
	
	@Override
	public void registerMenuItem(IMenuItemAction callback)
	{
		this.menuStructure.addMenuItem(callback);
	}
	
	@Override
	public void registerToolbarButton(IToolbarButtonAction callback)
	{
		this.toolbarButtons.add(callback);
	}
	
	JToolBar getToolbar()
	{
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		for (IToolbarButtonAction action: this.toolbarButtons)
		{
			//JButton button = new JButton(new ToolbarButtonActionWrapper(action));
			JButton button = toolbar.add(new ToolbarButtonActionWrapper(action));
			button.setPreferredSize(new Dimension(32, 32));
			button.setSize(new Dimension(32, 32));
		}
		
		return toolbar;
	}
	
	JMenuBar getMenubar()
	{
		JMenuBar menubar = new JMenuBar();
		
		for (List <IMenuItemAction> menuItemList: this.menuStructure)
		{
			assert !menuItemList.isEmpty();
			
			JMenu menu = new JMenu(menuItemList.get(0).getMenuTitle());
			
			for (IMenuItemAction action: menuItemList)
			{
				JMenuItem menuItem = new JMenuItem(action.getName());
				
				menuItem.setAction(new MenuItemActionWrapper(action));
				
				//int width = menuItem.getPreferredSize().width;
				//menuItem.setPreferredSize(new Dimension(width, 16));
				//menuItem.setSize(new Dimension(width, 16));
				
				menu.add(menuItem);
			}
			
			menubar.add(menu);
		}
		
		return menubar;
	}
}
