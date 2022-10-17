package pro.sky.java.course2.algorithms3.exceptions;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound() {
        super("Элемнт не найден");
    }
}
