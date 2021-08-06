package ceit.aut.ac.ir.model;

public class Note {

    private String title;
    private String content;
    private String date;

    public Note(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Note {\n" +
                "title='" + title + "'\n" +
                ", content='" + content + "'\n" +
                ", date='" + date + '\n' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}

