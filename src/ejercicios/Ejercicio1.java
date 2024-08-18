package ejercicios;

import java.util.Scanner;

public class Ejercicio1 {

    private static class Maximo {
        private int maxValue;

        public Maximo() {
            this.maxValue = Integer.MIN_VALUE;
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

    private static class T extends Thread {
        private int[] vector;
        private int start, end;
        private Maximo maximo;

        public T(int[] vector, int start, int end, Maximo maximo) {
            this.vector = vector;
            this.start = start;
            this.end = end;
            this.maximo = maximo;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                maximo.updateMaxValue(vector[i]);
            }
        }
    }

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica el número de threads: ");
        int numThreads = scanner.nextInt();

        int[] vector = {1, 4, 50, 12, 98, 35, 7, 11, 130, 48, 9, 68, 109, 13, 34, 3};

        Maximo maximo = new Maximo();

        // Crear los threads
        T[] threads = new T[numThreads];
        int lengthPerThread = vector.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * lengthPerThread;
            int end = (i == numThreads - 1) ? vector.length : start + lengthPerThread;
            threads[i] = new T(vector, start, end, maximo);
            threads[i].start();
        }

        // Esperar a que todos los threads terminen
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("El valor máximo es: " + maximo.getMaxValue());
    }
}
