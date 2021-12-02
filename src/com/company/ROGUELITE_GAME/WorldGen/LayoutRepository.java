package com.company.ROGUELITE_GAME.WorldGen;

import java.util.ArrayList;
import java.util.Random;

public class LayoutRepository {

    private static LayoutRepository instance;
    private Random random;
    private ArrayList<Layout> layouts;

    public static LayoutRepository getInstance() {
        if (instance == null) {
            instance = new LayoutRepository();
        }
        return instance;
    }

    public LayoutRepository() {
        random = new Random();
        loadLayouts();
    }

    private void loadLayouts() {
        layouts.add(new Layout(new ArrayList<>()));
    }

    public Layout getRandomLayout(boolean[] doors) {
        do {
            int index = random.nextInt(layouts.size());
            if (layouts.get(index).doors == doors) {
                return layouts.get(index);
            }
        }while (true);
    }

}
