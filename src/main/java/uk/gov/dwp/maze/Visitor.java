package uk.gov.dwp.maze;

import java.util.List;

public interface Visitor {
    boolean visit(Navigable maze);

    List<String> getPreviousMoves();

    String getCurrentCell();

    int getExplorerId();
}