package ejercicios;

public class Ejercicio1y2 {
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
        };

        M�ximo m�ximo = new M�ximo();
        Id id = new Id();

        int numThreads = matrix.length;
        T[] threads = new T[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new T(matrix, id, m�ximo);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("El valor m�ximo es: " + m�ximo.getMaxValue());
    }
}

class M�ximo {
    private int maxValue;

    public M�ximo() {
        maxValue = Integer.MIN_VALUE;
    }

    public synchronized int getMaxValue() {
        return maxValue;
    }

    public synchronized void updateMaxValue(int value) {
        if (value > maxValue) {
            maxValue = value;
        }
    }
}

class Id {
    private int currentId;

    public Id() {
        this.currentId = 0;
    }

    public synchronized int darId() {
        return currentId++;
    }
}

class T extends Thread {
    private int[][] matrix;
    private Id id;
    private M�ximo m�ximo;

    public T(int[][] matrix, Id id, M�ximo m�ximo) {
        this.matrix = matrix;
        this.id = id;
        this.m�ximo = m�ximo;
    }

    @Override
    public void run() {
        int rowId = id.darId();

        for (int j = 0; j < matrix[rowId].length; j++) {
            m�ximo.updateMaxValue(matrix[rowId][j]);
        }
    }
}

