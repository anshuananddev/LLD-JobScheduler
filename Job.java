public class Job {
    int requiredRAM;
    int requiredCPU;
    String jobId;
    int priority;

    public Job(int requiredRAM, int requiredCPU, String jobId, int priority) {
        this.requiredRAM = requiredRAM;
        this.requiredCPU = requiredCPU;
        this.jobId = jobId;
        this.priority = priority;
    }

    public Job() {
    }

    public int getRequiredRAM() {
        return requiredRAM;
    }

    public void setRequiredRAM(int requiredRAM) {
        this.requiredRAM = requiredRAM;
    }

    public int getRequiredCPU() {
        return requiredCPU;
    }

    public void setRequiredCPU(int requiredCPU) {
        this.requiredCPU = requiredCPU;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
