package pathfinder.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Nincs joga a művelet elvégzéséhez!")
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 9023429760321914145L;

}
