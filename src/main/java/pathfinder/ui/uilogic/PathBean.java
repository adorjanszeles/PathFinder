package pathfinder.ui.uilogic;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;

/**
 * A generált útvonalak felületi üzleti logikájának interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public interface PathBean {
    /**
     * Útvonal generálás.
     *
     * @param from A kiinduló város
     * @param to A cél város
     * @param vehicle A jármű
     * @return Egy útvonal a jármű számára
     */
    Path generatePath(City from, City to, Vehicle vehicle);
}
