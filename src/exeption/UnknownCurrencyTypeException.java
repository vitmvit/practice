package exeption;

public class UnknownCurrencyTypeException extends RuntimeException {
    public UnknownCurrencyTypeException() {
        super("Неизвестный тип валюты");
    }
}
