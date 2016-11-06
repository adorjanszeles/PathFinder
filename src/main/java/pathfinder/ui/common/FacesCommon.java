package pathfinder.ui.common;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JSF helper metódusok.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 30.
 */
public class FacesCommon {
    /**
     * Helper metódus a navigációhoz.
     *
     * @param pageWithPath Az oldal elérhetőséges és neve
     * @return A string, amellyel a JSF elnavigál az adott oldalra.
     */
    public static String redirectToJSFPage(String pageWithPath) {
        return pageWithPath + "?faces-redirect=true";
    }

    /**
     * Helper metódus az oldalon maradáshoz.
     */
    public static String stayOnPage() {
        return "";
    }

    /**
     * Visszaadja a paraméterként kapott típusnak megfelelő üzenet szövegét.
     *
     * @param context A FacesContext
     * @param message Az üzenet típusa
     * @return Az üzenet szövege
     */
    public static String getMessage(FacesContext context, Messages message) {
        if(context.getViewRoot() != null) {
            Locale locale = context.getViewRoot().getLocale();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ResourceBundle bundle = ResourceBundle.getBundle("pathfinder.properties.messages", locale, loader);
            return bundle.getString(message.name());
        }
        return message.name();
    }

}
