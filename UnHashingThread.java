import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UnHashingThread extends Thread {

    private String to_unhash;
    private int timeout;
    private List<Integer> crackedHashes;


    public UnHashingThread(String to_unhash, int timeout) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.crackedHashes = new ArrayList<Integer>();
    }

    public UnHashingThread(String to_unhash, int timeout, List<Integer> crackedHashes) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.crackedHashes = crackedHashes;
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
                    Pirate.crackedCompoundHashes.add(result);
                }
                else {
                    System.out.println(result);
                    Pirate.crackedHashes.add(Integer.parseInt(result));
                }
            }
            else {
                Pirate.uncrackedHashes.add(to_unhash);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }

}
