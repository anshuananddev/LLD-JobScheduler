import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JobScheduler {
    PriorityBlockingQueue<Job> jobs;
    private static ExecutorService executors ;
    private ClusterManager clusterManager;
    private final int maxThread= 2 ;

    public JobScheduler() {
        jobs = new PriorityBlockingQueue<>(100 ,  Comparator.comparingInt(Job::getPriority)) ;
        executors = Executors.newFixedThreadPool(maxThread) ;
        clusterManager = ClusterManager.getInstance();
    }

    public boolean addJob(Job job) {
        jobs.put(job);
        return true;
    }

    public void init(){
        int maxT = maxThread ;
        while(maxT-- > 0 ) {
            executors.submit(()-> {
                try {
                    startJob();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("JobScheduler interrupted");
                }
            }) ;
        }
    }
    private void startJob() throws InterruptedException {
        while(true) {
            Job job = jobs.take() ;
            System.out.println("Thread - " + Thread.currentThread().getId() + ": " + job.getJobId());
            String clusterId = clusterManager.findAnsUseCluster(job.requiredRAM , job.requiredCPU);
            if(clusterId!= null) {
                System.out.println("Thread - " + Thread.currentThread().getId() + ": " + clusterId);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    clusterManager.claimResource(clusterId , job.requiredRAM , job.requiredCPU);
                }
            }else{
                System.out.println("Thread - " + Thread.currentThread().getId() + ": No Cluster");
                jobs.put(job);
            }

        }
    }
}