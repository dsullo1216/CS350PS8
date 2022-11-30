import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public String hash(String to_hash) throws NoSuchAlgorithmException {
        to_hash = String.valueOf(to_hash);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(to_hash.getBytes());
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

}