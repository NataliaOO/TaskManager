package model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }
}
