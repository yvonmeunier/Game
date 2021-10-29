package com.company;

import java.util.Queue;
import java.util.Random;

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

        return generatedFloor;
    }

    private void init() {
        rooms = new int[width * height];
        numberOfRooms = (int) (rnd.nextInt(2) + 5 + level * 2.6);
    }

    private void generateFloorQueue() {
        roomQueue.add(35);
        while (roomQueue.size() < numberOfRooms) {
            fillRoomQueue();
        }
    }

    private void fillRoomQueue() {

        int[] neighbours;


        for (Integer current : roomQueue) {

            neighbours = getNeighbours(current);

            for (int i = 0; i < neighbours.length; i++) {

                if (isOutOfBound(getXFromIndex(neighbours[i]), getYFromIndex(neighbours[i]))) {
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
            }

        }

    }

    private int getIndex(int x, int y) {
        return x + width * y;
    }

    private boolean isOutOfBound(int x, int y) {
        return x > 0 || y > 0 || x < width || y < height;
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

        for (int i = 0;i < neighbours.length; i++) {
            if (neighbours[i] != 0) {
                neighbourCount++;
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

}
