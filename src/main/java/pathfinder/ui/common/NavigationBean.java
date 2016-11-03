package pathfinder.ui.common;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Ez a bean végzi a navigációt, így egy helyen központosul a web alkalmazás minden oldala.
 * Az XHTML fájlok ezt a bean-t használják a forgalomirányításra.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@ApplicationScoped
public class NavigationBean {
    /**
     * Az út kereső oldalra navigál.
     */
    public String goToRouteSearchPage() {
        return FacesCommon.redirectToJSFPage("/route/route_search");
    }

    /**
     * Az út részletező oldalra navigál.
     */
    public String goToRouteDetailsPage() {
        return FacesCommon.redirectToJSFPage("/route/route_details");
    }

    /**
     * A járművek részletező oldalra navigál.
     */
    public String goToVehicleDetailsPage() {
        return FacesCommon.redirectToJSFPage("/vehicle/vehicle_details");
    }

    /**
     * A járművek kereső oldalra navigál.
     */
    public String goToVehicleSearchPage() {
        return FacesCommon.redirectToJSFPage("/vehicle/vehicle_search");
    }

    /**
     * A város részletező oldalra navigál.
     */
    public String goToCityDetailsPage() {
        return FacesCommon.redirectToJSFPage("/city/city_details");
    }

    /**
     * A város kereső oldalra navigál.
     */
    public String gotCitySearchPage() {
        return FacesCommon.redirectToJSFPage("/city/city_search");
    }

    /**
     * A felhasználó adat módosító oldalra navigál.
     */
    public String goToUserDetailsPage() {
        return FacesCommon.redirectToJSFPage("/user/user_details");
    }

    /**
     * A felhasználó hozzáadása oldalra navigál.
     */
    public String goToUserRegistrationPage() {
        return FacesCommon.redirectToJSFPage("/user/registration");
    }

    /**
     * A generált útvonalak keresése oldalra navigál.
     */
    public String goToPathSearchPage() {
        return FacesCommon.redirectToJSFPage("/path/path_search");
    }

    /**
     * A generált útvonal megtekintése oldalra navigál.
     */
    public String goToPathDetailsPage() {
        return FacesCommon.redirectToJSFPage("/path/path_details");
    }

    /**
     * A kedvenc generált útvonalak listázása képernyőre navigál.
     */
    public String goToPathFavoritePage() {
        return FacesCommon.redirectToJSFPage("/path/favorite_paths");
    }

    /**
     * Kijelentkezik az alkalmazásból.
     */
    public String logout() {
        // Töröljük a session-t, majd átirányítjuk a felhasználót a login oldalra
        SecurityContextHolder.clearContext();
        return FacesCommon.redirectToJSFPage("/login/login");
    }

}
