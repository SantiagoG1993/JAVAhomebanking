package utils;
import java.util.Random;

 public final class CardUtils {
     private CardUtils() {
     }
    public static int getCVV() {
        int cvvRandom = new Random().nextInt(900)+100;
        return cvvRandom;
    }

    public static String getCardNumber() {
        String randomCardNumber=(int)((Math.random()*(9999-1000))+1000)
                +"-"+(int)((Math.random()*(9999-1000))+1000)
                +"-"+(int)((Math.random()*(9999-1000))+1000)
                +"-"+(int)((Math.random()*(9999-1000))+1000);
        return randomCardNumber;
    }
 }
