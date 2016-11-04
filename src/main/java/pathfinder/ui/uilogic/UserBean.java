package pathfinder.ui.uilogic;

import pathfinder.model.nodes.User;

import java.util.List;

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
     * @param confirmPassword A másodjára begépelt jelszó
     */
    void modifyUser(User loggedInUser, String confirmPassword);

    /**
     * Elment egy új felhasználót az adatbázisba
     *
     * @param newUser Az új felhasználó
     * @param confirmPassword A másodjára begépelt jelszó
     */
    void saveUser(User newUser, String confirmPassword);

    /**
     * Visszaadja a felhasználót az azonosítója alapján.
     *
     * @param userId A felhasználó azonosítója
     * @return A felhasználó entitás
     */
    User getUserById(Long userId);

    /**
     * Lekéri a szervertől a lehetséges tulajdonosokat.
     *
     * @return Egy user lista
     */
    List<User> getOwners();

    /**
     * Törli a megadott felhasználót a rendszerből.
     *
     * @param selectedUser A kiválasztott felhasználó
     */
    void deleteUser(User selectedUser);

    /**
     * Keres a felhasználók között a megadott feltételekkel.
     *
     * @param searchUserEntity A paramétereket tartalmazó bean
     * @return A talált felhasználók egy listában
     */
    List<User> searchUser(User searchUserEntity);
}
