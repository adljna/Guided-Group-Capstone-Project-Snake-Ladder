/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : C
 * Group    : XX
 * Members  :
 * 1. Student ID - Full Name
 * 2. Student ID - Full Name
 * 3. Student ID - Full Name
 * ------------------------------------------------------
 */

import javax.swing.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        SnakeAndLadder g1 = new SnakeAndLadder(100);
        SnakeAndLadderGUI game = new SnakeAndLadderGUI(g1);
    }
}