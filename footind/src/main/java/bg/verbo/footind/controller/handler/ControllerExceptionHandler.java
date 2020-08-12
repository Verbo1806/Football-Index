package bg.verbo.footind.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bg.verbo.footind.controller.error.Error;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	private static final String STACK_TRACE_LOG = "Stack trace: ";

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Error> handleException(Exception ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity
				.status(500)
				.body(new Error().setErrorName("An unknown error has occurred!").setErrorDescription("Unknown exception"));
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Error> handleException(MethodArgumentNotValidException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity.
				ok(new Error().setErrorName("Not valid entity!").setErrorDescription("MethodArgumentNotValidException"));
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Error> handleException(HttpMessageNotReadableException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Error> handleException(AccessDeniedException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity
				.status(401)
				.body(new Error().setErrorName("AccessDenied!").setErrorDescription("User doesn't have permission to call this service!"));
	}
	
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Error> handleException(BadCredentialsException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity
				.status(401)
				.body(new Error().setErrorName("AccessDenied!").setErrorDescription("Invalid credentials!"));
	}
	
	@ExceptionHandler({DisabledException.class})
	public ResponseEntity<Error> handleException(DisabledException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		return ResponseEntity
				.status(401)
				.body(new Error().setErrorName("AccessDenied!").setErrorDescription("Disabled user!"));
	}
	
}
