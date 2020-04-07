package me.wilsonhu.csce247.finalproject;

import java.io.IOException;

public class Application {
    private static Driver driver = new Driver();

    public static void main(String[] args) throws IOException {
        System.out.println(String.format("%.2f", 2.12452D));
        //driver.printVenues();
        //driver.start();
    }

    public static Driver getDriver() {
        return driver;
    }
}
