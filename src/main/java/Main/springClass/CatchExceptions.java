package Main.springClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatchExceptions extends RuntimeException {
    public CatchExceptions(String exception) {
        super(exception);
    }}
