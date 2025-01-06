//Written by ONEIL853
public class WindChill {
    public static void main(String[] args) {

        //Declares a variable to hold the answer:
        double windChill;

        //Obtains values given to the prompt for the calculations, and stores them in variables.
        double degreesFahrenheit = Double.valueOf(args[0]);
        double windSpeed = Double.valueOf(args[1]);

        //Makes sure that the quantities entered are valid before running the program.
        if (windSpeed >= 0) {

            //Executes parts of the calculation at a time and stores intermediate results
            //So that the computation doesn't get long and go off the page.
            double term1 = 0.6215 * degreesFahrenheit;
            double term2 = 35.75 * Math.pow(windSpeed, 0.16);
            double term3 = 0.4275 * degreesFahrenheit * Math.pow(windSpeed, 0.16);

            //Final calculation.
            windChill = 35.74 + term1 - term2 + term3;

            //The below output statement is copied from Dr. Challou's homework instructions:
            //It outputs the data requested, including degrees Fahrenheit and Wind speed.
            //It does so on three lines so that the code doesn't trail off the screen.
            System.out.print("The wind-chill at Temperature: " + degreesFahrenheit + " Fahrenheit ");
            System.out.print("and Wind Speed: " + windSpeed + " mph ");
            System.out.print("is: " + windChill + " degrees Fahrenheit");
        }
        else {
            //Displays an error if the user tried to enter a negative value for speed.
            System.out.print("Error: Cannot compute calculation. ");
            System.out.println("Speed cannot be a negative quantity. Please try again.");
        }
    }

}
