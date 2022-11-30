import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UnHashingThread extends Thread {

    private String to_unhash;
    private int timeout;
    private List<String> uncrackedHashes;
    private List<Integer> crackedHashes;
    private List<String> crackedCompoundHashes;


    public UnHashingThread(String to_unhash, int timeout, List<String> results) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.uncrackedHashes = results;
        this.crackedHashes = new ArrayList<Integer>();
        this.crackedCompoundHashes = new ArrayList<String>();
    }

    public UnHashingThread(String to_unhash, int timeout, List<String> results, List<Integer> crackedHashes) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.uncrackedHashes = results;
        this.crackedHashes = crackedHashes;
        this.crackedCompoundHashes = new ArrayList<String>();
    }

    @Override
    public void run() {
        try {
            String result;
            boolean isCrackedHashes = (crackedHashes.size() == 0);
            result = isCrackedHashes ? new UnHash().unhash(this.to_unhash, this.timeout) : new UnHash().unhash(to_unhash, timeout, crackedHashes);
            if (!result.equals("-1")) {
                if (result.contains(";")) {
                    System.out.println(result);
                    Dispatcher.crackedCompoundHashes.add(result);
                }
                else {
                    System.out.println(result);
                    Dispatcher.crackedHashes.add(Integer.parseInt(result));
                }
            }
            else {
                Dispatcher.uncrackedHashes.add(to_unhash);
            }
            // System.out.println(new UnHash().unhash(to_unhash, timeout));
            

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }

}
