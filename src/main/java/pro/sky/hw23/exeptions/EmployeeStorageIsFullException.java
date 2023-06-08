package pro.sky.hw23.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CREATED)
public class EmployeeStorageIsFullException extends RuntimeException{
    public EmployeeStorageIsFullException() {
        super();
    }

    public EmployeeStorageIsFullException(String message) {
        super(message);

    }
}