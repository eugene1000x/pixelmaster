
package eugenejonas.pixelmaster.core.api.domain;


/**
 * App config.
 */
public final class Config
{
	/**
	 * This option can be used to disable time-consuming assertion checks and
	 * leave only fast ones (by putting an "if" condition around assertion check).
	 * Note that assertions are only checked if run with parameter -ea (or -enableassertions).
	 * When run with -ea and ARE_FULL_ASSERTION_CHECKS_ENABLED == false, only fast assertion
	 * checks will be performed.
	 */
	public static final boolean ARE_FULL_ASSERTION_CHECKS_ENABLED = false;
}
