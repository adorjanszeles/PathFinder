package pathfinder.exceptions.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A megadott várod adatok nem megfelelőek!")
public class CityBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 6111259649997514344L;

}
