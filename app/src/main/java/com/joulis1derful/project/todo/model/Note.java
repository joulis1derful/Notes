package com.joulis1derful.project.todo.model;

public class Note {

    private String firebaseKey;
    private String title;
    private String body;
    private int priority;

    public Note() {
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getPriority() {
        return priority;
    }

    public Note(String firebaseKey, String title, String body, int priority) {
        this.firebaseKey = firebaseKey;
        this.title = title;
        this.body = body;
        this.priority = priority;
    }
}
