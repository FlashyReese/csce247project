package me.wilsonhu.csce247.finalproject;

import java.io.IOException;

public class Application {
    private static Driver driver = new Driver();

    public static void main(String[] args) throws IOException {
        driver.start();
    }

    public static Driver getDriver() {
        return driver;
    }
}
