package pathfinder.common;

/**
 * A rendszer jogosultságainak kezelésére szolgáló enum.
 * 
 * @author Kiss László
 *
 */
public enum RoleEnum {
	
	/**
	 * Felhasználói jogosultság
	 */
	USER,
	
	/**
	 * Adminisztrátori jogosultság
	 */
	ADMINISTRATOR;

    public String getName() {
        return this.name();
    }
}
