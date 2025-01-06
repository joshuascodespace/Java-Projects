public class DoFunFormulas {
    public static void main(String[] args){

        //Checks if the user input one and only one argument.
        if (args.length == 1) {
            //Gets the input from terminal and stores it.
            String userInput = args[0].toLowerCase();
            FunFormulas formulaKey = new FunFormulas();

            //if-else-if structure checks which equation was requested and
            //Then calls the FunFormulas class to execute that specific formula.
            if (userInput.equals("sd")) {
                formulaKey.sd();
            } else if (userInput.equals("ls")) {
                formulaKey.ls();
            } else if (userInput.equals("wi")) {
                formulaKey.wi();
            } else if (userInput.equals("dt")) {
                formulaKey.dt();
            } else if (userInput.equals("sa")) {
                formulaKey.sa();
            } else {
                //The program realizes from the if-else-if structure that the
                //user did not request one of the formulas that the program excepts,
                //and so prints an error, then requests that they enter a valid formula.
                errorMessage("Invalid String", userInput);
            }
        }
        else {
            //Calls an error message to be printed to prompt the user to input a
            //correct number of arguments
            errorMessage("wrongNumberOfArgs", "null");
        }
    }

    public static void errorMessage(String errorType, String argument) {
        //The first argument allows the error printing function to now which error to print
        //and the second argument allows the program to know what the user input.

        //The following messages are copied from Dr. Challou's Programming Assignment one description.
        //These if statements allow for slightly different errors to print, depending on the cause.
        if (errorType.equals("wrongNumberOfArgs")) {
            //If the user
            System.out.println("ERROR: 1 command line argument required: Two letter name of function to");
            System.out.println("compute. Type:");
        }
        if (errorType.equals("Invalid String")) {
            System.out.println("ERROR: Formula " + argument + " is not recognized, Type: ");
        }


        //This tells the user what they are supposed to do, which are left outside
        //the if-statements because they are the same regardless of the error type.
        System.out.println("sd compute storm distance");
        System.out.println("ls to compute distance to lightning strike");
        System.out.println("wi to compute weight of ice cube");
        System.out.println("dt to computer distance traveled");
        System.out.println("sa to compute skin area");
    }
}
