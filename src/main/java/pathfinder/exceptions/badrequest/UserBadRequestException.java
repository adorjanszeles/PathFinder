package pathfinder.exceptions.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A megadott felhasználó adatok nem megfelelőek!")
public class UserBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -2072358483784577970L;

}
