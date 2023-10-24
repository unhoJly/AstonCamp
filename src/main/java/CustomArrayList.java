import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> extends AbstractList<E> {
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        ensureCapacity();

        if (size - index >= 0) {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }

        elements[index] = element;
        size++;
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    public boolean addAll(Collection<? extends E> list) {
        Object[] a = list.toArray();
        int numNew = a.length;

        if (numNew == 0) {
            return false;
        }

        if (size + numNew > elements.length) {
            expandCapacity();
        }

        System.arraycopy(a, 0, elements, size, numNew);
        size += numNew;
        return true;
    }

    public void expandCapacity() {
        int expand = (elements.length) * 3 / 2;
        Object[] expandArray = new Object[expand];
        System.arraycopy(elements, 0, expandArray, 0, size);
        elements = expandArray;
    }

    public void clear() {
        for (Object element : elements) {
            element = null;
        }

        size = 0;
    }

    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        return (E) elements[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        Object item = elements[index];

        if (size - 1 - index >= 0) {
            System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        }

        size--;
        return (E) item;
    }

    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                this.remove(i);
                return true;
            }
        }

        return false;
    }

    public void sort(Comparator<? super E> comparator) {
        quickSort(0, size() - 1, comparator);
    }

    private void quickSort(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivot = (E) elements[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare((E) elements[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, high);

        return i + 1;
    }

    private void swap(int i, int j) {
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    public int size() {
        return size;
    }
}