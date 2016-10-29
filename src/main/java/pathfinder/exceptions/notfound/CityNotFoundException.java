package pathfinder.exceptions.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "A város nem található!")
public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 673332090916477698L;

}
