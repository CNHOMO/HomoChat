package ml.cnhomo.homochat.verify;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHash {
    public static String getHash(String str, String hashType){
        return hash(str, hashType);
    }
    private static String hash(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes());
                byte[] byteBuffer = messageDigest.digest();

                StringBuilder strHexString = new StringBuilder();
                for (byte b : byteBuffer) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }
}
