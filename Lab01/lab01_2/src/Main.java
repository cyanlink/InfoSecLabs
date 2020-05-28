//任意代换表

import java.io.*;
import java.util.Scanner;

public class Main {
    //原字母表
    public static char[] alphabet={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5', '6','7','8','9'};

    //代换表
    public static char[] replace={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5', '6','7','8','9'};

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("选择模式：1.加密 2.解密");
        int mode = scan.nextInt();
        //防止吞回车
        scan.nextLine();
        if (mode==1){
            System.out.println("请输入要加密的文件路径");
            String inputFileName=scan.nextLine();
            System.out.println("请输入保存加密内容的文件路径");
            String outputFileName=scan.nextLine();
            replace(0,inputFileName,outputFileName);
            System.out.println("加密完成");
            return;
        }else if (mode==2){
            System.out.println("请输入要解密的文件路径");
            String inputFileName=scan.nextLine();
            System.out.println("请输入保存解密内容的文件路径");
            String outputFileName=scan.nextLine();
            replace(1,inputFileName,outputFileName);
            System.out.println("解密完成");
        }else
            return;
    }

    //加解密
    //输入加解密的文件路径
    //第一个参数，0是加密，1是解密
    public static void replace(int mode,String input,String output) throws IOException {
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
            //对字符进行替换
            if (mode==0)
                sb.append(replaceChar((char)tempchar));
            else
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

    //替换字符加密
    public static char replaceChar(char c){
        //大写字母
        if(c>='A'&&c<='Z'){
            c=replace[(int)c-65];
            return c;
        }
        //小写字母
        else if (c>='a'&&c<='z'){
            c=replace[(int)c-97+26];
            return c;
        }
        //数字
        else if (c>='0'&& c<='9'){
            c=replace[(int)c-48+52];
            return c;
        }
        else {
            return c;
        }
    }

    //字符解密
    public static char decryptChar(char c){
        //和代换表中的字符比较
        for(int i=0;i<replace.length;i++){
            if (c==replace[i])
                return alphabet[i];
        }
        return c;
    }
}
