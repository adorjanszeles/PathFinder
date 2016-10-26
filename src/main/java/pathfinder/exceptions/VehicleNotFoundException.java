package pathfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A jármű nem található!")
public class VehicleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2697836134215708603L;
}
