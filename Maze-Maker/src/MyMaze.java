// Names: Joshua O'Neill
// x500s: ONEIL853

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyMaze{
    private Cell[][] maze;

    public MyMaze() {}

    public MyMaze(int rows, int cols) {
        //Creates an array of cells to be as big as specified by the constructor.
        //The loop fills every element of this array with default cells.
        maze = new Cell[rows][cols];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell();
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols) {
        Random rand = new Random();
        //Initializes a maze object, such that it holds an array of cells that corresponds with input parameters.
        MyMaze myMaze = new MyMaze(rows, cols);
        //Creates the stack and adds the start of the maze's location to it, and marks this location as visited.
        Stack<int[]> pathFinder = new Stack<int[]>();
        int[] arrayToAdd = {0,0};
        pathFinder.add(arrayToAdd);
        myMaze.maze[0][0].setVisited(true);

        //Loops until stack is empty
        while(!(pathFinder.empty())) {
            //Keeps track of the most recent cell index held by the stack for easy reference.
            int currentColumn = pathFinder.peek()[1];
            int currentRow = pathFinder.peek()[0];

            //Keeps track of which neighbors have been visited, so that if all are,
            //The program can pop the most recent index held by the stack per the algorithm.
            boolean rightNeighborVisited = true;
            boolean leftNeighborVisited = true;
            boolean upperNeighborVisited = true;
            boolean lowerNeighborVisited = true;

            //Checks to see which neighbors have been visited
            //If statements are there so that neighbors outside the maze's bounds do not get checked.
            if (currentColumn != 0) {
                leftNeighborVisited = myMaze.maze[currentRow][currentColumn - 1].getVisited();
            }
            if (currentRow != 0) {
                upperNeighborVisited = myMaze.maze[currentRow - 1][currentColumn].getVisited();
            }
            if (currentColumn != cols - 1) {
                rightNeighborVisited = myMaze.maze[currentRow][currentColumn + 1].getVisited();
            }
            if (currentRow != rows - 1) {
                lowerNeighborVisited = myMaze.maze[currentRow + 1][currentColumn].getVisited();
            }

            //Checks if all neighbors have been visited.
            if (!(leftNeighborVisited && upperNeighborVisited && rightNeighborVisited && lowerNeighborVisited)) {
                //Creates a random number 1 through 4, and decides the next direction to go based on this.
                int randomInstruction = rand.nextInt(5);

                //In addition to the random number instructions, the if statement checks if the
                //neighbor is in bounds
                if (randomInstruction == 1 && currentColumn != 0) {
                    if (!(leftNeighborVisited)) {
                        //Sets neighbor as visited, removes walls, then adds neighbor to stack
                        myMaze.maze[currentRow][currentColumn - 1].setVisited(true);
                        myMaze.maze[currentRow][currentColumn - 1].setRight(false);
                        int[] newArrayToAdd = {currentRow, currentColumn - 1};
                        pathFinder.push(newArrayToAdd);
                    }
                }
                if (randomInstruction == 2 && currentRow != 0) {
                    if (!(upperNeighborVisited)) {
                        //Sets neighbor as visited, removes walls, then adds neighbor to stack
                        myMaze.maze[currentRow - 1][currentColumn].setVisited(true);
                        myMaze.maze[currentRow - 1][currentColumn].setBottom(false);
                        int[] newArrayToAdd = {currentRow - 1, currentColumn};
                        pathFinder.push(newArrayToAdd);
                    }
                }
                if (randomInstruction == 3 && currentColumn != cols-1) {
                    if (!(rightNeighborVisited)) {
                        //Sets neighbor as visited, removes walls, then adds neighbor to stack
                        myMaze.maze[currentRow][currentColumn + 1].setVisited(true);
                        myMaze.maze[currentRow][currentColumn].setRight(false);
                        int[] newArrayToAdd = {currentRow, currentColumn + 1};
                        pathFinder.push(newArrayToAdd);
                    }
                }
                if (randomInstruction == 4 && currentRow != rows-1) {
                    if (!(lowerNeighborVisited)) {
                        //Sets neighbor as visited, removes walls, then adds neighbor to stack
                        myMaze.maze[currentRow + 1][currentColumn].setVisited(true);
                        myMaze.maze[currentRow][currentColumn].setBottom(false);
                        int[] newArrayToAdd = {currentRow + 1, currentColumn};
                        pathFinder.push(newArrayToAdd);
                    }
                }
            } else {
                pathFinder.pop();
            }
        }
        //Resets the visited attribute of each cell to false.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                myMaze.maze[i][j].setVisited(false);
            }
        }

        //Removes the wall at the end of the maze to mark the end.
        myMaze.maze[rows-1][cols-1].setRight(false);

        return myMaze;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        //Assigns "|---|" to the print output string outside of loop to avoid nullPointerException
        String toPrint = "|---";

        //Creates border of top row of maze manually, since it is not marked by any of the cells.
        for (int i = 1; i < maze[0].length; i++) {
            toPrint += "|---";
        }
        //Adds left border before newline, since it was missed by loop.
        //Space after newLine represents the maze's start
        toPrint += "|\n ";


        for (int i = 0; i < maze.length; i++) {
            //Prints Left border everywhere after each new line, except at maze's start
            if (i != 0) {
                toPrint += "|";
            }

            //Loop prints middle-parts of each cell, including left/right boundaries and visited markers.
            for (int j = 0; j < maze[0].length; j++) {

                //If the user requests visited cells to be marked and a cell is visited, it gets marked.
                //Otherwise, spaces are used to fill in empty space.
                if (determinePrintStatus(maze[i][j].getVisited(),path)) {
                    toPrint += " * ";
                } else {
                    toPrint += "   ";
                }

                //This marks left/right boundaries between each cell based on their existence
                if (maze[i][j].getRight()) {
                    toPrint += "|";
                } else {
                    toPrint += " ";
                }
            }

            //Creates new line and marks implicit left boundary.
            toPrint += "\n|";

            //Prints the top/bottom boundaries where relevant.
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getBottom()) {
                    toPrint += "---";
                } else {
                    toPrint += "   ";
                }
                toPrint += "|";
            }
            toPrint += "\n";
        }

        //Prints the String containing the maze.
        System.out.println(toPrint);
    }

    public boolean determinePrintStatus(boolean pathTraveled, boolean userPreference) {
        if (pathTraveled && userPreference) {
            return true;
        } else {
            return false;
        }
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        //Creates queue to explore possible solutions,
        // adding the start of the maze as the start of said exploration
        Queue<int[]> solutionFinder = new LinkedList<int[]>();
        int[] arrayToAdd = {0,0};
        solutionFinder.add(arrayToAdd);


        while(true) {

            //Looks at the current cell on the queue and marks it visited and records it for reference.
            int[] currentCellIndex = solutionFinder.remove();
            Cell currentCell = maze[currentCellIndex[0]][currentCellIndex[1]];
            currentCell.setVisited(true);

            //Ensures that the neighbor exists before attempting to add it to the queue
            if (!(currentCellIndex[1] == maze[0].length-1)) {
                //If the right cell is not visited and no wall blocks the path to it,
                //The cell is added to the queue
                if (determineSolutionViability(maze[currentCellIndex[0]][currentCellIndex[1]+1].getVisited(),currentCell.getRight())) {
                    int[] newArrayToAdd = {currentCellIndex[0],currentCellIndex[1]+1};
                    solutionFinder.add(newArrayToAdd);
                    //If the cell added is the last cells, the maze has been solved, so the method finishes
                    if (newArrayToAdd[0] == maze.length-1 && newArrayToAdd[1] == maze[0].length-1) {
                        maze[maze.length-1][maze[0].length-1].setVisited(true);
                        break;
                    }
                }
            }

            //Ensures that the neighbor exists before attempting to add it to the queue
            if (!(currentCellIndex[0] == maze.length-1)) {
                //If the lower cell is not visited and no wall blocks the path to it,
                //The cell is added to the queue
                if (determineSolutionViability(maze[currentCellIndex[0]+1][currentCellIndex[1]].getVisited(),currentCell.getBottom())) {
                    int[] newArrayToAdd = {currentCellIndex[0]+1,currentCellIndex[1]};
                    solutionFinder.add(newArrayToAdd);
                    //If the cell added is the last cells, the maze has been solved, so the method finishes
                    if (newArrayToAdd[0] == maze.length-1 && newArrayToAdd[1] == maze[0].length-1) {
                        maze[maze.length-1][maze[0].length-1].setVisited(true);
                        break;
                    }
                }
            }

            //Ensures that the neighbor exists before attempting to add it to the queue
            if (!(currentCellIndex[0] == 0)) {
                Cell upperCell = maze[currentCellIndex[0]-1][currentCellIndex[1]];
                //If the upper cell is not visited and no wall blocks the path to it,
                //The cell is added to the queue
                if (determineSolutionViability(upperCell.getVisited(),upperCell.getBottom())) {
                    int[] newArrayToAdd = {currentCellIndex[0]-1,currentCellIndex[1]};
                    solutionFinder.add(newArrayToAdd);
                }
            }
            //Ensures that the neighbor exists before attempting to add it to the queue
            if (!(currentCellIndex[1] == 0)) {
                Cell leftCell = maze[currentCellIndex[0]][currentCellIndex[1]-1];
                //If the left cell is not visited and no wall blocks the path to it,
                //The cell is added to the queue
                if (determineSolutionViability(leftCell.getVisited(),leftCell.getRight())) {
                    int[] newArrayToAdd = {currentCellIndex[0],currentCellIndex[1]-1};
                    solutionFinder.add(newArrayToAdd);
                }
            }
        }
    }

    public boolean determineSolutionViability(boolean neighborVisited,boolean wall) {
        if (!(neighborVisited) && !(wall)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
        /* Any testing can be put in this main function */
        MyMaze test = MyMaze.makeMaze(5,20);
        test.solveMaze();
        test.printMaze(true);
    }
}
