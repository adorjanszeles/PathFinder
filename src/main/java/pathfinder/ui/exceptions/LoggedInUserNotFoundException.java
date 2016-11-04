package pathfinder.ui.exceptions;

/**
 * Kivétel arra az esetre, amikor a SecurityContextHolderben nincs felhasználó,
 * így a bejelentkezett felhasználó lekérése a szervertől nem lehetséges.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 04.
 */
public class LoggedInUserNotFoundException extends Exception {}
