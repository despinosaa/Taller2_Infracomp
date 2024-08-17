package ejercicios;

public class Ejercicio1 {

    // Clase interna para almacenar el valor m�ximo de manera segura
    private static class M�ximo {
        private int maxValue;

        public M�ximo() {
            this.maxValue = Integer.MIN_VALUE;
        }

        // M�todo sincronizado para obtener el valor m�ximo
        public synchronized int getMaxValue() {
            return maxValue;
        }

        // M�todo sincronizado para actualizar el valor m�ximo
        public synchronized void updateMaxValue(int value) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
    }

    // Clase interna para representar un thread que busca el m�ximo en una parte del vector
    private static class T extends Thread {
        private int[] vector;
        private int start, end;
        private M�ximo m�ximo;

        public T(int[] vector, int start, int end, M�ximo m�ximo) {
            this.vector = vector;
            this.start = start;
            this.end = end;
            this.m�ximo = m�ximo;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                m�ximo.updateMaxValue(vector[i]);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Indica el n�mero de threads: ");
            return;
        }

        int numThreads = Integer.parseInt(args[0]);

        int[] vector = {1,2,3,4,5,6,7,8,9,10,20,30,40,50,60};

        M�ximo m�ximo = new M�ximo();

        // Definir los threads y distribuir el trabajo
        T[] threads = new T[numThreads];
        int lengthPerThread = vector.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * lengthPerThread;
            int end = (i == numThreads - 1) ? vector.length : start + lengthPerThread;
            threads[i] = new T(vector, start, end, m�ximo);
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

        // Imprimir el valor m�ximo encontrado
        System.out.println("El valor m�ximo es: " + m�ximo.getMaxValue());
    }
}