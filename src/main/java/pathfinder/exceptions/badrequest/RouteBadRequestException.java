package pathfinder.exceptions.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A megadott útvonal adatok nem megfelelőek!")
public class RouteBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 3553979965094806186L;

}
