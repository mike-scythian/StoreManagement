package nix.project.store.management.controllers;

import nix.project.store.management.dto.ErrorMessageDto;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.InvalidPasswordException;
import nix.project.store.management.exceptions.NotEnoughLeftoversException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data not found")
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity <ErrorMessageDto> handlerException(DataNotFoundException e){

        ErrorMessageDto error = new ErrorMessageDto(e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong password input")
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity <ErrorMessageDto> handlerException(InvalidPasswordException e){

        ErrorMessageDto error = new ErrorMessageDto(e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Value already exists in database")
    @ExceptionHandler(ValueExistsAlreadyException.class)
    public ResponseEntity <ErrorMessageDto> handlerException(ValueExistsAlreadyException e){

        ErrorMessageDto error = new ErrorMessageDto(e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Тot enough resources")
    @ExceptionHandler(NotEnoughLeftoversException.class)
    public ResponseEntity <ErrorMessageDto> handlerException(NotEnoughLeftoversException e){

        ErrorMessageDto error = new ErrorMessageDto(e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
}
