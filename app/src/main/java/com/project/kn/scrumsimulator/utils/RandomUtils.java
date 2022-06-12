package com.project.kn.scrumsimulator.utils;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static int getRandomIntBetween(int low, int high) {

        return random.nextInt(high - low) + low;
    }
}
