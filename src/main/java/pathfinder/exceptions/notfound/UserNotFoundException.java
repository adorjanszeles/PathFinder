package pathfinder.exceptions.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A felhasználó nem található!")
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4277964768178600839L;
}
