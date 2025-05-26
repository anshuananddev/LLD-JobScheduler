import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class JobScheduler {
    PriorityBlockingQueue<Job> jobs;
    ExecutorService executors ;
    private ClusterManager clusterManager;

    public JobScheduler() {
        jobs = new PriorityBlockingQueue<>(100 ,  Comparator.comparingInt(Job::getPriority)) ;
        executors = Executors.newFixedThreadPool(2) ;
        clusterManager = ClusterManager.getInstance();
    }

    public boolean addJob(Job job) {
        jobs.put(job);
        return true;
    }

    public void startJob() {

    }
}