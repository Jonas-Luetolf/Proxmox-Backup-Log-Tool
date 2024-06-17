package backend.markdownfactory;

public class MarkdownHeadline extends MarkdownComponent{
    private final String title;
    private final Integer level;

    public MarkdownHeadline(String title, Integer level) {
        this.title = title;
        this.level = level;
    }

    @Override
    public String getPlainStringRepresentation() {
        return "# ".repeat(level + 1) +
                title +
                "\n";
    }
}