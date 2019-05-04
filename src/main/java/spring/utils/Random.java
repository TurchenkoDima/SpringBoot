package spring.utils;

public class Random {
    private static final int ONE_HUNDRED = 100;

    public static int getRandom() {
        return (int) (Math.random() * ONE_HUNDRED);
    }
}
