import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

public class Main {
    public static byte[] sBox=new byte[256];
    public static String secretKey="123456";//密钥
    public static String data="Hello World";//要加密的数据

    public static void main(String[] args) throws UnsupportedEncodingException {
        //因为直接输出显示字节会乱码，不方便复制
        //所以使用base64编码来显示加密结果
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

        System.out.println("元数据：");
        System.out.println(data);
        //加密
        init(secretKey.getBytes("UTF-8"));
        byte[] result=crypt(data.getBytes());
        System.out.println("加密结果：");


        System.out.println(encoder.encodeToString(result));
        //解密，要记得再初始化一次
        init(secretKey.getBytes("UTF-8"));
        byte[] result2=crypt(result);
        System.out.println("解密结果：");
        System.out.println(new String(result2));
        //System.out.println(new String(decoder.decode(encoder.encodeToString(result2)), "UTF-8"));
    }

    //初始化
    //传入的参数是密钥
    public static void init(byte[] key){
        int j=0;
        //用来保存密钥序列
        byte[] k=new byte[256];
        byte tmp;
        for(int i=0;i<256;i++) {
            //初始化0到255
            sBox[i]=(byte)i;
            //用密钥填满256位
            k[i]=key[i%key.length];
        }
        //打乱S盒
        for(int i=0;i<256;i++) {
            j=(j+(sBox[i]&0xff)+(k[i]&0xff))%256;
            //交换
            tmp=sBox[i];
            sBox[i]=sBox[j];
            sBox[j]=tmp;
        }
    }

    //加解密
    //传入的参数是要处理的数据
    public static byte[] crypt(byte[] data){
        int i=0,j=0,t;
        byte tmp;
        for(int k=0;k<data.length;k++)
        {
            //一套操作，主要目的就是保证随机性
            i=(i+1)%256;
            j=(j+(sBox[i]&0xff))%256;
            //交换
            tmp=sBox[i];
            sBox[i]=sBox[j];
            sBox[j]=tmp;
            t=((sBox[i]&0xff)+(sBox[j]&0xff))%256;
            //异或运算，这里直接一边生成密钥流一边加密了
            //其实也可以把密钥流全部保存到一个变量里，再依次和数据进行异或运算
            data[k]^=sBox[t];
        }
        return data;
    }

    /**
    //把字节转换成字符
    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }
     **/

}
