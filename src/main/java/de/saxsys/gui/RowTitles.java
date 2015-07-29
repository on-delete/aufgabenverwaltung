package de.saxsys.gui;

public enum RowTitles {
    TODO("Todo"), IN_RROGRESS("InProgress"), DONE("Done");
    
    private String title;
    
    private RowTitles(String title){
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
}
