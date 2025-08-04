package model;


public class SubTask extends Task {
    private final int epikId;

    public SubTask(String name, String description, int epikId) {
        super(name, description);
        this.epikId = epikId;
    }

    public int getEpicId() {
        return epikId;
    }

    @Override
    public String toString() {
        return "\nSubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatusTask() +
                ", epicId=" + epikId +
                '}';
    }
}
