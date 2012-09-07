package uk.ac.ebi.atlas.model;

class Transcript {

    private String id;
    private Integer specificityIndex;

    public Transcript(String id){
        this.id = id;
    }

    public Transcript(String id, int specificityIndex){
        this(id);
        this.setSpecificityIndex(specificityIndex);
    }

    public int getSpecificityIndex() {
        return specificityIndex;
    }

    public void setSpecificityIndex(int specificityIndex) {
        this.specificityIndex = specificityIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
