import java.util.Arrays;

public class QueueRadixSort {

    // ArrayQueue (Circular Array)//
    public static class ArrayQueue<T> {
        private T[] data;
        private int front;
        private int size;
        private int capacity;

        @SuppressWarnings("unchecked")
        public ArrayQueue(int capacity) {
            this.capacity = capacity;
            data = (T[]) new Object[capacity];
            front = 0;
            size = 0;
        }

        public void enqueue(T item) {
            if (size == capacity) {
                throw new RuntimeException("Queue is full");
            }
            int rear = (front + size) % capacity;
            data[rear] = item;
            size++;
        }

        public T dequeue() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            T item = data[front];
            data[front] = null;
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            return data[front];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }

    //LinkedQueue (Linked List)//
    public static class LinkedQueue<T> {

        private static class Node<T> {
            T data;
            Node<T> next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        private Node<T> head;
        private Node<T> tail;
        private int size;

        public void enqueue(T item) {
            Node<T> newNode = new Node<>(item);

            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;

            if (head == null) {
                head = newNode;
            }

            size++;
        }

        public T dequeue() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }

            T item = head.data;
            head = head.next;

            if (head == null) {
                tail = null;
            }

            size--;
            return item;
        }

        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            return head.data;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }

    //LSD Radix Sort//
    public static void lsdRadixSort(int[] arr) {
        int max = getMax(arr);
        int exp = 1;

        // Create 10 queues (0–9)
        ArrayQueue<Integer>[] buckets = new ArrayQueue[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new ArrayQueue<>(arr.length);
        }

        while (max / exp > 0) {

            // Step 1: Distribute into buckets
            for (int num : arr) {
                int digit = (num / exp) % 10;
                buckets[digit].enqueue(num);
            }

            // Step 2: Collect back into array
            int index = 0;
            for (int i = 0; i < 10; i++) {
                while (!buckets[i].isEmpty()) {
                    arr[index++] = buckets[i].dequeue();
                }
            }

            exp *= 10;
        }
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    //Main Method (Testing)//
    public static void main(String[] args) {

        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};

        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));

        lsdRadixSort(arr);

        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(arr));
    }
}
