package backend.markdownfactory;

import java.util.ArrayList;
import java.util.List;

public class MarkdownTable extends MarkdownComponent {
    private final List<String> header;
    private final Integer numCells;
    private final List<List<String>> rows = new ArrayList<>();

    /**
     * Constructor to initialize the table with a header and the number of cells per row.
     *
     * @param header   The list of header titles for the table.
     * @param numCells The number of cells (columns) per row.
     */
    public MarkdownTable(List<String> header, Integer numCells) {
        this.header = header;
        this.numCells = numCells;
    }

    /**
     * This method adds a row to the table with
     * the specified list of cell values.
     *
     * @param row The list of cell values for the new row.
     * @throws IllegalArgumentException if the number of cell values does not
     *  match the number of cells per row.
     */
    public void addRow(List<String> row) {
        assert row.size() == numCells;
        rows.add(row);
    }

    /**
     * This method returns a plain string representation of the Markdown table.
     *
     * @return A string representing the chart in Mermaid Markdown syntax.
     */
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