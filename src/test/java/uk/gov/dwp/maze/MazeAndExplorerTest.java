package uk.gov.dwp.maze;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import uk.gov.dwp.maze.exception.MazeCreationException;
import uk.gov.dwp.maze.utils.MazeUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MazeAndExplorerTest {
    static Maze createMaze(String mazeFileStr) throws MazeCreationException {
        File file = new File(new File("").getAbsolutePath() + mazeFileStr);
        return MazeUtils.createMaze(file);
    }

    @Test
    public final void testCreateMazeShouldSuccessfullyCreateMaze() throws MazeCreationException {
        Maze maze = createMaze("/src/test/resources/TestMaze1.txt");
        assertThat(maze).isNotNull();
    }

    @Test
    public final void testCreateMazeShouldSuccessfullyCreateMazeWithCells() throws MazeCreationException {
        Maze maze = createMaze("/src/test/resources/TestMaze2.txt");
        assertThat(maze.getCell("2-10")).isNotNull();
    }

    @Test
    public final void testNegativeCreateMazeWithCellMap() throws MazeCreationException {
        Maze maze = createMaze("/src/test/resources/TestMaze2.txt");
        assertThat(maze.getCell("200-1000")).isNull();
    }

    @Test
    public final void testMazeWithNoStartShouldReturnError() {
        try {
            createMaze("/src/test/resources/TestIncorrectMaze5.txt");
            fail("The test case failed for incorrect file");
        } catch (MazeCreationException e) {
            assertThat(e.getMessage()).isEqualTo("Starting position is not mentioned in the Maze");
        }
    }

    @Test
    public final void testMazeWithNoEndShouldReturnError() {
        try {
            createMaze("/src/test/resources/TestIncorrectMaze6.txt");
            fail("The test case failed for incorrect file");
        } catch (MazeCreationException e) {
            assertThat(e.getMessage()).isEqualTo("Finishing position is not mentioned in the Maze");
        }
    }

    @Test
    public final void testCreateMazeSetWithStartEndCells() throws MazeCreationException {
        Maze maze = createMaze("/src/test/resources/TestMaze2.txt");
        assertThat(maze.getStartCell()).isNotNull();
        assertThat(maze.getEndCell()).isNotNull();
        assertThat(maze.getStartCell().getVisitableUniqueID()).isEqualTo("3-4");
        assertThat(maze.getEndCell().getVisitableUniqueID()).isEqualTo("6-48");
    }

    @Test
    public final void testCreateCells() throws MazeCreationException {
        Maze maze = createMaze("/src/main/resources/Maze1.txt");
        Map<String, Navigable> cellMap = maze.getMazeMap();
        assertThat(cellMap.size()).isEqualTo(225);
        assertThat(cellMap.get("1-1").isBlocked()).isEqualTo(true);
        assertThat(cellMap.get("2-2").isBlocked()).isEqualTo(false);
        assertThat(cellMap.get("4-10").isBlocked()).isEqualTo(false);
        assertThat(cellMap.get("4-13").isBlocked()).isEqualTo(true);
    }

    @Test
    public final void testStartCellStatusForExplorer() throws MazeCreationException {
        Maze maze = createMaze("/src/main/resources/Maze1.txt");
        Visitor visitor = MazeUtils.createExplorer(2);
        maze.permitExplorer(visitor);
        String cellId = visitor.getCurrentCell();
        List<String> cellVisitHistory = visitor.getPreviousMoves();
        assertThat(maze.getStartCell().getVisitableUniqueID()).isEqualTo(cellId);
        assertThat(cellVisitHistory).hasSize(1);
    }


    @Test

    public final void testCreateCells_Spaces_AndWalls() throws MazeCreationException {

        Maze maze = createMaze("/src/main/resources/Maze1.txt");

        Map<String, Navigable> cellMap = maze.getMazeMap();

        assertThat(cellMap.size()).isEqualTo(225);

        assertThat(maze.getCountOfSpaces()).isEqualTo(76);

        assertThat(maze.getCountOfWalls()).isEqualTo(149);

        assertThat(cellMap.get("1-1").isBlocked()).isEqualTo(true);

        assertThat(cellMap.get("2-2").isBlocked()).isEqualTo(false);

        assertThat(cellMap.get("4-10").isBlocked()).isEqualTo(false);

        assertThat(cellMap.get("4-13").isBlocked()).isEqualTo(true);

    }
}
