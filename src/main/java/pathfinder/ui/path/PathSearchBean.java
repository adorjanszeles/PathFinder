package pathfinder.ui.path;

import pathfinder.model.nodes.Path;
import pathfinder.ui.common.FacesCommon;
import pathfinder.ui.common.NavigationBean;
import pathfinder.ui.uilogic.PathBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * A generált útvonalak keresésére szolgáló view managed bean osztálya.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
@ManagedBean
@SessionScoped
public class PathSearchBean {
    @ManagedProperty(value = "#{pathBeanImpl}")
    private PathBean pathBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;
    private List<Path> pathList;
    private Path selectedPath;
    private Path searchPathEntity;

    @PostConstruct
    public void postConstruct() {
        searchPathEntity = new Path();
    }

    /**
     * Útvonalak keresése.
     */
    public String searchPath() {
        pathList = pathBean.searchPath(searchPathEntity);
        return FacesCommon.stayOnPage();
    }

    /**
     * Útvonal törlése.
     */
    public String deletePath() {
        pathBean.deletePath(selectedPath);
        return navigationBean.goToPathSearchPage();
    }

    public Path getSearchPathEntity() {
        return searchPathEntity;
    }

    public void setSearchPathEntity(Path searchPathEntity) {
        this.searchPathEntity = searchPathEntity;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public Path getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(Path selectedPath) {
        this.selectedPath = selectedPath;
    }

    public void setPathBean(PathBean pathBean) {
        this.pathBean = pathBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}
