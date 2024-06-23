package backend.markdownfactory;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MarkdownPieChart  extends MarkdownComponent{
    private final String title;
    private final List<Pair<String, Integer>> classes = new ArrayList<>();

    /**
     * Constructor to initialize the pie chart with a title.
     *
     * @param title The title of the pie chart.
     */
    public MarkdownPieChart(String title) {
        this.title = title;
    }

    /**
     * This method adds a class to the pie chart
     * with the specified label and data value.
     *
     * @param label The label of the class.
     * @param data  The data value associated with the class.
     */
    public void addClass(String label, Integer data) {
        classes.add(new Pair<>(label, data));
    }

    /**
     * This method returns a plain string representation of the pie
     * chart in Mermaid Markdown syntax.
     *
     * @return A string representing the chart in Mermaid Markdown syntax.
     */
    @Override
    public String getPlainStringRepresentation() {
        StringBuilder s = new StringBuilder();
        s.append("```mermaid\n");
        s.append("pie\n");
        s.append("  title " ).append(title).append("\n" );

        for (Pair<String, Integer> cls : classes) {
            s.append("  \"").append(cls.getKey()).append("\" : ").append(cls.getValue()).append("\n" );
        }

        s.append("```\n");
        return s.toString();
    }
}
