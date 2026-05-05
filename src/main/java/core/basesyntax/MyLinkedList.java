package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
            return;
        }
        Node<T> currentNode = getNode(index);
        Node<T> previousNode = currentNode.prev;
        previousNode.next = newNode;
        newNode.prev = previousNode;
        newNode.next = currentNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            add(element);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        T removedValue = node.value;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;

        while (currentNode != null) {
            boolean valuesAreEqual = object == null ?
                    currentNode.value == null :
                    object.equals(currentNode.value);

            if (valuesAreEqual) {
                Node<T> previousNode = currentNode.prev;
                Node<T> nextNode = currentNode.next;


                if (previousNode == null) {
                    first = nextNode;

                } else {
                    previousNode.next = nextNode;
                }
                if (nextNode == null) {
                    last = previousNode;
                } else {
                    nextNode.prev = previousNode;
                }
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
