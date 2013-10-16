package zx.exception;

public class ExistedException extends RuntimeException {
    private String value;

    public ExistedException(String value) {
        super("Existed value: " + value);
        this.value = value;
    }
}
