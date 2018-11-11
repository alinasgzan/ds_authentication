package rmiprintserver.models;

import rmiprintserver.Constants;

import java.util.ArrayList;

public class Printer extends Thread {

    private String state;
    private ArrayList<PrintJob> jobs;
    private int printerId;
    private PrintJob currentPrintJob;

    public Printer(int _printerId) {
        printerId = _printerId;
        state = Constants.PRINTER_DEFAULT_STATE;
        jobs = new ArrayList<>();
    }

    public void Print(String initiatedBy, String filename, int jobId) {
        jobs.add(new PrintJob(initiatedBy, filename, jobId));
        state = Constants.PRINTER_PRINTING_STATE;
    }

    public boolean topQueue(int jobID) {

        for (int i = 0; i <= jobs.size(); i++) {
            PrintJob _printJob = jobs.get(i);

            if (_printJob.getJobId() == jobID) {
                PrintJob _print = jobs.remove(jobID);

                jobs.add(0, _print);

                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        System.out.println(String.format("Printer %d started.", printerId));
        try {
            while (true) {
                while (jobs.size() == 0) {
                    Thread.onSpinWait();
                }
                currentPrintJob = jobs.remove(0);

                Thread.sleep(Constants.PRINTER_DEFAULT_PRINT_TIME);

            }
        } catch (InterruptedException e) {

            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }

    }

    public String status() {
        return state.equals(Constants.PRINTER_DEFAULT_STATE) ? Constants.PRINTER_DEFAULT_STATE : Constants.PRINTER_PRINTING_STATE + " " + currentPrintJob.toString();
    }

    public String getJobList() {

        String jobList = "";

        for (PrintJob _job : jobs) {
            jobList += String.format("%s%n", _job.toString());
        }

        return jobList;
    }
}