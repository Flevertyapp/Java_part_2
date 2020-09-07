package lesson5;

import static java.lang.System.currentTimeMillis;

public class MainHW5 {
    static final int SIZE = 1000000;
    static final int HALF_OF_ARRAY = SIZE / 2;

    public static void main(String[] args) {

        float[] sourceArray = new float[SIZE]; //заполнение массива еденицами
        for (int i = 0; i < SIZE; i++) {
            sourceArray[i]= 1;
        }

        calculateArray(sourceArray);
        calculateArrayThread(sourceArray);
    }

    //метод для начального массива
    private static void calculateArray(float[] sourceArray){

        long startTime = currentTimeMillis(); //начальное время
        for (int i = 0; i < SIZE; i++) {
            sourceArray[i] = (float)(sourceArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("результат обычного метода");
        System.currentTimeMillis(); //конечное время
        System.out.println(System.currentTimeMillis() - startTime); //время выполнения
    }

    //метод с разбивкой на два потока
    private static void calculateArrayThread(float[] sourceArray){

        long startTime = currentTimeMillis(); //начальное время
        float[] firstHalfArray = new float[HALF_OF_ARRAY]; //1 половина массива
        float[] secondHalfArray = new float[HALF_OF_ARRAY]; //2 половина массива

        System.arraycopy(sourceArray, 0, firstHalfArray, 0, HALF_OF_ARRAY); //разбивка исходного массива на 2 части
        System.arraycopy(sourceArray, HALF_OF_ARRAY, secondHalfArray, 0, HALF_OF_ARRAY); //разбивка исходного массива на 2 части

        Thread MyThread1 = new Thread(() -> {         //создание через лямбду
            for (int i = 0; i < HALF_OF_ARRAY; i++) {
                firstHalfArray[i] = (float) (firstHalfArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread MyThread2 = new Thread(new Runnable() {  //для себя оставил классическое создание для наглядности
            @Override
            public void run() {
                for (int i = 0; i < HALF_OF_ARRAY; i++) {
                    secondHalfArray[i] = (float)(secondHalfArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        MyThread1.start(); //запускаем потоки
        MyThread2.start();

        try {               //тормозим main поток
            MyThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            MyThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //склейка массива обратно
        System.arraycopy(firstHalfArray, 0, sourceArray, 0, HALF_OF_ARRAY);
        System.arraycopy(secondHalfArray, 0, sourceArray, HALF_OF_ARRAY, HALF_OF_ARRAY);
        System.out.println("результат с потоками");

        System.currentTimeMillis(); //конечное время
        System.out.println(System.currentTimeMillis() - startTime); //время выполнения
    }


}
