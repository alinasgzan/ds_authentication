package rmiprintserver;

import java.lang.reflect.Array;

public class Constants {

    public static final String PRINTER_DEFAULT_STATE = "Idle";
    public static final String PRINTER_PRINTING_STATE = "Printing";
    public static final long PRINTER_DEFAULT_PRINT_TIME = 10000;
    public static final int DEFAULT_PRINTER_COUNT = 5;
    public static final String NOT_STARTED_MESSAGE = "Service not started";
    public static final String [] DEFAULT_OPERATIONS = {"print", "start",  "stop",  "queue", "topQueue", "restart", "status", "readConfig", "setConfig"};
}