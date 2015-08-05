package de.saxsys.gui.view;

import de.saxsys.model.Status;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RowTitles {
   public static Map<Status, String> ROW_TITLES;
    static {
        Map<Status, String> tempMap = new HashMap<>();
        tempMap.put(Status.TODO, "Todo");
        tempMap.put(Status.IN_PROGRESS, "In Progress");
        tempMap.put(Status.DONE, "Done");

        ROW_TITLES = Collections.unmodifiableMap(tempMap);
    }
}
