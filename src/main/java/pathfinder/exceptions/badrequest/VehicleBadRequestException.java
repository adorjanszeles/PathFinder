package pathfinder.exceptions.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A megadott jármű adatok nem megfelelőek!")
public class VehicleBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -8477004191995968740L;

}
