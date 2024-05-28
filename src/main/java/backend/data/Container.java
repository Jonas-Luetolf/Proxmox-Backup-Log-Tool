package backend.data;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class Container {
    private final int id;
    private final String name;
    private final ArrayList<Log> logs = new ArrayList<>();

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

    /**
     * returns an Array with the number of ok and failed jobs
     *
     * @return ArrayList first element: number of jobs with status ok
     *              second element: number of jobs with status failed
     */
    public ArrayList<Integer> getStatusStatistics (){
        int num_ok = 0;
        for (Log log : logs) {
            num_ok += (log.isStatus()) ? 1 : 0;

        }

        return new ArrayList<>(Arrays.asList(num_ok, this.logs.size() - num_ok));
    }

    public ArrayList<Pair<Number, Number>> getSizeStatistics () {
        ArrayList<Pair<Number, Number>> points = new ArrayList<>();
        ListIterator<Log> iterator = logs.listIterator();
        while (iterator.hasNext()) points.add(new Pair<>(iterator.nextIndex(), iterator.next().getSize()));

        return points;
    }
}