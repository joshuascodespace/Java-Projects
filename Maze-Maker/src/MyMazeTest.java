import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMazeTest {

    @Test
    void makeMaze() {
        MyMaze testMaze = MyMaze.makeMaze(5,6);
        assertNotNull(testMaze);
    }

    @Test
    void printMaze() {
        MyMaze testMaze = MyMaze.makeMaze(5,6);
        testMaze.solveMaze();
        testMaze.printMaze(false);
        testMaze.printMaze(true);
    }

    @Test
    void solveMaze() {
        MyMaze testMaze1 = MyMaze.makeMaze(5,6);
        testMaze1.solveMaze();
        testMaze1.printMaze(true);
        MyMaze testMaze2 = MyMaze.makeMaze(1,6);
        testMaze2.solveMaze();
        MyMaze testMaze3 = MyMaze.makeMaze(6,1);
        testMaze3.solveMaze();
    }

    @Test
    void determinePrintStatus() {
        MyMaze testMaze = MyMaze.makeMaze(5,6);
        assertTrue(testMaze.determinePrintStatus(true,true));
        assertFalse(testMaze.determinePrintStatus(false,true));
        assertFalse(testMaze.determinePrintStatus(true,false));
        assertFalse(testMaze.determinePrintStatus(false,false));
    }

    @Test
    void determineSolutionViability() {
        MyMaze testMaze = MyMaze.makeMaze(5,6);
        assertFalse(testMaze.determineSolutionViability(true,true));
        assertFalse(testMaze.determineSolutionViability(false,true));
        assertFalse(testMaze.determineSolutionViability(true,false));
        assertTrue(testMaze.determineSolutionViability(false,false));
    }
}