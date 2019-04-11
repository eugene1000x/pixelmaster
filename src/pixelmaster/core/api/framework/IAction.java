
package pixelmaster.core.api.framework;


public interface IAction
{
	/**
	 * Called when user activates control element.
	 */
	public void onActionPerformed();

	/**
	 * <p>
	 * Determines whether performing this action is possible in current context.
	 * Control elements are being enabled/disabled based on the underlying action's enabled/disabled status.
	 * </p>
	 * 
	 * <p>
	 * Enabled/disabled status of every control element will be updated:
	 * 	<ol>
	 * 		<li>when control element is first created;</li>
	 * 		<li>when image in main window changes (if user presses undo/redo, modifies or closes image or opens another image).</li>
	 * 	</ol>
	 * </p>
	 * 
	 * @return true if control element is enabled.
	 */
	public boolean isEnabled();
}
