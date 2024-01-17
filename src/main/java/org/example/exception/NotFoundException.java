package org.example.exception;

/**
 * Исключение, выбрасываемое в случае, если запрашиваемый ресурс не найден.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Конструктор с передачей сообщения об ошибке.
     *
     * @param message Сообщение об ошибке, описывающее причину возникновения исключения.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
