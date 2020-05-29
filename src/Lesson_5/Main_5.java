package Lesson_5;

public class Main_5 {

    private static final int size = 10000000;
    private static final int h = size / 2;

    public static void main(String[] args) {

        findTime();
        System.out.println("________________________");
        twoThreadsArrTime();

    }

    private static void findTime(){

        float[] arr = new float[size];

        for (int i = 0; i < arr.length; i++)
            arr[i] = 1;

        long start = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        long finish = System.currentTimeMillis();

        System.out.println("Spent time - " + (finish - start) + " millis");
    }

    private static void twoThreadsArrTime(){

        float[] arr = new float[size];

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        new Thread( () -> {

            for (int i = 0; i < a1.length; i++)
                a1[i] = 1;
            for (int i = 0; i < a1.length; i++)
                a1[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        }).start();

        new Thread( () -> {

            for (int i = 0; i < a2.length; i++)
                a2[i] = 1;
            for (int i = 0; i < a2.length; i++)
                a2[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        }).start();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long finish = System.currentTimeMillis();

        System.out.println("Threads time - " + (finish - start) + " millis");
    }
}
