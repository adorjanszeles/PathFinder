package pathfinder.ui.path;

import pathfinder.model.nodes.Path;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.uilogic.PathBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@SessionScoped
public class PathFavoriteBean {
    @ManagedProperty(value = "#{pathBeanImpl}")
    private PathBean pathBean;
    private List<Path> pathList;

    /**
     * Az oldal betöltődésekor lekéri a kedvenc pathokat.
     */
    public String getPaths() {
        pathList = pathBean.searchPath(null);
        return FacesCommon.stayOnPage();
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public void setPathBean(PathBean pathBean) {
        this.pathBean = pathBean;
    }
}
