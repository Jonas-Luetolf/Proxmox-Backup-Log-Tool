package backend.data;

public class Log {
    private final boolean status;
    private final float size; // Unit GB
    private final float time; // Unit: min
    private final String logText;

    public Log(boolean status, float size, float time, String logText) {
        this.status = status;
        this.size = size;
        this.time = time;
        this.logText = logText;
    }

    public boolean isStatus() {
        return status;
    }

    public float getSize() {
        return size;
    }

    public float getTime() {
        return time;
    }

    public String getLogText() {
        return logText;
    }
}
