package rmiprintserver;

import rmiprintserver.models.Printer;

import java.io.NotActiveException;

public class PrinterManager {

    private boolean IsActive;
    private Printer[] printerList;
    private int jobCount;

    //TODO: Implement the rest of the printing methods
    public PrinterManager() {
        IsActive = false;
        jobCount = 0;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public void toggleIsActive() {
        IsActive = !IsActive;
        if (IsActive) {
            jobCount = 0;
            populatePrinterList();
        } else emptyPrinterList();
    }

    public void print(String filename, int printer, String username) throws NotActiveException, IllegalArgumentException {
        if (IsActive)
            if (printer > 0 && printer <= Constants.DEFAULT_PRINTER_COUNT) {
                printerList[printer - 1].Print(username, filename, jobCount);
                jobCount++;
            } else throw new IllegalArgumentException();
        else throw new NotActiveException();
    }

    private void populatePrinterList() {
        for (int i = 0; i < Constants.DEFAULT_PRINTER_COUNT; i++) {
            printerList[i] = new Printer(i + 1);
            printerList[i].start();
        }
    }

    private void emptyPrinterList() {
        //So we don't have memory leaks
        for (int i = 0; i < Constants.DEFAULT_PRINTER_COUNT; i++) {
            printerList[i].interrupt();
        }
    }

    public String status() {

        if (!IsActive) return "";

        String printersStatus = "";
        for (Printer _printer : printerList) {
            printersStatus += String.format("%s%n%s", _printer.status(), "---------------------");
        }
        return printersStatus;
    }

    public String queue() {

        if (!IsActive) return "";

        String printersQueue = "";
        for (Printer _printer : printerList) {
            printersQueue += String.format("%s%n%s", _printer.getJobList(), "---------------------");
        }
        return printersQueue;
    }

    public boolean topQueue(int jobId) {
        for (Printer _printer : printerList)
            if (_printer.topQueue(jobId)) return true;
        return false;
    }


}
