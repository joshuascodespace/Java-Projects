//Written by ONEIL853

import java.util.Scanner;
public class ComputeBMI {
    public static void main(String[] args) {
        //Instantiates the class, so that its non-static methods can be called.
        ComputeBMI calculator = new ComputeBMI();

        //Initiates a scanner so that user input can be read.
        Scanner scanner = new Scanner(System.in);

        //Asks the user for the weight, and then stores it.
        System.out.print("Enter Weight in pounds: ");
        double weight = scanner.nextDouble();

        //Asks the user to enter a different value, if they entered a negative value.
        //The process is repeated via the while loop until an appropriate value is entered.
        while (weight < 0) {
            System.out.println("Error: Negative values are not valid for weight.");
            System.out.print("Enter Weight in pounds: ");
            weight = scanner.nextDouble();
        }

        //Asks the user for the height, and then stores it.
        System.out.println("Enter Height in inches: ");
        double height = scanner.nextDouble();

        //Asks the user to enter a different value, if they entered a negative value, or 0.
        //The process is repeated via the while loop until an appropriate value is entered.
        while (height <= 0) {
            System.out.println("Error: Negative values and '0' are not valid for height ");
            System.out.println("Enter Height in pounds: ");
            height = scanner.nextDouble();
        }


        //Calls the CalcBMI method to calculate the BMI given height and weight, and stores the result.
        double bodyMassIndex = calculator.calcBMI(weight, height);

        //Prints out the result
        System.out.println("For Weight: " + weight + " and Height: " + height);
        System.out.println("The BMI is: " + bodyMassIndex);
    }

    public double calcBMI(double weight, double height) {
        //Calculates the BMI from the passed weight and height, and returns the result.
        return ((703 * weight) / (height * height));
    }
}
