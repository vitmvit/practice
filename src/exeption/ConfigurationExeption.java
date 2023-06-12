package exeption;

public class ConfigurationExeption extends RuntimeException {
    public ConfigurationExeption() {
        super("Не найден параметр в конфигурации");
    }
}
