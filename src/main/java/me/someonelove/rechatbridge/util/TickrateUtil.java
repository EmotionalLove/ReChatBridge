package me.someonelove.rechatbridge.util;

import java.util.Arrays;

public class TickrateUtil {

    private final float[] tickRates = new float[3];
    public static TickrateUtil INSTANCE;
    private int nextIndex = 0;
    public long timeLastTimeUpdate;

    public TickrateUtil() {
        INSTANCE = this;
    }

    public float getTickRate() {
        float numTicks = 0.0F;
        float sumTickRates = 0.0F;
        for (float tickRate : tickRates) {
            if (tickRate > 0.0F) {
                sumTickRates += tickRate;
                numTicks += 1.0F;
            }
        }
        try {
            return Util.fround(sumTickRates / numTicks, 2);
        } catch (NumberFormatException e) {
            return 20f;
        }
    }


    public void onTimeUpdate() {

        if (timeLastTimeUpdate != -1L) {
            float timeElapsed = (float) (System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
            tickRates[(nextIndex % tickRates.length)] = Util.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
            nextIndex += 1;
        }
        timeLastTimeUpdate = System.currentTimeMillis();
    }

    public boolean isServerResponding() {
        return timeLastTimeUpdate == -1 || System.currentTimeMillis() - timeLastTimeUpdate <= 5000L;
    }

    public void reset() {
        nextIndex = 0;
        timeLastTimeUpdate = -1L;
        Arrays.fill(tickRates, 0.0F);
    }
}
