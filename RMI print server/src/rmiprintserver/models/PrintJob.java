package rmiprintserver.models;

public class PrintJob {
    private String initiatedBy;
    private String fileName;
    private int jobId;

    public PrintJob(String _initiatedBy, String _fileName, int _jobId) {
        initiatedBy = _initiatedBy;
        fileName = _fileName;
        jobId = _jobId;
    }

    public int getJobId() {
        return jobId;
    }

    @Override
    public String toString() {
        return fileName + " by " + initiatedBy;
    }
}
