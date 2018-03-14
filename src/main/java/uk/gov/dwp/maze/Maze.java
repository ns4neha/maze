package uk.gov.dwp.maze;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import uk.gov.dwp.maze.utils.MazeUtils;

public class Maze {
    private static final int MAX_EXPLORERS = 1;
    private final Map<String, Navigable> mazeMap;
    private final Set<Visitor> explorerRegistry;
    private final Navigable startCell;
    private final Navigable endCell;

    public Maze(final Map<String, Navigable> mazeMap, Navigable startCell, Navigable endCell) {
        this.mazeMap = mazeMap;
        this.startCell = startCell;
        this.endCell = endCell;
        explorerRegistry = new HashSet<>(1);
        fillNavigationInCells();
        countOfWalls = this.getMazeMap().values().stream().filter(visitable -> visitable.isBlocked()).count();
        countOfSpaces = this.getMazeMap().values().stream().filter(visitable -> !visitable.isBlocked()).count();
    }

    public boolean permitExplorer(Visitor explorer) {
        if (explorerRegistry.size() == MAX_EXPLORERS) {
            return false;
        }
        explorerRegistry.add(explorer);
        return explorer.visit(getStartCell());
    }

    public List<MazeUtils.Direction> availableNextSteps(Visitor visitor) {
        Navigable currentCell= getCell(getCurrentCell(visitor));
        return currentCell.availableNextSteps();
    }

    public boolean moveRight(Visitor visitor) {
        return move(visitor, getVisitorsCurrentCell(visitor).getRight());
    }

    public boolean moveLeft(Visitor visitor) {
        return move(visitor, getVisitorsCurrentCell(visitor).getLeft());
    }

    public boolean moveUp(Visitor visitor) {
        return move(visitor, getVisitorsCurrentCell(visitor).getUp());
    }

    public boolean moveDown(Visitor visitor) {
        return move(visitor, getVisitorsCurrentCell(visitor).getDown());
    }

    private Navigable getVisitorsCurrentCell(Visitor visitor) {
        return mazeMap.get(visitor.getCurrentCell());
    }

    private boolean move(Visitor visitor, Navigable newCell) {
        boolean visitSuccessful = false;
        if (newCell != null) {
            visitSuccessful = visitor.visit(newCell);
        }
        return visitSuccessful;
    }

    public boolean reachedDestination(Visitor visitor) {
        return visitor.getCurrentCell().equals(endCell.getVisitableUniqueID());
    }

    public void sayByeToVisitor(Visitor visitor) {
        explorerRegistry.remove(visitor);
    }

    public Navigable getCell(String cellId) {
        return mazeMap.get(cellId);
    }

    public Navigable getStartCell() {
        return startCell;
    }

    public Navigable getEndCell() {
        return endCell;
    }

    public Map<String, Navigable> getMazeMap() {
        return mazeMap;
    }

    String getCurrentCell(Visitor visitor) {
        return visitor.getCurrentCell();
    }

    private void fillNavigationInCells() {
        mazeMap.values().forEach(visitable -> {
            visitable.setDown(this.getDownCell(visitable)); visitable.setLeft(this.getLeftCell(visitable));
            visitable.setUp(this.getUpCell(visitable)); visitable.setRight(this.getRightCell(visitable));
        } );
    }
    private Navigable getUpCell(Navigable currentCell) {
        String cellIdUp = MazeUtils.makeCellId(currentCell.getX() - 1, currentCell.getY());
        return getOpenCellIfAvailable(cellIdUp);
    }

    private Navigable getDownCell(Navigable currentCell) {
        String cellIdDown = MazeUtils.makeCellId(currentCell.getX() + 1, currentCell.getY());
        return getOpenCellIfAvailable(cellIdDown);
    }

    private Navigable getLeftCell(Navigable currentCell) {
        String cellIdLeft = MazeUtils.makeCellId(currentCell.getX(), currentCell.getY() - 1);
        return getOpenCellIfAvailable(cellIdLeft);
    }

    private Navigable getRightCell(Navigable currentCell) {
        String cellIdRight = MazeUtils.makeCellId(currentCell.getX(), currentCell.getY() + 1);
        return getOpenCellIfAvailable(cellIdRight);
    }

    private Navigable getOpenCellIfAvailable(String cellId) {
        Navigable upCell = this.getCell(cellId);
        if (upCell != null && !upCell.isBlocked()) {
            return upCell;
        }
        return null;
    }

    private final long countOfWalls;

    private final long countOfSpaces;



    public long getCountOfWalls() {

        return this.countOfWalls;

    }

    public long getCountOfSpaces() {

        return this.countOfSpaces;

    }



}