package uk.gov.dwp.maze;

import java.util.List;
import uk.gov.dwp.maze.utils.MazeUtils;

public interface Navigable {
    boolean isBlocked();

    String getVisitableUniqueID();

    int getY();

    int getX();

    Navigable getLeft();

    void setLeft(Navigable left);

    Navigable getRight();

    void setRight(Navigable right);

    Navigable getUp();

    void setUp(Navigable up);

    Navigable getDown();

    void setDown(Navigable down);

    List<MazeUtils.Direction> availableNextSteps();
}
