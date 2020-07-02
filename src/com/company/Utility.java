package com.company;

import java.util.Random;

public class Utility {
    public static int rangeRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
