package com.company;

import java.util.Random;

public class RandNum {

    Random randNum;
    int seedValue;

    public RandNum(int seedValue) {
        this.seedValue = seedValue;
    }

    public Random getRandNum() {
        return randNum;
    }
}
