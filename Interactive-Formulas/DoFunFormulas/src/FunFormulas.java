//Written by ONEIL853

import java.util.Scanner;
public class FunFormulas {
    public void sd() {
        //Calls the getInput function to get the diameter of the storm from the user.
        double stormDiameter = getInput("sd");

        //Performs the calculation sqrt(stormDiameter^3/216) and stores it
        double stormLength = Math.sqrt(Math.pow(stormDiameter, 3)/216);

        //Prints the result of the calculation
        //The words printed here are taken from Dr. Challou's homework instructions.
        System.out.println("The storm will last: " + stormLength + " hours");
    }

    public void ls() {
        //Calls the getInput function to get the amount of time between lighting and the storm from the user.
        double timeInterval = getInput("ls");

        //Performs the calculation 1100 * the time interval, and stores it
        double lightningDistance = 1100 * timeInterval;

        //Prints the result of the calculation
        System.out.println("The lightning is " + lightningDistance + " feet away from you");
    }

    public void wi() {
        //Calls the getInput function to get the edge of the ice cube.
        double iceCubeEdge = getInput("wi");

        //Performs the calculation 0.33 * the length of the cube's edge to the third power, and stores it
        double iceCubeWeight = 0.33 * Math.pow(iceCubeEdge,3);

        //Prints the result of the calculation.
        System.out.println("The ice cube weights " + iceCubeWeight + " pounds");
    }

    public void dt() {
        //Calls the getInput function to get the amount of time travelled for.
        double speed = getInput("dtSpeed");

        //Calls the getInput function to get the amount of time travelled for.
        double timeTravelled = getInput("dtTime");

        //Performs the calculation 0.33 * the length of the cube's edge to the third power, and stores it
        double distanceTravelled = timeTravelled * speed;

        //Prints the result of the calculation.
        //The words printed here are taken from Dr. Challou's homework instructions.
        System.out.println("Distance travelled is: " + distanceTravelled + " miles");
    }

    public void sa() {
        //Calls the getInput function to get the weight.
        double weight = getInput("saWeight");

        //Converts pounds into kilograms
        weight *= 0.4536;

        //Calls the getInput function to get the height.
        double height = getInput("saHeight");

        //Converts inches into centimeters
        height *= 2.54;

        //Performs the calculation that the square root of the weight times height
        // all divided by 60, and stores it
        double bodySurfaceArea = Math.sqrt(weight * height)/60.0;

        //Prints the result of the calculation.
        System.out.println("The Body Surface Area is " + bodySurfaceArea + " cm^2");
    }


    public double getInput(String promptStr) {
        //Allows the computer to read user prompt.
        Scanner scanner = new Scanner(System.in);

        if (promptStr.equals("sd")) {
                //Asks the user for the diameter of a storm, if that is the equation they requested
                // Note that this input message is Dr. Challou's
            System.out.print("Enter diameter of storm in miles: ");
        }
        if (promptStr.equals("ls")) {
                //Asks the user for the time interval between lightning and thunder
                // if that is the equation they requested
                //Note that this input message is Dr. Challou's
            System.out.print("Enter number of seconds from the time you saw the lightning flash ");
            System.out.print("until you hear the thunder: ");
        }
        if (promptStr.equals("wi")) {
                //Asks the user for the size of the ice cube's edge
                //if that is the equation they requested.
            System.out.print("Enter the length, in inches, of the ice cube's edge: ");
        }
        if (promptStr.equals("dtSpeed")) {
            //Asks the user for the time spent travelling
            //if that is the equation they requested.
            System.out.print("Enter the speed in miles per hour: ");
        }
        if (promptStr.equals("dtTime")) {
            //Asks the user for the time spent travelling
            //if that is the equation they requested.
            System.out.print("Enter the amount of time travelled for: ");
        }
        if (promptStr.equals("saWeight")) {
            //Asks the user for the weight
            //if that is the equation they requested.
            System.out.print("Enter the weight of the body in pounds: ");
        }
        if (promptStr.equals("saHeight")) {
            //Asks the user for the height.
            //if that is the equation they requested.
            System.out.print("Enter the height of the body in inches: ");
        }


        //Stores the user's answer
        double input = scanner.nextDouble();

            //Prints an error message if the user's input is not valid
        if (input < 0) {
            System.out.println("ERROR: please enter a non-negative number!!!");
            return getInput(promptStr);
        }

        //Closes the scanner.
        scanner.close();

        //Returns the user's input, since the computer successfully checked its validity.
        return input;
    }
}
