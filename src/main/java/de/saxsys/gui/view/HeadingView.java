package de.saxsys.gui.view;

import de.saxsys.model.Status;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HeadingView extends HBox {

    DoubleBinding columnWith;
    Double textWidth;

    public HeadingView(DoubleBinding columnWith) {

        this.columnWith = columnWith;
        textWidth = columnWith.get();
        this.columnWith.addListener((e) -> {
            textWidth = columnWith.get();
            setHeadings();
        });

        setHeadings();
    }

    public void setHeadings() {
        getChildren().clear();
        for (Status status : Status.values()) {
            Text heading = new Text(RowTitles.ROW_TITLES.get(status));
            heading.setId(heading.getText() + "_heading");
            heading.setTextAlignment(TextAlignment.CENTER);
            heading.setWrappingWidth(textWidth);

            getChildren().add(heading);
        }
    }
}
