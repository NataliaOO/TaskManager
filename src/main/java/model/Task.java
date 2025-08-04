package model;

import lombok.Data;

@Data
public class Task {
    private int id;
    private String name;
    private String description;
    private StatusTask statusTask;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatusTask() +
                '}';
    }
}
