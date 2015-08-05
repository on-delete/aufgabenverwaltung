package de.saxsys.gui.controller;

import de.saxsys.gui.view.Expandable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class ExpendableController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        if ((event.getEventType().equals(ActionEvent.ACTION)) && (event.getSource() instanceof Hyperlink)) {
            Hyperlink source = (Hyperlink)event.getSource();
            if (source.getParent().getParent() instanceof Expandable) {
                Expandable expandableView = (Expandable)source.getParent().getParent();
                expandableView.switchExpansion();
            }
        }
    }
}
