package elsys.project;

import java.io.File;

public class Workflow {
    private String name;
    private File workflowFile;
    private String content;

    public Workflow(String name, File workflowFile) {
        this.name = name;
        this.workflowFile = workflowFile;
        //TODO: reading the file and getting it's content
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
