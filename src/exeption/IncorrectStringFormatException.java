package exeption;

public class IncorrectStringFormatException extends RuntimeException {

    public IncorrectStringFormatException() {
        super("Некорректный формат строки");
    }
}
