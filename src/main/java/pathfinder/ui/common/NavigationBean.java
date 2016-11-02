package pathfinder.ui.common;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Ez a bean végzi a navigációt, így egy helyen központosul a web alkalmazás minden oldala.
 * Az XHTML fájlok ezt a bean-t használják a forgalomirányításra.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
@ManagedBean
@ApplicationScoped
public class NavigationBean implements Serializable {
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

    public String goToVehicleDetailsPage() {
        return FacesCommon.redirectToJSFPage("/vehicle/vehicle_details");
    }

    public String goToVehicleSearchPage() {
        return FacesCommon.redirectToJSFPage("/vehicle/vehicle_search");
    }

    public String goToCityDetailsPage() {
        return FacesCommon.redirectToJSFPage("/city/city_details");
    }

    public String gotCitySearchPage() {
        return FacesCommon.redirectToJSFPage("/city/city_search");
    }

    public String goToUserDetailsPage() {
        return FacesCommon.redirectToJSFPage("/user/user_details");
    }

    public String goToUserRegistrationPage() {
        return FacesCommon.redirectToJSFPage("/user/registration");
    }

    public String logout() {
        // Töröljük a session-t, majd átirányítjuk a felhasználót a login oldalra
        SecurityContextHolder.clearContext();
        return FacesCommon.redirectToJSFPage("/login/login");
    }

}
