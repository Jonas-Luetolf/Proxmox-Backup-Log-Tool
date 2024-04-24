package backend.data;
import java.util.ArrayList;

public class Container {
    private int id;
    private String name;
    private ArrayList<Log> logs = new ArrayList<>();

    public Container(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    /**
     * adds a new Log to the Container
     *
     * @param newLog new Log to add
     */
    public void addLog(Log newLog){
        this.logs.add(newLog);
    }
}
