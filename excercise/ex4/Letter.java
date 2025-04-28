package excercise.ex4;

public class Letter {
    private String from;
    private String to;
    private String body;

    public Letter(String from, String to) {
        this.from = from;
        this.to = to;
        this.body = " ";
    }

    public void addLine(String line) {
        body = body.concat(line).concat("\n");
    }

    public String getText() {
        String text = " ";
        text = text.concat(to).concat("Azizam\n");
        text = text.concat("\n");
        text = text.concat(body);
        text = text.concat("\n");
        text = text.concat("Ba Ehteram\n");
        text = text.concat("\n");
        text = text.concat(from);
        return text;
    }
}

