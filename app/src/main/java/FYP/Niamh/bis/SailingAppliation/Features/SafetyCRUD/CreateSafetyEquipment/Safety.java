package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class Safety {
    private long id;
    private String name;
    private String description;
    private String issue;
    private String available;

    public Safety(int id, String name, String description, String issue, String available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.issue = issue;
        this.available =available;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getAvailable(){return available;}

    public void setAvailable(String available){this.available = available;}
}
