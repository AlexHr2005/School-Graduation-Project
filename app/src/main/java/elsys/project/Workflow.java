package elsys.project;

import java.io.File;
import java.util.Objects;

public class Workflow {
    private String name;
    private File workflowFile;
    private String content;

    public Workflow(String name, File workflowFile) {
        this.name = name;
        this.workflowFile = workflowFile;
        //TODO: reading the file and getting it's content
    }

    public Workflow(String name) {
        this.name = name;
        this.workflowFile = new File(WorkflowsList.getWorkflowsDir(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getWorkflowFile() {
        return workflowFile;
    }

    public void setWorkflowFile(File workflowFile) {
        this.workflowFile = workflowFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workflow workflow = (Workflow) o;
        return name.equals(workflow.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
