package backend.markdownfactory;

import java.util.ArrayList;
import java.util.List;

public class MarkdownTable extends MarkdownComponent {
    private final List<String> header;
    private final Integer numCells;
    private List<List<String>> rows = new ArrayList<>();

    public MarkdownTable(List<String> header, Integer numCells) {
        this.header = header;
        this.numCells = numCells;
    }

    public void addRow(List<String> row) {
        assert row.size() == numCells;
        rows.add(row);
    }

    @Override
    public String getPlainStringRepresentation() {
        StringBuilder s = new StringBuilder();

        // add header
        s.append("|").append(String.join("|", header)).append("|\n");
        s.append("|").append("---|".repeat(numCells)).append("\n");

        for (List<String> row : rows) {
            s.append("|").append(String.join("|", row)).append("|\n");
        }

        return String.valueOf(s);
    }
}