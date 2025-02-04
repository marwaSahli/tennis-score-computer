package org.tennis.computer;

public class Application {
    public String getGreeting() {
        return "Hello from tennis score computer!";
    }

    public static void main(String[] args) {
        System.out.println(new Application().getGreeting());
    }
}
