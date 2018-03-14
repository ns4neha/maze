package uk.gov.dwp.maze.utils;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;
import uk.gov.dwp.maze.*;
import uk.gov.dwp.maze.exception.MazeCreationException;

public class MazeUtils {

    private static final char DARK_CELL = 'X';

    private static final char START_CELL = 'S';

    private static final char END_CELL = 'F';

    private static final String CELL_ID_AXIS_DELIMITER = "-";

    public static Maze createMaze(File mazePlanFile) throws MazeCreationException {
        String row;
        List<String> rows = new ArrayList<>();
        try (LineNumberReader lineReader = new LineNumberReader(new FileReader(mazePlanFile))) {
            while ((row = lineReader.readLine()) != null) {
                rows.add(row);
            }
            return createCells(rows);
        } catch (Exception ioe) {
            throw new MazeCreationException(ioe.getMessage());
        }
    }

    private static Maze createCells(List<String> rows) throws MazeCreationException {
        int rowNumber = 0;
        boolean blocked;
        boolean start;
        boolean end;
        Navigable startCell = null;
        Navigable endCell = null;
        HashMap<String, Navigable> mazeMap = new HashMap<>();
        String cellId;
        for (String row : rows) {
            rowNumber++;
            char[] cellsInRow = row.toCharArray();
            int noOfCellsInRow = cellsInRow.length;
            for (int cellNumber = 1; cellNumber <= noOfCellsInRow; cellNumber++) {
                blocked = cellsInRow[cellNumber - 1] == DARK_CELL;
                start = cellsInRow[cellNumber - 1] == START_CELL;
                end = cellsInRow[cellNumber - 1] == END_CELL;
                cellId = makeCellId(rowNumber, cellNumber);
                Navigable cell = new Cell(cellId, rowNumber, cellNumber, blocked);
                if (start && startCell == null) startCell = cell;
                if (end && endCell == null) endCell = cell;
                mazeMap.put(cellId, cell);
            }
        }
        if (startCell == null)
            throw new MazeCreationException("Starting position is not mentioned in the Maze");
        if (endCell == null)
            throw new MazeCreationException("Finishing position is not mentioned in the Maze");
        return new Maze(mazeMap, startCell, endCell);
    }

    public static String makeCellId(int x, int y) {
        return Integer.toString(x) + CELL_ID_AXIS_DELIMITER + Integer.toString(y);
    }

    public static Visitor createExplorer(int id) {
        return new Explorer(id);
    }

    public static void print(Map<String, Navigable> mazeMap) {
        Set<String> cellIds = mazeMap.keySet();
        TreeSet<String> cellIdsSortedSet = new TreeSet<>(cellIds);
        cellIdsSortedSet.forEach((cellId) -> {
            Navigable cell = mazeMap.get(cellId);
            System.out.println("Cell " + cell.getVisitableUniqueID() + " Dark " + cell.isBlocked());
        });
    }

    public enum Direction {UP, DOWN, LEFT, RIGHT}
}
