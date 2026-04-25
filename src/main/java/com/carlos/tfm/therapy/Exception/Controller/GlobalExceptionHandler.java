package com.carlos.tfm.therapy.Exception.Controller;

import com.carlos.tfm.therapy.Exception.DTO.CustomError;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityExistsException;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.Exception.Exceptions.InvalidOperationException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.status(422)
                .body(new CustomError(
                        LocalDateTime.now().toString(),
                        422,
                        errors.toString()
                ));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomError> handleEntityExists(EntityExistsException ex) {
        return ResponseEntity.status(409)
                .body(new CustomError(
                        LocalDateTime.now().toString(),
                        409,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<CustomError> handleEntityNotFound(EntityNotFound ex) {
        return ResponseEntity.status(404)
                .body(new CustomError(
                        LocalDateTime.now().toString(),
                        404,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<CustomError> handleInvalidOperation(InvalidOperationException ex) {
        return ResponseEntity.status(400)
                .body(new CustomError(
                        LocalDateTime.now().toString(),
                        400,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> handleBadRequest(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {

            Class<?> targetType = ife.getTargetType();

            if (targetType != null && targetType.isEnum()) {

                Object[] values = targetType.getEnumConstants();

                return ResponseEntity.badRequest().body(
                        new CustomError(
                                LocalDateTime.now().toString(),
                                400,
                                "Valor inválido para " + targetType.getSimpleName() +
                                        ". Valores permitidos: " + Arrays.toString(values)
                        )
                );
            }
        }

        return ResponseEntity.badRequest().body(
                new CustomError(
                        LocalDateTime.now().toString(),
                        400,
                        "Request mal formado"
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleGeneric(Exception ex) {

        ex.printStackTrace();

        return ResponseEntity.status(500)
                .body(new CustomError(
                        LocalDateTime.now().toString(),
                        500,
                        ex.getMessage()
                ));
    }
}