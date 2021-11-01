package com.company;

import java.util.*;

public class FloorGenerator {

    private Random rnd;
    private Floor generatedFloor;
    private int[] rooms;
    private int width, height, level;
    private int numberOfRooms;
    private HashSet<Integer> roomQueue;
    private HashSet<Integer> deadEndQueue;

    public Floor generateFloor(int width, int height, int level) {
        generatedFloor = new Floor();

        this.width = width;
        this.height = height;
        this.level = level;

        init();
        generateFloorQueue();
        rooms = convertQueueToRooms(roomQueue);
        printFloor(rooms);

        return generatedFloor;
    }

    private void init() {
        rooms = new int[width * height];
        rnd = new Random();
        roomQueue = new HashSet<>();
        deadEndQueue = new HashSet<>();
        numberOfRooms = (int) (rnd.nextInt(2) + 5 + level * 2.6);
    }

    private void generateFloorQueue() {
        roomQueue.clear();
        roomQueue.add(width * (height / 2) + (width / 2));
        while (roomQueue.size() < numberOfRooms) {
            fillRoomQueue();
        }
    }

    private void fillRoomQueue() {

        Integer[] neighbours;
        Integer[] roomQueueArray = roomQueue.toArray(new Integer[0]);
        for (Integer current : roomQueueArray) {
            neighbours = getNeighbours(current);

            for (Integer neighbour : neighbours) {
                if (roomQueue.contains(neighbour)) {
                    continue;
                }
                if (doesNeighbourHasMoreThanOneNeighbour(neighbour)) {
                    continue;
                }
                if (rnd.nextBoolean()) {
                    continue;
                }
                roomQueue.add(neighbour);
            }
        }

    }

    private int getIndex(int x, int y) {
        return x + width * y;
    }

    private boolean isInOfBound(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private int getXFromIndex(int index) {
        return index % width;
    }

    private int getYFromIndex(int index) {
        return (index / width) % height;
    }

    private boolean doesNeighbourHasMoreThanOneNeighbour(int current) {
        int neighbourCount = 0;
        Integer[] neighbours;
        neighbours = getNeighbours(current);

        for (Integer neighbour : neighbours) {
            if (roomQueue.contains(neighbour) && current != neighbour) neighbourCount++;
        }

        return neighbourCount > 1;
    }

    private Integer[] getNeighbours(int current) {

        ArrayList<Integer> neighbours = new ArrayList<>();

        if (getYFromIndex(current) > 0) neighbours.add(current - width);
        if (getYFromIndex(current) < height - 1) neighbours.add(current + width);
        if (getXFromIndex(current) > 0) neighbours.add(current - 1);
        if (getXFromIndex(current) < width - 1) neighbours.add(current + 1);

        return neighbours.toArray(new Integer[0]);
    }

    private int[] convertQueueToRooms(HashSet<Integer> roomQueue) {
        int[] result = new int[width * height];

        for (Integer i : roomQueue) {
            result[i] = 1;
        }
        return result;
    }

    private void printFloor(int[] floor) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(floor[i + width * j] == 1 ? "\u001B[41m" + "  " + "\u001B[0m" : "  ");
            }
            System.out.print("\n");
        }

    }
}
