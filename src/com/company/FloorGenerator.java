package com.company;

import java.util.*;

public class FloorGenerator {

    private Random rnd;
    private Floor generatedFloor;
    private int[] rooms;
    private int width, height, level;
    private int numberOfRooms;
    private Queue<Integer> roomQueue;
    private Queue<Integer> deadEndQueue;

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
        roomQueue = new LinkedList<>();
        deadEndQueue = new LinkedList<>();
        numberOfRooms = (int) (rnd.nextInt(2) + 5 + level * 2.6);
    }

    private void generateFloorQueue() {
        roomQueue.clear();
        roomQueue.add((width * (height / 2)));
        while (roomQueue.size() < numberOfRooms) {
            fillRoomQueue();
        }
    }

    private void fillRoomQueue() {

        int[] neighbours;
        Integer[] roomQueueArray = new Integer[roomQueue.size()];
        roomQueueArray = roomQueue.toArray(roomQueueArray);
        for (Integer current : roomQueueArray) {
            neighbours = getNeighbours(current);

            for (int i = 0; i < neighbours.length; i++) {

                if (!isInOfBound(getXFromIndex(neighbours[i]), getYFromIndex(neighbours[i]))) {
                    continue;
                }
                if (isNumberInQueue(neighbours[i])) {
                    continue;
                }
                if (doesNeighbourHasMoreThanOneNeighbour(neighbours[i])) {
                    continue;
                }
                if (rnd.nextBoolean()) {
                    continue;
                }
                if (neighbours[i] < 0 || neighbours[i] >= (width * height)) {
                    continue;
                }
                roomQueue.add(neighbours[i]);
            }
        }

    }

    private int getIndex(int x, int y) {
        return x + width * y;
    }

    private boolean isInOfBound(int x, int y) {
        return x >= 0 || y >= 0 || x <= width || y <= height;//x >= 0 && y >= 0 && x < width && y < height // x >= 0 || y >= 0 || x <= width || y <= height
    }

    private int getXFromIndex(int index) {
        return index % width;
    }

    private int getYFromIndex(int index) {
        return (index / width) % height;
    }

    private boolean isNumberInQueue(int number) {
        for (Integer current : roomQueue) {
            if (current == number) {
                return true;
            }
        }
        return false;
    }

    private boolean doesNeighbourHasMoreThanOneNeighbour(int current) {
        int neighbourCount = 0;
        int[] neighbours;
        neighbours = getNeighbours(current);

        for (int i = 0; i < neighbours.length; i++) {
            for (Integer j : roomQueue) {
                if (j == neighbours[i] && current != j) {
                    neighbourCount++;
                }
            }
        }

        return neighbourCount > 1;
    }

    private int[] getNeighbours(int current) {

        int upNeighbour;
        int downNeighbour;
        int leftNeighbour;
        int rightNeighbour;

        upNeighbour = current - width;
        downNeighbour = current + width;
        leftNeighbour = current - 1;
        rightNeighbour = current + 1;

        return new int[]{upNeighbour, downNeighbour, leftNeighbour, rightNeighbour};
    }

    private int[] convertQueueToRooms(Queue<Integer> roomQueue) {
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
