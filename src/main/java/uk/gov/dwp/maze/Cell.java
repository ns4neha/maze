package uk.gov.dwp.maze;

import java.util.ArrayList;
import java.util.List;
import uk.gov.dwp.maze.utils.MazeUtils;

import static uk.gov.dwp.maze.utils.MazeUtils.Direction.*;

public class Cell implements Navigable {

    private final String cellId;

    private final boolean blocked;

    private final int x;

    private final int y;

    private Navigable left;

    private Navigable right;

    private Navigable up;

    private Navigable down;

    public Cell(String cellId, int x, int y, boolean blocked) {
        this.cellId = cellId;
        this.x = x;
        this.y = y;
        this.blocked = blocked;
    }

    @Override
    public String getVisitableUniqueID() {
        return cellId;
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Navigable getLeft() {
        return (left!=null && !left.isBlocked()) ? left : null;
    }

    @Override
    public void setLeft(Navigable left) {
        this.left = left;
    }

    @Override
    public Navigable getRight() {
        return (right!=null && !right.isBlocked()) ? right : null;
    }

    @Override
    public void setRight(Navigable right) {
        this.right = right;
    }

    @Override
    public Navigable getUp() {
        return (up!=null && !up.isBlocked()) ? up : null;
    }

    @Override
    public void setUp(Navigable up) {
        this.up = up;
    }

    @Override
    public Navigable getDown() {
        return (down!=null && !down.isBlocked()) ? down : null;
    }

    @Override
    public void setDown(Navigable down) {
        this.down = down;
    }

    @Override
    public List<MazeUtils.Direction> availableNextSteps() {
        List<MazeUtils.Direction> nextSteps = new ArrayList<>();
        if (getUp() != null) nextSteps.add(UP);
        if (getDown() != null) nextSteps.add(DOWN);
        if (getLeft() != null) nextSteps.add(LEFT);
        if (getRight() != null) nextSteps.add(RIGHT);
        return nextSteps;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Cell ");
        builder.append(" x axis position is ");
        builder.append(x);
        builder.append(" y axis position is ");
        builder.append(y);
        builder.append(" .The cell is ");
        if (isBlocked())
            builder.append(" blocked");
        else
            builder.append(" empty");
        return builder.toString();
    }
}
