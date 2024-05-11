package ThreadsTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainTask {
    public static void main(String[] args) {

        int numbers[]={3190802,284792431,22476434,2485751,1011111,942846799,582934717,991,97,100,208,511,1098182,20998,17777,131317,281173,1211,984813,11714,289067};
        int count=0;

        long t1start = System.currentTimeMillis();

        for(int i=0;i< numbers.length;i++){
            for(int j=2;j<numbers[i];j++){
                if(numbers[i]%j==0){
                    count=count+1;
                }
            }
            if(count==2){
                System.out.println("prime");
            }else {
                System.out.println("not prime");
            }
        }
        long t1end = System.currentTimeMillis();

        long t1time = t1end - t1start;

        System.out.println("time taken by the process is "+t1time);

        long t2start=System.currentTimeMillis();

        //create a thread pool with a fixed no of threads
        ExecutorService executorService= Executors.newFixedThreadPool(3);

        List<Future<String>> futures = new ArrayList<>();
        List<Callable<String>> callableTasks = new ArrayList<>();

        try {


            for (int number : numbers) {
                callableTasks.add(new PrimeTask(number));
            }

            futures = executorService.invokeAll(callableTasks);

            //ExecutorService executorService = Executors.newFixedThreadPool(noOfThreadPool);

            //submit tasks to the executor
            for (Future<String> future : futures) {
                String result = future.get();
                System.out.println(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //shutting down executor task
        executorService.shutdown();

        long t2end=System.currentTimeMillis();

        long t2time=t2end-t2start;
        System.out.println("time taken by the second task is "+t2time);
    }
}

class PrimeTask implements Callable{

    int num;

    public PrimeTask(int num){
        this.num=num;
    }

    //code for the required logic
    @Override
    public String call() throws Exception{
        System.out.println(Thread.currentThread().getName()+" working on "+num);
        int count = 0;
        for(int i=2;i<num;i++){
            if(num%i==0){
                count=count+1;
            }
        }
        if(count==2){
            return "prime";
        }else{
            return "not prime";
        }

    }
}