package pro.sky.java.course2.algorithms3;

import pro.sky.java.course2.algorithms3.exceptions.*;

import java.util.Arrays;
import java.util.Random;

public class IntegerListImp implements IntegerList {
    private int fullSize;
    private int size;
    private Integer[] array;

    public IntegerListImp(int fullSize) {
        if (fullSize <= 0) {
            throw new WrongSizeOfArray();
        }

        this.array = new Integer[fullSize];
        this.size = 0;
        this.fullSize = fullSize;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for(int i=0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public Integer get(int index) {
        if (index < 0 || index >= size) {
            throw new WrongIndex();
        }
        return array[index];
    }

    public Integer add(Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        if (size == fullSize) {
            throw new ArrayIsFull();
        }
        array[size] = item;
        size++;
        return item;
    }

    private void moveElementsRight(int index) {
        for (int i=size; i > index; i--) {
            array[i] = array[i-1];
        }
    }

    private void moveElementsLeft(int index) {
        for(int i = index; i < size-1; i++) {
            array[i] = array[i+1];
        }
        array[size-1] = null;
    }

    public Integer add(int index, Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        if (index < 0 || index > size) {
            throw new WrongIndex();
        }
        if (size == fullSize) {
            throw new ArrayIsFull();
        }

        if (index == size) {
            add(item);
        } else {
            moveElementsRight(index);
            array[index] = item;
            size++;
        }
        return item;
    }

    public Integer set(int index, Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        if (index < 0 || index >= size) {
            throw new WrongIndex();
        }
        array[index] = item;
        return item;
    }

    public Integer remove(Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFound();
        }
        return remove(index);
    }

    public Integer remove(int index) {
        if (index < 0 || index >= size) {
            throw new WrongIndex();
        }
        Integer item = get(index);
        moveElementsLeft(index);
        size--;
        return item;
    }

    public boolean contains(Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        IntegerListImp.quickSort(array, 0, size-1);
        int idx = binarySearch(item, 0, size-1);
        return idx >= 0;
    }

    public int indexOf(Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        for (int i=0; i < size; i++) {
            if (item.equals(array[i])) return i;
        }
        return -1;
    }

    public int lastIndexOf(Integer item) {
        if (item == null) {
            throw new InvalidArgument();
        }
        for (int i=size-1; i >= 0; i--) {
            if (item.equals(array[i])) return i;
        }
        return -1;
    }

    public boolean equals(IntegerList otherList) {
        if (otherList == null) {
            throw new InvalidArgument();
        }
        if (size != otherList.size()) return false;
        for (int i=0; i<size; i++) {
            if (!array[i].equals(otherList.get(i))) return false;
        }
        return true;
    }

    public Integer[] toArray() {
        if (size == 0) return null;
        Integer[] result = new Integer[size];
        for (int i=0; i < size;  i++) {
            result[i] = array[i];
        }
        return result;
    }

    // для проверки быстродействия алгоритмов
    public static String performanceSortAlgorithms() {
        Integer[] arrayBubbleSort = generateArray(100_000);
        Integer[] arrayInsertionSort = Arrays.copyOf(arrayBubbleSort, arrayBubbleSort.length);
        Integer[] arrayQuickSort = Arrays.copyOf(arrayBubbleSort, arrayBubbleSort.length);

        String result = "Производительность алгоритмов:\n";

        long start = System.currentTimeMillis();
        bubbleSort(arrayBubbleSort, 0, arrayBubbleSort.length-1);
        long finish = System.currentTimeMillis();
        result += "\nвремя выполнения пузырьковой сортировки = " + (finish - start) + " мс";

        start = System.currentTimeMillis();
        insertionSort(arrayInsertionSort, 0, arrayInsertionSort.length-1);
        finish = System.currentTimeMillis();
        result += "\nвремя выполнения сортировки вставками = " + (finish - start) + " мс";

        start = System.currentTimeMillis();
        quickSort(arrayQuickSort, 0, arrayQuickSort.length-1);
        finish = System.currentTimeMillis();
        result += "\nвремя выполнения быстрой сортировки = " + (finish - start) + " мс";

        return result;
    }

    // метод для тестирования алгоритмов сортировки
    public static void testSortAlgorithm(Integer[] array, String name) {
        switch (name) {
            case("bubbleSort") -> bubbleSort(array, 0, array.length-1);
            case("insertionSort") -> insertionSort(array, 0, array.length-1);
            case("quickSort") -> quickSort(array, 0, array.length-1);
        }
    }

    private static Integer[] generateArray(int quantity) {
        Integer[] result = new Integer[quantity];
        Random random = new Random(System.currentTimeMillis());
        for (int i=0; i < quantity; i++) {
            result[i] = random.nextInt(50_000);
        }
        return result;
    }

    // пузырьковая сортировка
    private static void bubbleSort(Integer[] array, int leftIndex, int rightIndex) {
        boolean flag;
        do {
            flag = false;
            for(int i=leftIndex; i < rightIndex; i++) {
                if (array[i] > array[i+1]) {
                    int tmp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tmp;
                    flag = true;
                }
            }
        } while (flag);
    }

    // быстрая сортировка
    private static void quickSort(Integer[] array, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) return;

        if (rightIndex - leftIndex == 1) {
            if (array[leftIndex] > array[rightIndex]) {
                int tmp = array[leftIndex];
                array[leftIndex] = array[rightIndex];
                array[rightIndex] = tmp;
            }
            return;
        }

        int leftBorder = leftIndex;
        int rightBorder = rightIndex;
        int pivot = (rightIndex - leftIndex+1) / 2 + leftIndex;

        while (leftIndex < rightIndex) {
            while(array[leftIndex] < array[pivot] && leftIndex < pivot) leftIndex++;
            while(array[rightIndex] > array[pivot] && rightIndex > pivot) rightIndex--;
            if (leftIndex < rightIndex) {
                int tmp = array[leftIndex];
                array[leftIndex] = array[rightIndex];
                array[rightIndex] = tmp;
                leftIndex++;
                rightIndex--;
            }
        }
        quickSort(array, leftBorder, pivot);
        quickSort(array, pivot, rightBorder);
    }

    // алгоритм сортировки вставками
    private static void insertionSort(Integer[] array, int leftIndex, int rightIndex) {
        for (int i=rightIndex; i>=leftIndex+1; i--) {
            for (int j=leftIndex; j < i; j++) {
                if (array[j] > array[i]) {
                    int tmp = array[j];
                    array[j] = array[i];
                    array[i] = tmp;
                }
            }
        }
    }

    private int binarySearch(Integer item, int leftIndex, int rightIndex) {
        int result = -1;

        if (leftIndex > rightIndex) return result;

        int idx = (rightIndex - leftIndex) / 2 + leftIndex;
        if (array[idx].equals(item)) return idx;
        if (leftIndex == rightIndex) return result;

        if (item < array[idx]) {
            result = binarySearch(item, leftIndex, idx-1);
        }
        if (item > array[idx]) {
            result = binarySearch(item, idx+1, rightIndex);
        }
        return result;
    }


}
