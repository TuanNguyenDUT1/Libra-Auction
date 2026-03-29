package io.github.guennhatking.libra_auction.exception;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.guennhatking.libra_auction.dto.request.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) {
        log.error("=== UNCAUGHT EXCEPTION ===", exception);
        log.error("Exception Type: {}", exception.getClass().getName());
        log.error("Exception Message: {}", exception.getMessage());
        
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = BindException.class)
    ResponseEntity<ApiResponse> handlingBindException(BindException exception) {
        log.error("=== Form-Data Validation Error ===", exception);
        
        String errors = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.error("Validation errors: {}", errors);
        
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message("Validation failed: " + errors)
                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var error = exception.getBindingResult().getAllErrors().get(0);

            if (error instanceof org.springframework.validation.FieldError fieldError) {
                attributes = fieldError.getArguments() != null
                        ? Map.of(MIN_ATTRIBUTE, fieldError.getArguments()[1])
                        : null;
            }

            if (Objects.nonNull(attributes)) {
                log.info(attributes.toString());
            }

        } catch (IllegalArgumentException e) {
            log.warn("Invalid error code: {}", enumKey);
        }

        String message = Objects.nonNull(attributes)
                ? mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage();

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(message)
                .build());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse> handlingHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        log.error("=== JSON Parse Error ===", exception);
        log.error("Exception Message: {}", exception.getMessage());
        
        String message = "Invalid JSON format";
        Throwable cause = exception.getCause();
        
        // Get more detailed message if available
        if (cause != null && cause.getMessage() != null) {
            String causeMsg = cause.getMessage();
            if (causeMsg.contains("Unexpected character")) {
                message = "Invalid JSON: " + causeMsg;
            } else {
                message = "JSON parse error: " + causeMsg;
            }
        }
        
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(message)
                .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}