package de.saxsys.model;

import de.saxsys.gui.view.ActiveViewElement;

public interface Model {
    /**
     * Adds en view Element, that will be notified whenever the underlying model data get changed
     * @param view the view Element
     * @return True if element was added
     */
    public boolean registerView(ActiveViewElement view);
    
    /**
     * Notify all registered views, if elements changed
     */
    public void notifyViews();
}
