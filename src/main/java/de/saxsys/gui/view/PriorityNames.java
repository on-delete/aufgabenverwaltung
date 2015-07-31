package de.saxsys.gui.view;

import de.saxsys.model.Priority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PriorityNames {
    public static Map<Priority, String> PRIORITY_NAMES;
    static {
        Map<Priority, String> tempMap = new HashMap<>();
        tempMap.put(Priority.HIGH, "High");
        tempMap.put(Priority.MIDDLE, "Middle");
        tempMap.put(Priority.LOW, "Low");
        tempMap.put(Priority.VERY_LOW, "Very Low");
        PRIORITY_NAMES = Collections.unmodifiableMap(tempMap);
    }
}
