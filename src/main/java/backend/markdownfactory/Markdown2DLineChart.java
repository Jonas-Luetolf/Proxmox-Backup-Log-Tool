package backend.markdownfactory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Markdown2DLineChart extends MarkdownComponent{
    private final String title, xTitle, yTitle;
    private final List<Point2D> points = new ArrayList<>();
    public Markdown2DLineChart(String title, String xTitle, String yTitle) {
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }

    public void addPoint(Float yNum){
        points.add(new Point2D.Float(points.toArray().length, yNum));
    }

    @Override
    public String getPlainStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("``` mermaid\n xychart-beta\n");
        sb.append("   title " ).append(title).append("\n" );

        StringBuilder xData = new StringBuilder("[" );
        StringBuilder yData = new StringBuilder("[" );

        Double min_y = Double.MAX_VALUE;
        Double max_y = Double.MIN_VALUE;

        for (Point2D point : points) {
            xData.append(point.getX()).append("," );
            yData.append(point.getY()).append("," );

            if (point.getY() < min_y) min_y = point.getY();
            if (point.getY() > max_y) max_y = point.getY();
        }

        xData.deleteCharAt(xData.length()-1).append("]");
        yData.deleteCharAt(yData.length()-1).append("]");

        sb.append("   x-axis \"").append(xTitle).append("\" ").append(xData).append("\n");
        sb.append("   y-axis \"").append(yTitle).append("\" ").append(min_y).append(" --> ").append(max_y).append("\n");
        sb.append("   line ").append(yData).append("\n");
        sb.append("   bar ").append(yData).append("\n");

        sb.append("```\n");

        return sb.toString();
    }
}
