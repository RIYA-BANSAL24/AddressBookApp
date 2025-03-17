package com.AddressBookApp.exception;

import com.AddressBookApp.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🛑 Handle Validation Errors (Contact & User)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseDTO response = new ResponseDTO("Validation Failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 🛑 Handle Contact Not Found Exception
    @ExceptionHandler(ContactException.class)
    public ResponseEntity<ResponseDTO> handleContactNotFoundException(ContactException ex) {
        ResponseDTO response = new ResponseDTO("Error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 🛑 Handle User Not Found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        ResponseDTO response = new ResponseDTO("User Not Found", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 🛑 Handle Duplicate Email Exception
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ResponseDTO> handleDuplicateEmailException(DuplicateEmailException ex) {
        ResponseDTO response = new ResponseDTO("Email Already Exists", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 🛑 Handle Invalid Login Exception
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ResponseDTO> handleInvalidLoginException(InvalidLoginException ex) {
        ResponseDTO response = new ResponseDTO("Invalid Login Credentials", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 🛑 Handle Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGlobalException(Exception ex) {
        ResponseDTO response = new ResponseDTO("An unexpected error occurred", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
