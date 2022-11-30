import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pirate {

    private String file_path;
    private int num_threads;
    private int timeout;
    public static List<String> uncrackedHashes;
    public static List<Integer> crackedHashes;
    public static List<String> crackedCompoundHashes;

    public Pirate(String path_to_file, int n, int timeout) {
        file_path = path_to_file;
        num_threads = n;
        this.timeout = timeout;
        uncrackedHashes = Collections.synchronizedList(new ArrayList<String>());
        crackedHashes = Collections.synchronizedList(new ArrayList<Integer>());
        crackedCompoundHashes = Collections.synchronizedList(new ArrayList<String>());
    }

    public void findTreasure() throws FileNotFoundException, InterruptedException {
        // Try to crack the hashes in the file without hints
        Dispatcher dispatcher = new Dispatcher(file_path, num_threads, timeout);
        
        dispatcher.dispatch();
        
        Collections.sort(crackedHashes);

        // Try to crack the hashes in the file with hints
        Dispatcher dispatcher2 = new Dispatcher(uncrackedHashes, num_threads, timeout);
        
        dispatcher2.dispatch(crackedHashes);
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        // String file_path = args[0];
        // int num_threads = Integer.parseInt(args[1]);
        // int timeout = Integer.parseInt(args[2]);
        String file_path = "hashes.txt";
        int num_threads = 4;
        int timeout = 2000;
        Pirate pirate = new Pirate(file_path, num_threads, timeout);
        pirate.findTreasure();
    }
    
}
