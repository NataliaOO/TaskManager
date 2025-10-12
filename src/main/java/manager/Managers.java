package manager;

public final class Managers {
    private Managers() {}

    public static TaskManager getDefault() {
        return new TaskManagerImpl(new InMemoryHistoryManager());
    }
}
