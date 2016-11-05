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
	ROLE_USER,
	
	/**
	 * Adminisztrátori jogosultság
	 */
	ROLE_ADMIN;

    public String getName() {
        return this.name();
    }
}
