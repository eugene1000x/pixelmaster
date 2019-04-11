
package pixelmaster.core.api.domain;


/**
 * @see <a href="http://www.ibm.com/developerworks/java/library/j-jtp06243.html">
 * 		Java theory and practice: Whose object is it, anyway?</a>
 */
public final class ObjectOwnership
{
	/**
	 * Indicates that owner of the passed object is the calling method.
	 */
	public static final int OWNERSHIP_CALLER = 0;

	/**
	 * Indicates that owner of the passed object is the called method.
	 * This means that typically, a defensive copy should be created if caller
	 * is about to modify the object.
	 */
	public static final int OWNERSHIP_CALLEE = 1;
}
