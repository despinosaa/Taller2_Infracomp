package ejercicios;

public class Ejercicio1 {

    // Clase interna para almacenar el valor máximo de manera segura
    private static class Máximo {
        private int maxValue;

        public Máximo() {
            this.maxValue = Integer.MIN_VALUE;
        }

        // Método sincronizado para obtener el valor máximo
        public synchronized int getMaxValue() {
            return maxValue;
        }

        // Método sincronizado para actualizar el valor máximo
        public synchronized void updateMaxValue(int value) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
    }

    // Clase interna para representar un thread que busca el máximo en una parte del vector
    private static class T extends Thread {
        private int[] vector;
        private int start, end;
        private Máximo máximo;

        public T(int[] vector, int start, int end, Máximo máximo) {
            this.vector = vector;
            this.start = start;
            this.end = end;
            this.máximo = máximo;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                máximo.updateMaxValue(vector[i]);
            }
        }
    }

    public static void main(String[] args) {
        int[] vector = {1, 23, 34, 56, 78, 90, 12, 11, 45, 88, 102, 56, 78, 99};
        int numThreads = 4;

        Máximo máximo = new Máximo();

        // Definir los threads y distribuir el trabajo
        T[] threads = new T[numThreads];
        int lengthPerThread = vector.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * lengthPerThread;
            int end = (i == numThreads - 1) ? vector.length : start + lengthPerThread;
            threads[i] = new T(vector, start, end, máximo);
            threads[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Imprimir el valor máximo encontrado
        System.out.println("El valor máximo es: " + máximo.getMaxValue());
    }
}
