package pathfinder.ui.uilogic;

import pathfinder.model.nodes.City;
import pathfinder.model.nodes.Path;
import pathfinder.model.nodes.Vehicle;

import java.util.List;

/**
 * A generált útvonalak felületi üzleti logikájának interfésze.
 *
 * @author Széles Adorján
 * Date: 2016. 11. 01.
 */
public interface PathBean {
    /**
     * Az adatbázisban lévő generált útvonalak közötti keresés.
     *
     * @param searchParams A keresési feltételeket tartalmazó bean
     * @return Egy lista a talált elemekkel.
     */
    List<Path> searchPath(Path searchParams);

    /**
     * Útvonal generálás.
     *
     * @param from A kiinduló város
     * @param to A cél város
     * @param vehicle A jármű
     * @return Egy útvonal a jármű számára
     */
    Path generatePath(City from, City to, Vehicle vehicle);

    /**
     * Egy útvonal törlése.
     *
     * @param path A törlendő útvonal
     */
    void deletePath(Path path);

    /**
     * Elment egy generált útvonalat az adatbázisba.
     *
     * @param selectedPath A mentendő útvonal
     */
    void savePath(Path selectedPath);
}
