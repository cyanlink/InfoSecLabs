
public class MyRC4 {



    public static void main(String[] args) {

        MyRC4 rc4 = new MyRC4();
        //明文
        String plaintext = "helloworld";
        //密钥
        String key = "hasdg";
        //加密
        String ciphertext = rc4.encrypt(plaintext, key);
        //解密
        String decryptText = rc4.encrypt(ciphertext, key);

        System.out.print(

                "明文为：" + plaintext + "\n" + "密钥为：" + key + "\n\n" + "密文为：" + ciphertext + "\n" + "解密为：" + decryptText);
    }



    /**

     * 3.加解密过程是一样的

     * 加密：明文流字与密钥流字XOR得到密文流字

     * 解密：密文流字与密钥流字XOR得到明文流字

     * */

    public String encrypt(final String plainOrCipherText, final String key) {

        Integer[] S = new Integer[256]; // S盒

        Character[] keySchedul = new Character[plainOrCipherText.length()]; // 生成的密钥流

        StringBuffer ciphertext = new StringBuffer();
        //初始化
        ksa(S, key);
        //伪随机
        rpga(S, keySchedul, plainOrCipherText.length());



        for (int i = 0; i < plainOrCipherText.length(); ++i) {

            ciphertext.append((char) (plainOrCipherText.charAt(i) ^ keySchedul[i]));

        }



        return ciphertext.toString();

    }



    /*1.初始化向量S*/

    public void ksa(Integer[] s, String key) {

        for (int i = 0; i < 256; ++i) {

            s[i] = i;

        }



        int j = 0;

        for (int i = 0; i < 256; ++i) {

            j = (j + s[i] + key.charAt(i % key.length())) % 256;

            swap(s, i, j);

        }

    }



    /*2.伪随机生成算法*/

    public void rpga(Integer[] s, Character[] keySchedul, int plaintextLength) {

        int i = 0, j = 0;

        for (int k = 0; k < plaintextLength; ++k) {

            i = (i + 1) % 256;

            j = (j + s[i]) % 256;

            swap(s, i, j);

            keySchedul[k] = (char) (s[(s[i] + s[j]) % 256]).intValue();

        }

    }



    /*置换*/

    public void swap(Integer[] s, int i, int j) {

        Integer mTemp = s[i];

        s[i] = s[j];

        s[j] = mTemp;

    }

}
