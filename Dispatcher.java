import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Dispatcher {

    private String file_path;
    private int num_threads;
    private int timeout;
    private LinkedBlockingDeque<String> queue;
    // public static List<String> uncrackedHashes;
    // public static List<Integer> crackedHashes;
    // public static List<String> crackedCompoundHashes;

    public Dispatcher(String path_to_file, int n, int timeout) throws FileNotFoundException {
        file_path = path_to_file;
        num_threads = n;
        this.timeout = timeout;
        queue = new LinkedBlockingDeque<String>();
        // uncrackedHashes = Collections.synchronizedList(new ArrayList<String>());
        // crackedHashes = Collections.synchronizedList(new ArrayList<Integer>());
        // crackedCompoundHashes = Collections.synchronizedList(new ArrayList<String>());
        fillQueue();
    }

    public Dispatcher(List<String> hashes, int n, int timeout) {
        num_threads = n;
        this.timeout = timeout;
        queue = new LinkedBlockingDeque<String>();
        // uncrackedHashes = Collections.synchronizedList(new ArrayList<String>());
        // crackedHashes = Collections.synchronizedList(new ArrayList<Integer>());
        // crackedCompoundHashes = Collections.synchronizedList(new ArrayList<String>());
        queue.addAll(hashes);
    }

    public void fillQueue() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file_path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            queue.add(line);
        }
    }

    public void dispatch() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(num_threads);

        while (!queue.isEmpty()) {
            executor.execute(new UnHashingThread(queue.remove(), timeout));
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

    }

    public void dispatch(List<Integer> crackedHashes) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(num_threads);

        while (!queue.isEmpty()) {
            executor.execute(new UnHashingThread(queue.remove(), timeout, crackedHashes));
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }
        

    }

    // public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {

    //     String path_to_file = args[0];
    //     int n = Integer.parseInt(args[1]);
    //     Dispatcher dispatcher;
    //     if (args.length > 2) {
    //         int timeout = Integer.parseInt(args[2]);
    //         dispatcher = new Dispatcher(path_to_file, n, timeout);
    //     }
    //     else {
    //         dispatcher = new Dispatcher(path_to_file, n, 0);
    //     }
    //     dispatcher.fillQueue();
    //     while (!dispatcher.queue.isEmpty()) {
    //         dispatcher.dispatch();
    //     }

    // }
    
}
