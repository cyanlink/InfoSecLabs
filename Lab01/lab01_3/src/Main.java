import java.io.*;
import java.util.*;

//词频分析
public class Main {
    //用来存放正常文章的文件夹
    public static String dir="text";
    //用来保存字母出现的次数
    //明文词频
    public static Map<Character, Integer> plainTextFreq=new HashMap<>();
    //密文词频
    public static Map<Character, Integer> cipherTextFreq=new HashMap<>();

    //分析后得出的代换表
    public static char[] plainTextAlphabet=new char[62];
    public static char[] cipherTextAlphabet=new char[62];

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入要解密的文件路径");
        String inputFileName=scan.nextLine();
        System.out.println("请输入保存解密内容的文件路径");
        String outputFileName=scan.nextLine();
        //初始化
        init();
        //统计明文的词频
        plainTextFreqAnalysis();
        //统计密文的词频
        cipherTextFreqAnalysis(inputFileName);
        //排序
        sort();
        //替换
        replace(inputFileName,outputFileName);
        System.out.println("解密完成");
    }
    //初始化
    public static void init(){
        //所有字母出现次数设置为0
        for (int i=0;i<26;i++){
            plainTextFreq.put((char)(i+65),0);
            plainTextFreq.put((char)(i+97),0);
            cipherTextFreq.put((char)(i+65),0);
            cipherTextFreq.put((char)(i+97),0);
        }
        //数字
        for (int i=0;i<10;i++){
            plainTextFreq.put((char)(i+48),0);
            cipherTextFreq.put((char)(i+48),0);
        }
    }

    //明文词频分析
    public static void plainTextFreqAnalysis() throws IOException {
        File file=new File(dir);
        File[] fileList=file.listFiles();
        for (int i=0;i<fileList.length;i++){
            //判断是否是文件
            if (fileList[i].isFile()){
                Reader reader = new InputStreamReader(new FileInputStream(fileList[i]));
                int tempchar;
                while ((tempchar = reader.read()) != -1) {
                    //如果读入的字符是词频表里有的
                    if (plainTextFreq.containsKey((char)tempchar)){
                        //先获取当前字符的出现次数
                        int t=plainTextFreq.get((char)tempchar);
                        //然后加一
                        t++;
                        //用新值覆盖
                        plainTextFreq.put((char)tempchar,t);
                    }
                }
                reader.close();
            }
        }
        //输出统计结果
        System.out.println("明文词频统计结果：");
        System.out.println(plainTextFreq);
    }

    //密文词频分析
    public static void cipherTextFreqAnalysis(String inputFileName) throws IOException {
        File inputFile=new File(inputFileName);
        Reader reader = new InputStreamReader(new FileInputStream(inputFile));
        int tempchar;
        while ((tempchar = reader.read()) != -1) {
            //如果读入的字符是字母
            if (cipherTextFreq.containsKey((char)tempchar)){
                //先获取当前字符的出现次数
                int t=cipherTextFreq.get((char)tempchar);
                //然后加一
                t++;
                //用新值覆盖
                cipherTextFreq.put((char)tempchar,t);
            }
        }
        reader.close();
        //输出统计结果
        System.out.println("密文词频统计结果：");
        System.out.println(cipherTextFreq);
    }

    //排序
    public static void sort(){
        //根据value值由大到小排序
        //以下代码参考了网上的例子
        List<Map.Entry<Character, Integer>> list1 = new ArrayList<Map.Entry<Character, Integer>>(plainTextFreq.entrySet());
        List<Map.Entry<Character, Integer>> list2 = new ArrayList<Map.Entry<Character, Integer>>(cipherTextFreq.entrySet());
        //使用list的自带的排序方法
        list1.sort(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        list2.sort(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println("明文 => 密文");
        //将排序结果存到另外一个数组中
        for (int i = 0; i < list1.size(); i++) {
            plainTextAlphabet[i]=list1.get(i).getKey();
            cipherTextAlphabet[i]=list2.get(i).getKey();
            System.out.println(plainTextAlphabet[i]+" => "+cipherTextAlphabet[i]);
        }
    }

    //知道替换表之后解密
    //解密
    //输入解密的文件路径
    public static void replace(String input,String output) throws IOException {
        File inputFile=new File(input);
        //判断文件是否存在
        if(!inputFile.exists()){
            System.out.println("文件不存在");
            System.exit(0);
            return;
        }
        //读文件
        System.out.println("开始读取文件");
        Reader reader = new InputStreamReader(new FileInputStream(inputFile));
        StringBuffer sb=new StringBuffer();
        //接收输入字符的变量
        int tempchar;
        while ((tempchar = reader.read()) != -1) {
            sb.append(decryptChar((char)tempchar));
        }
        reader.close();
        System.out.println("读取完成");

        //写文件
        System.out.println("开始写入文件");
        File outputFile=new File(output);
        outputFile.createNewFile();
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(outputFile));
        bos.write(sb.toString().getBytes());
        bos.flush();
        bos.close();
        System.out.println("写入完成");
    }

    //字符解密
    public static char decryptChar(char c){
        //和代换表中的字符比较
        for(int i=0;i<cipherTextAlphabet.length;i++){
            if (c==cipherTextAlphabet[i])
                return plainTextAlphabet[i];
        }
        return c;
    }
}
