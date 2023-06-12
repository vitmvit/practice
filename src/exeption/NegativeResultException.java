package exeption;

public class NegativeResultException extends RuntimeException {
    public NegativeResultException() {
        super("Отрицательный результат");
    }
}
