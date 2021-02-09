package br.gov.sp.fatec.presensor.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
class AccessDeniedExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public void handleConflict(HttpServletResponse response) throws IOException {
        response.sendError(403, "Acesso não autorizado");
    }

}
