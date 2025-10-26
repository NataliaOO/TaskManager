package manager;

import model.Task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_SIZE = 10;
    private final Deque<Task> history = new ArrayDeque<>(MAX_SIZE);

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (history.size() == MAX_SIZE) {
            history.removeFirst(); // сложность O(1)
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
