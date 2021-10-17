package de.hdm_stuttgart.mi;

/**
 * Wrapper class to fix startup from IDE and JAR/package (see
 * https://stackoverflow.com/questions/52144931/how-to-add-javafx-runtime-to-eclipse-in-java-11)
 */
public class Launcher {
    /**
     * Start the main app
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.main(args);
    }

}
