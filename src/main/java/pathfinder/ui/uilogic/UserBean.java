package pathfinder.ui.uilogic;

import pathfinder.model.nodes.User;

/**
 * A felhasználó adatainak módosításáért felelős üzleti logika interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public interface UserBean {
    /**
     * A bejelentkezett felhasználó lekérése.
     *
     * @return Visszaadja a bejelentkezett felhasználót
     */
    User getLoggedInUser();

    /**
     * A bejelentkezett felhasználó adatainak módosítása.
     *
     * @param loggedInUser A bejelentkezett felhasználó módosított adatai
     * @param confirmPassword
     */
    void modifyUser(User loggedInUser, String confirmPassword);
}
