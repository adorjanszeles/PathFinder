package pathfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Az útvonal nem található!")
public class RouteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7001344956522935753L;

}
