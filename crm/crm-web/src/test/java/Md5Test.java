import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Md5Test {

    @Test
    public void md5(){
        System.out.println(DigestUtils.md5Hex("123123"));
    }
}
