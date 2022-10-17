package pro.sky.java.course2.algorithms3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pro.sky.java.course2.algorithms3.exceptions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegerListTest {
    private final IntegerList out = new IntegerListImp(20);

    @BeforeAll
    public void initSetup() {
        out.add(16);
        out.add(100);
        out.add(51);
        out.add(32);
        out.add(4);
        out.add(51);
        out.add(33);
    }

    @Test
    public void add() {
        Integer expected = 82;
        int beforeSize = out.size();
        Integer actual = out.add(expected);
        int afterSize = out.size();

        Assertions.assertEquals(1, afterSize-beforeSize);
        Assertions.assertEquals(expected, actual);
        actual = out.get(out.size()-1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addByIndex() {
        Integer expected = 19;
        int beforeSize = out.size();
        Integer actual = out.add(0, expected);
        int afterSize = out.size();

        Assertions.assertEquals(1, afterSize-beforeSize);
        Assertions.assertEquals(expected, actual);
        actual = out.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addGrow() {
        IntegerList out1 = new IntegerListImp(2);
        out1.add(25);
        out1.add(50);

        Assertions.assertFalse(out1.size() > 2);
        out1.add(100);
        Assertions.assertTrue(out1.size() > 2);
    }

    @Test
    public void set() {
        Integer expected = 21;
        int beforeSize = out.size();
        Integer actual = out.set(0, expected);
        int afterSize = out.size();

        Assertions.assertEquals(0, afterSize-beforeSize);
        Assertions.assertEquals(expected, actual);
        actual = out.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void removeByElement() {
        Integer expected = 16;
        int beforeSize = out.size();
        Integer actual = out.remove(expected);
        int afterSize = out.size();

        Assertions.assertEquals(-1, afterSize-beforeSize);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void removeByIndex() {
        Integer expected = out.get(0);
        int beforeSize = out.size();
        Integer actual = out.remove(0);
        int afterSize = out.size();

        Assertions.assertEquals(-1, afterSize-beforeSize);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void contains() {
        Integer element = 33;
        boolean result = out.contains(element);
        Assertions.assertTrue(result);

        element = 1001;
        result = out.contains(element);
        Assertions.assertFalse(result);
    }

    @Test
    public void indexOf() {
        int expected = 1;
        Integer element = out.get(expected);
        Assertions.assertNotNull(expected);

        int actual = out.indexOf(element);
        Assertions.assertEquals(expected, actual);

        actual = out.indexOf(1001);
        Assertions.assertEquals(-1, actual);
    }

    @Test
    public void lastIndexOf() {
        Integer element = 51;

        int actualIndex = out.indexOf(element);
        int actualLastIndex = out.lastIndexOf(element);
        Assertions.assertTrue(actualIndex>0);
        Assertions.assertTrue(actualLastIndex>0);
        Assertions.assertNotEquals(actualIndex, actualLastIndex);

        int actual = out.lastIndexOf(1001);
        Assertions.assertEquals(-1, actual);
    }

    @Test
    public void equals() {
        IntegerList out1 = new IntegerListImp(10);
        out1.add(25);
        out1.add(50);

        IntegerList out2 = new IntegerListImp(10);
        out2.add(25);

        boolean actual = out1.equals(out2);
        Assertions.assertFalse(actual);

        out2.add(50);
        actual = out1.equals(out2);
        Assertions.assertTrue(actual);
    }

    @Test
    public void clear() {
        IntegerList out1 = new IntegerListImp(10);
        out1.add(25);
        out1.add(50);

        Assertions.assertTrue(out1.size()>0);
        out1.clear();
        Assertions.assertTrue(out1.size()==0);
    }

    @Test
    public void isEmpty() {
        IntegerList out1 = new IntegerListImp(10);
        Assertions.assertTrue(out1.isEmpty());

        out1.add(25);
        out1.add(50);
        Assertions.assertFalse(out1.isEmpty());
    }

    @Test
    public void toArray() {
        IntegerList out1 = new IntegerListImp(10);
        out1.add(25);
        out1.add(50);

        Integer[] expected = {25, 50};
        Integer[] actual = out1.toArray();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void createStringListException() {
        Assertions.assertThrows(WrongSizeOfArray.class, () -> new IntegerListImp(0));
    }

    @Test
    public void getException() {
        Assertions.assertThrows(WrongIndex.class, () -> out.get(-5));
    }

    @Test
    public void addSetException() {
        IntegerList out1 = new IntegerListImp(2);
        out1.add(25);
        out1.add(50);

        Integer element = null;
        Assertions.assertThrows(InvalidArgument.class, () -> out1.add(element));
        Assertions.assertThrows(WrongIndex.class, () -> out1.add(out1.size()+1, 100));

        Assertions.assertThrows(InvalidArgument.class, () -> out1.set(0, element));
        Assertions.assertThrows(WrongIndex.class, () -> out1.set(-1, 100));
    }

    @Test
    public void removeException() {
        IntegerList out1 = new IntegerListImp(2);
        out1.add(25);
        out1.add(50);

        Integer element = null;
        Assertions.assertThrows(InvalidArgument.class, () -> out1.remove(element));
        Assertions.assertThrows(WrongIndex.class, () -> out1.remove(-1));
        Assertions.assertThrows(ElementNotFound.class, () -> out1.remove(Integer.valueOf(100)));
    }

    @Test
    public void invalidArgumentException() {
        Integer element = null;
        IntegerList integerList = null;
        Assertions.assertThrows(InvalidArgument.class, () -> out.contains(element));
        Assertions.assertThrows(InvalidArgument.class, () -> out.indexOf(element));
        Assertions.assertThrows(InvalidArgument.class, () -> out.lastIndexOf(element));
        Assertions.assertThrows(InvalidArgument.class, () -> out.equals(integerList));
    }

    @Test
    public void bubbleSort() {
        Integer[] actual = {45, 3, 33, 5, 67, 32, 21, 89, 103, 2};
        Integer[] expected = {2, 3, 5, 21, 32, 33, 45, 67, 89, 103};
        IntegerListImp.testSortAlgorithm(actual, "bubbleSort");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void insertionSort() {
        Integer[] actual = {45, 3, 33, 5, 67, 32, 21, 89, 103, 2};
        Integer[] expected = {2, 3, 5, 21, 32, 33, 45, 67, 89, 103};
        IntegerListImp.testSortAlgorithm(actual, "insertionSort");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void quickSort() {
        Integer[] actual = {45, 3, 33, 5, 67, 32, 21, 89, 103, 2};
        Integer[] expected = {2, 3, 5, 21, 32, 33, 45, 67, 89, 103};
        IntegerListImp.testSortAlgorithm(actual, "quickSort");
        Assertions.assertArrayEquals(expected, actual);
    }
}
