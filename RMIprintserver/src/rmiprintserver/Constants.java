package rmiprintserver;

import java.lang.reflect.Array;

public class Constants {

    public static final String PRINTER_DEFAULT_STATE = "Idle";
    public static final String PRINTER_PRINTING_STATE = "Printing";
    public static final long PRINTER_DEFAULT_PRINT_TIME = 10000;
    public static final int DEFAULT_PRINTER_COUNT = 5;
    public static final String NOT_STARTED_MESSAGE = "Service not started";
    public static final String [] DEFAULT_OPERATIONS = {"print", "start",  "stop",  "queue", "topQueue", "restart", "status", "readConfig", "setConfig"};

    public static final String START_PERMISSION = "start";
    public static final String STOP_PERMISSION = "stop";
    public static final String RESTART_PERMISSION = "restart";
    public static final String STATUS_PERMISSION = "status";
    public static final String READCONFIG_PERMISSION = "readConfig";
    public static final String SETCONFIG_PERMISSION = "setConfig";
    public static final String PRINT_PERMISSION = "print";
    public static final String QUEUE_PERMISSION = "queue";
    public static final String TOPQUEUE_PERMISSION = "topQueue";

}