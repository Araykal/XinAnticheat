/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.utils;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static Random getRandom() {
        return random;
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        return endExclusive - startInclusive <= 0 ? startInclusive : startInclusive + new Random().nextInt(endExclusive - startInclusive);
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        return startInclusive == endInclusive || endInclusive - startInclusive <= 0.0 ? startInclusive : startInclusive + (endInclusive - startInclusive) * Math.random();
    }

    public static long nextLong(long startInclusive, long endInclusive) {
        return endInclusive - startInclusive <= 0L ? startInclusive : (long)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        return startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f ? startInclusive : (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    public final long randomDelay(int minDelay, int maxDelay) {
        return RandomUtil.nextInt(minDelay, maxDelay);
    }
}

