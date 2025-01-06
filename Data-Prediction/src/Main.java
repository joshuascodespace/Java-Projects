//ONEIL853
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
/**
 * <h1>HW5</h1>
 * The HW5 program computes the probability of user-input wind speed at a
 * particular location based on input file data
 * @author  Joshua O'Neill
 * @version 1.0
 * @since   2023-11-29
 */
public class Main {
    /**
     * This is the main method which calls all the methods to perform the program's tasks.
     * @param args Unused.
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     */
    public static void main(String[] args) throws IOException {
        //List to store wind values read from file.
        double[] windValues = new double[9000];

        //Holds the interval the user inputs
        int userDefinedInterval;

        //Holds histogram of bins the program uses
        Bin[] histogram = new Bin[200];

        //Scanners to read input
        Scanner myStringScanner = new Scanner(System.in);
        Scanner myDoubleScanner = new Scanner(System.in);

        //Holds the amount of total values read from scanner.
        int numWindValues;


        userDefinedInterval = getIntervalData(myDoubleScanner);

        //The method returns the total values it stored in the windValues list.
        numWindValues = getFileData(myStringScanner,windValues);

        histogram = populateBins(userDefinedInterval,windValues,numWindValues);

        getCumulativeProbability(histogram,numWindValues);

        //Outputs the histogram to a file called "cumProbability.txt"
        saveCumulativeProbability(histogram);

        //Gets the coefficient that can be used to craft a graph that approximates the data gathered.
        double k = ordinaryLeastSquares(histogram);

        //Asks the user what they want the program to do with the data it gathered.
        promptUserForTask(myStringScanner,myDoubleScanner,k);
    }

    /**
     * This is the getIntervalData method which gets a "Bin size" value from the user.
     * @param myDoubleScanner Used to read a double value from input.
     * @return int This allows the main method to work with the value obtained from the user through this method.
     */
    public static int getIntervalData(Scanner myDoubleScanner) {
        int inputInterval;

        //While loops repeats until input is valid
        while (true) {
            //Asks the user for the bin size, then tries to convert the input to a double
            //and notifies the user if an error occurs in this process
            System.out.print("Please enter, between 50 and 100, the number of intervals: ");
            String userInput = myDoubleScanner.next();
            try {
                inputInterval = Integer.parseInt(userInput);
            }
            catch (NumberFormatException badInput) {
                System.out.println("Error: The value you entered was not an integer. Please try again.");
                continue;
            }
            //Checks if the interval is in valid range.
            if (inputInterval >= 50 && inputInterval <= 100) {
                //The value gathered is valid, so the while loop and method ends
                break;
            } else {
                System.out.println("Error: The value entered is not in the specified range");
                System.out.println("Please try again, and recall only values between 50 and 100 are accepted");
            }
        }
        System.out.println("Thank you, ");
        return inputInterval;
    }

    /**
     * This is the getFileData method which gets the name of a file from a user and stores
     * values of maximum wind speed from the file in a list.
     * @param myStringScanner Used to read a file name from input.
     * @param windValues Stores values of maximum wind speed read from a file.
     * @return int This tells the main program how many values were read from the file.
     */
    public static int getFileData(Scanner myStringScanner, double[] windValues) {
        //While loop repeats "try" statement until valid file data is provided
        while (true) {
            try {
                //Gets the file the user wants the program to retrieve data from.
                System.out.print("Now please enter the name of the file with the data you would like analyzed: ");
                String fileName = myStringScanner.nextLine();
                File dataFile = new File(fileName);
                Scanner fileScanner = new Scanner(dataFile);

                //Skips reading the first 7 lines of the file, since those lines don't contain valid input
                for(int i = 0; i < 7; i++) {
                    fileScanner.nextLine();
                }

                //Counts how much data is gathered from the file.
                int counter = 0;

                //Reads all lines of the file
                while (fileScanner.hasNext()) {
                    //Gets the 8th column from each file, then attempts to convert it to a double value.
                    //If input is invalid, the user is informed of this, and the program continues.
                    String fileLine = fileScanner.nextLine();
                    String[] fileLineEntries = fileLine.split(",");
                    try {
                        windValues[counter] = Double.parseDouble(fileLineEntries[5]);
                        counter++;
                    }
                    catch (NumberFormatException badInput) {
                        System.out.println(fileLineEntries[7] + " is not a valid input for this program");
                    }
                }
                return counter;
            } catch (IOException badFile) {
                System.out.println("The file name you entered is not valid. Please try again");
            }
        }
    }

    /**
     * This is the populateBins method which creates a histogram of bins
     * and sets their size to be of user-specified length, and
     * their count hold however much file data falls in their intervals.
     * @param interval Used so that the method can set the histogram size and bin intervals accordingly.
     * @param windValues Used to access previously obtained file data.
     * @param numData Used so that the method knows how much data there is for it to sort.
     * @return Bin[] This gives the rest of the program a histogram to work with.
     */
    public static Bin[] populateBins(int interval, double[] windValues, int numData) {
        //Finds the histogram's size based on its bins
        int histogramSize = 10000/interval + 1;

        Bin[] myHistogram = new Bin[histogramSize];

        //Initializes each histogram bin with a new bin with interval data based on the user--
        //input interval and the bin's location in the histogram, and initializes other variables to 0.
        for (int i = 0; i < histogramSize; i++) {
            myHistogram[i] = new Bin(i * interval, 0, 0);
        }

        //For each value retrieved from the file, the loop checks whether that value is inside the
        //interval of each bin in the histogram until it finds a match, then increments the bin's
        //field that tracks how much data falls within that bin's interval.
        //This algorithm was inspired by Dr. Challou's HW5 Instructions
        for (int i = 0; i < numData; i++) {
            for (int j = 0; j < histogramSize-1; j++) {
                if (((windValues[i] * windValues[i]) >= myHistogram[j].getInterval()) &&
                        ((windValues[i] * windValues[i]) < myHistogram[j + 1].getInterval())) {
                    myHistogram[j].setCount(myHistogram[j].getCount() + 1);
                    break;
                }
            }
        }

        //Returns the histogram created.
        return myHistogram;
    }

    /**
     * This is the getCumulativeProbability method which finds the cumulative probability associated with
     * each bin, based on the amount of data that falls into each bin and the total amount of data.
     * @param histogram Used so that the method can access each Bin's data and set their cumulative probabilities.
     * @param valueCount Unused
     * @return Nothing.
     */
    public static void getCumulativeProbability(Bin[] histogram, int valueCount) {
        //The cumulative probability starts off at 1.0, since the start of the histogram contains all
        //probabilities added up, which must equal 1.0.
        double cumulativeProbability = 1.0;

        //Gets the probability for each bin by dividing the bin's count by the total file data gathered,
        // then subtracts the probability from the cumulative, since it no longer is a part of it.
        //These values are then stored in each respective Bin's probability field.
        //This algorithm is inspired by Dr. Challou's HW5 Instructions.
        for (int j = 0; j < histogram.length; j++) {
            cumulativeProbability = cumulativeProbability - (double) histogram[j].getCount()/ (double) valueCount;
            if (cumulativeProbability < 0.0)
                break;
            histogram[j].setCumProbability(cumulativeProbability);
        }
    }

    /**
     * This is the saveCumulativeProbability method which stores the histogram created
     * in a file called "cumProbability.txt".
     * @param histogram Allows the method to access the histogram it needs to store.
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     */
    public static void saveCumulativeProbability(Bin[] histogram) throws IOException {
        //Sets up a Buffered Writer on a new file called "cumProbability.txt"
        File outputFile = new File("cumProbability.txt");
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //Writes out the histogram's bins' cumulative probabilities to a file.
        bw.write(0 + " " + 1.0);
        bw.newLine();
        for(int i = 0; i < histogram.length; i++) {
            bw.write((i+1) + " " + histogram[i].getCumProbability());
            bw.newLine();
        }
        bw.close();
    }

    /**
     * This is the ordinaryLeastSquares method which performs ordinary least squares regression
     * to find a value that can be used to find a probability function that best approximates
     * the data.
     * @param histogram Used to access the data used to find the value mentioned.
     * @return double Allows the rest of the program to access the value that forms the probability function.
     */
    public static double ordinaryLeastSquares(Bin[] histogram) {
        //Performs the ordinary least squares algorithms. The algorithm was inspired by
        //Dr. Challou's HW5 instructions
        double num = 0.0;
        double den = 0.0;
        for (int j = 0; j < 200; j++) {
            if (histogram[j].getCumProbability() <= 0.01)
                break;
            num = num - Math.log(histogram[j].getCumProbability());
            den = den + histogram[j+1].getInterval();
        }
        return num/den;
    }

    /**
     * This is the promptUserForTask method which asks the user which probability they want
     * the program to find, then uses the formula that approximates the gathered data to
     * obtain the probability the user requested.
     * @param myStringScanner Used to read the user's request from input.
     * @param myDoubleScanner Used to read the probability value the user wants to be found.
     * @param regressionCoefficient Used to create a formula that calculates the probability the user requested.
     * @return Nothing.
     */
    public static void promptUserForTask(Scanner myStringScanner,Scanner myDoubleScanner, double regressionCoefficient) {
        //The program asks the user what they want the program to do with the data collected from a file,
        // until the user requests the program to stop.
        while(true) {
            System.out.print("Enter ‘less’, ‘greaterEq’, or ‘q’ to quit: ");
            String taskRequest = myStringScanner.nextLine();
            if (taskRequest.equals("q")) {
                System.out.println("Okay, have a nice day!");
                myStringScanner.close();
                myDoubleScanner.close();
                break;
            }
            //Checks if input is valid, and re-prompts the user for their request if it is not.
            if (taskRequest.equals("less") || taskRequest.equals("greaterEq")) {
                //Asks the user for wind speed, and attempts to convert it to a double.
                //If their input does not allow for this conversion, the user is re-prompted for info.
                System.out.print("Enter wind speed: ");
                try {
                    String userInput = myDoubleScanner.next();
                    double windSpeed = Double.parseDouble(userInput);

                    //Calculates the probability the user requested using the formula generated by
                    //The ordinary least squares algorithm, then informs the user of the results.
                    double probability = Math.exp(-1 * regressionCoefficient * windSpeed * windSpeed);
                    if (taskRequest.equals("greaterEq")) {
                        System.out.println("Probability wind speed >= " + windSpeed + " is " + probability);
                    } else {
                        //This statement makes use of the fact that P(X>=x)=1-P(X<x)
                        System.out.println("Probability wind speed < " + windSpeed + " is " + (1-probability));
                    }
                }
                catch(NumberFormatException badInput) {
                    System.out.println("Umm...");
                    System.out.println("The data you entered wasn't a number as expected.");
                    System.out.println("Please do better next time.");
                }
            } else {
                System.out.println("Umm... ");
                System.out.println(taskRequest + " is not a valid request. ");
                System.out.println("Please only enter one of the prompts requested.");
            }
        }
    }
}


//Left to do:
//Make JavaFX graph visualization.