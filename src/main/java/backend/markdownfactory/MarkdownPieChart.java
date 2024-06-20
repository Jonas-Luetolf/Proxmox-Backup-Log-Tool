package backend.markdownfactory;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MarkdownPieChart  extends MarkdownComponent{
    private final String title;
    private final List<Pair<String, Integer>> classes = new ArrayList<>();

    public MarkdownPieChart(String title) {
        this.title = title;
    }

    public void addClass(String label, Integer data) {
        classes.add(new Pair<>(label, data));
    }

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
