package pathfinder.ui.uilogic;

import org.springframework.web.jsf.FacesContextUtils;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.Messages;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Abstract bean, hogy egy helyen legyen a spring bean-ek injektációja.
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public abstract class AbstractBean {
    /**
     * Injektálja a Spring általt menedzselt beaneket a JSF által menedzselt beanekbe.
     */
    @PostConstruct
    protected void postConstruct() {
        FacesContextUtils
                .getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
                .getAutowireCapableBeanFactory().autowireBean(this);
    }

    /**
     * Üzenet megjelenítése a felületen.
     *
     * @param message Az üzenet típusa enumból
     * @param severity Az üzenet típusa, Hiba, Info, stb...
     */
    protected void showMessage(Messages message, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, FacesCommon.getMessage(context, message), null));
    }
}
