package rmiprintserver;

import java.util.HashMap;

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

    public static final HashMap<String, Boolean> defaultPermissions = new HashMap<>() {{
        put(START_PERMISSION, false);
        put(STOP_PERMISSION, false);
        put(RESTART_PERMISSION, false);
        put(STATUS_PERMISSION, false);
        put(READCONFIG_PERMISSION, false);
        put(SETCONFIG_PERMISSION, false);
        put(PRINT_PERMISSION, false);
        put(QUEUE_PERMISSION, false);
        put(TOPQUEUE_PERMISSION, false);
    }};
}