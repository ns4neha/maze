package uk.gov.dwp.maze;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import uk.gov.dwp.maze.exception.MazeCreationException;
import uk.gov.dwp.maze.utils.MazeUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MovementTest {
    @Test
    public final void testAvailableNextSteps() throws MazeCreationException {
        Maze mazeRight = MazeAndExplorerTest.createMaze("/src/test/resources/TestMaze4.txt");
        Visitor visitorRight = MazeUtils.createExplorer(1);
        mazeRight.permitExplorer(visitorRight);
        List<MazeUtils.Direction> availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(3);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.UP, MazeUtils.Direction.DOWN, MazeUtils.Direction.RIGHT);
    }

    @Test
    public final void testMoveRight() throws MazeCreationException {
        Maze mazeRight = MazeAndExplorerTest.createMaze("/src/test/resources/TestMaze4.txt");
        Visitor visitorRight = MazeUtils.createExplorer(1);
        mazeRight.permitExplorer(visitorRight);
        mazeRight.moveRight(visitorRight);
        List<MazeUtils.Direction> availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(4);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.values());
        mazeRight.moveRight(visitorRight);
        availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(2);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.DOWN, MazeUtils.Direction.LEFT);
    }

    @Test
    public final void testUnableToMoveFurtherRight() throws MazeCreationException {
        Maze mazeRight = MazeAndExplorerTest.createMaze("/src/test/resources/TestMaze4.txt");
        Visitor visitorRight = MazeUtils.createExplorer(1);
        mazeRight.permitExplorer(visitorRight);
        mazeRight.moveRight(visitorRight);
        List<MazeUtils.Direction> availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(4);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.values());
        mazeRight.moveRight(visitorRight);
        availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(2);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.DOWN, MazeUtils.Direction.LEFT);
        String lastEndCellIdBefore = mazeRight.getCurrentCell(visitorRight);
        mazeRight.moveRight(visitorRight);
        String lastEndCellIdAfter = mazeRight.getCurrentCell(visitorRight);
        availableStepsList = mazeRight.availableNextSteps(visitorRight);
        Assertions.assertThat(availableStepsList).hasSize(2);
        Assertions.assertThat(availableStepsList).containsOnly(MazeUtils.Direction.DOWN, MazeUtils.Direction.LEFT);
        assertThat(lastEndCellIdBefore).isEqualTo(lastEndCellIdAfter);
    }

    @Test
    public final void testStartToEnd() throws MazeCreationException {
        Maze mazeRight = MazeAndExplorerTest.createMaze("/src/test/resources/TestMaze4.txt");
        Visitor visitorRight = MazeUtils.createExplorer(1);
        mazeRight.permitExplorer(visitorRight);
        mazeRight.moveDown(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveDown(visitorRight);
        boolean reachedDestination = mazeRight.reachedDestination(visitorRight);
        assertThat(reachedDestination).isTrue();
        mazeRight.sayByeToVisitor(visitorRight);
    }

    @Test
    public final void testVisitHistory() throws MazeCreationException {
        Maze mazeRight = MazeAndExplorerTest.createMaze("/src/test/resources/TestMaze4.txt");
        Visitor visitorRight = MazeUtils.createExplorer(1);
        mazeRight.permitExplorer(visitorRight);
        mazeRight.moveDown(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveRight(visitorRight);
        mazeRight.moveDown(visitorRight);
        boolean reachedDestination = mazeRight.reachedDestination(visitorRight);
        assertThat(reachedDestination).isTrue();
        List<String> visitHistory = visitorRight.getPreviousMoves();
        assertThat(visitHistory).contains("3-2", "4-2", "4-3", "4-4", "4-5", "5-5");
        mazeRight.sayByeToVisitor(visitorRight);
    }
}
