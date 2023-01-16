package Ex2_2;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
public class Task<V> extends FutureTask<V> implements Comparable<Task<V>>, Callable<V>  {

    private TaskType priority;
    private Callable<V> callable;

    private Task(Callable callable, TaskType priority) {
        super(callable);
        this.callable = callable;
        this.priority = priority;
    }

    private Task(Callable<V> callable) {
        super(callable);
        this.callable = callable;
        this.priority = TaskType.OTHER;
    }

    public static <V> Task<V> createTask(Callable<V> callable, TaskType type) {
        return new Task<>(callable, type);
    }

    public static <V> Task<V> createTask(Callable<V> callable) {
        return new Task<>(callable);
    }

    @Override
    public V call() throws Exception {
        return callable.call();
    }

    public TaskType getPriority(){
        return this.priority;
    }

    public Callable<V> getCallable(){
        return this.callable;
    }

    @Override
    public int compareTo(Task that) {

        if (this.priority.getPriorityValue() <that.priority.getPriorityValue()) {
            return -1;
        } else if (this.priority.getPriorityValue() > that.priority.getPriorityValue())
            return 1;
        return 0;
    }

}