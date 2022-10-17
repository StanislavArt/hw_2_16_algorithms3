package pro.sky.java.course2.algorithms3.exceptions;

public class InvalidArgument extends RuntimeException {
    public InvalidArgument() {
        super("Указан неверный аргумент");
    }
}
