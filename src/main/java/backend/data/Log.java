package backend.data;

public class Log {
    private String name;
    private boolean status;
    private int size;
    private int time;
    private String logText;

    public Log(String name, boolean status, int size, int time, String logText) {
        this.name = name;
        this.status = status;
        this.size = size;
        this.time = time;
        this.logText = logText;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public int getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

    public String getLogText() {
        return logText;
    }
}
