
package uk.ac.ebi.atlas.web;


public class DifferentialDesignRequestPreferences {

    private String selectedContrast;

    private String rootContext;

    public String getSelectedContrast() {
        return selectedContrast;
    }

    public void setSelectedContrast(String selectedContrast) {
        this.selectedContrast = selectedContrast;
    }

    //I'm not sure if this is used for anything, I think not. TODO try remove
    public String getRootContext() {
        return rootContext;
    }

    public void setRootContext(String rootContext) {
        this.rootContext = rootContext;
    }
}