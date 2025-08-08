package arquivosapi.infrastructure.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class CriptografiaUtil {
    private static final String CHAVE_AES = "A1B2C3D4E5F6G7H8";

    public static byte[] processarAES(byte[] dados, int modo) throws Exception {
        Key key = new SecretKeySpec(CHAVE_AES.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(modo, key);
        return cipher.doFinal(dados);
    }
}
