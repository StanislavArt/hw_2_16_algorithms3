package pro.sky.java.course2.algorithms3.exceptions;

public class WrongSizeOfArray extends RuntimeException {
    public WrongSizeOfArray() {
        super("Неверный размер массива");
    }
}
