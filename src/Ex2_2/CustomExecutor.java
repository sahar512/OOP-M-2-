package Ex2_2;

import java.util.Arrays;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor{
    private int[] prioritiesList;
    private ThreadPoolExecutor poolExecutor;

    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() -1,
                300,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>());

        prioritiesList = new int[10];
        Arrays.fill(prioritiesList , 0);
    }
    public <V> Future<V> submit(Task<V> task) {
        if (task!=null)
        {
            this.prioritiesList[task.getPriority().getPriorityValue()]= this.prioritiesList[task.getPriority().getPriorityValue()]+1;
            super.execute(task);
            return (task);
        }
        throw new NullPointerException();
    }

    public <V> Future<V> submit(Callable<V> callable) {
        return submit(Task.createTask(callable));
    }

    public <V> Future<V> submit(Callable<V> callable, TaskType taskType) {
        return submit(Task.createTask(callable, taskType));
    }

    int getCurrentMax(){
        for (int i = 0 ; i<this.prioritiesList.length ; i++) {
            if(this.prioritiesList[i] > 0){
                return i + 1;
            }
        }

        return -1;
    }


    public void gracefullyTerminate(){
        this.shutdown();
    }

}
