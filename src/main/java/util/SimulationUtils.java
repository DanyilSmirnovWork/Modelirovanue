package util;

import java.util.Random;

public class SimulationUtils {

    /** Функция, которая переводит время в миллисекунды учитывая скорость моделирования
     * @return время в миллисекундах
     */
    public static long getTimeMillis(long time, long speed) {
        return time * 60000 / speed;
    }

    /**
     * Функция распределяющая детали
     * @return возвращает true или false в зависимости от случайного числа
     */
    public static boolean getRandom(double probability) {
        return Math.random() < probability;
    }

    public static long roundUp(Double number) {
        return (int) Math.ceil(number);
    }

    @Deprecated
    public static void getExpRandom() {
        final double alpha = 0.08D;
        final String FORMAT = "%f\t%f";

        double sum1 = 0;
        double sum2 = 0;
        Random random = new Random();
        for (int i=0; i<20; i++) {

            double current = random.nextDouble();

            // Формула из методички
            double resultMethodical = (-(1.0 / alpha)) * Math.log(current);

            // Формула по указанной ссылке
            double resultOther = Math.log(1 - current) / (-alpha);

            System.out.println(
                    String.format(FORMAT, resultMethodical, resultOther));
            sum1 += resultMethodical;
            sum2 += resultOther;
        }
    }
}
