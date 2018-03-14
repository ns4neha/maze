package uk.gov.dwp.maze;

import java.util.ArrayList;
import java.util.List;

public class Explorer implements Visitor {

    private final int explorerId;
    private final List<String> movements;
    private String currentCellId;

    public Explorer(final int explorerId) {
        super();
        movements = new ArrayList<>();
        this.explorerId = explorerId;
    }

    @Override
    public boolean visit(Navigable cell) {
        boolean visited = false;
        if (!cell.isBlocked()) {
            currentCellId = cell.getVisitableUniqueID();
            movements.add(currentCellId);
            visited = true;
        }
        return visited;
    }

    @Override
    public String getCurrentCell() {
        return currentCellId;
    }

    @Override
    public List<String> getPreviousMoves() {
        return movements;
    }

    @Override
    public int getExplorerId() {
        return explorerId;
    }

    @Override
    public String toString() {
        return "Explorer ID " + getExplorerId();
    }
}
