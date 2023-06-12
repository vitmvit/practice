package exeption;

public class IncompatibleCurrencyTypesException extends RuntimeException {

    public IncompatibleCurrencyTypesException() {
        super("Несовместимые типы валют");
    }
}
