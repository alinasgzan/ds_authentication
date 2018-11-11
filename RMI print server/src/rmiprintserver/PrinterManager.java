package rmiprintserver;

public class PrinterManager {

    private boolean IsActive;
    //TODO: Implement the rest of the printing methods
    public PrinterManager() {
        IsActive = false;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public void toggleIsActive() {
        IsActive = !IsActive;
    }
}
