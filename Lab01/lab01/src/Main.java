//凯撒密码

import java.io.*;
import java.util.Scanner;

public class Main {
    //字母表
    /**
    char[] alphabet={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    **/

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
            System.out.println("请输入偏移量");
            int offset=scan.nextInt();
            scan.nextLine();
            encrypt(offset,inputFileName,outputFileName);
            System.out.println("加密完成");
            return;
        }else if (mode==2){
            System.out.println("请输入要解密的文件路径");
            String inputFileName=scan.nextLine();
            System.out.println("请输入保存解密内容的文件路径");
            String outputFileName=scan.nextLine();
            System.out.println("请输入偏移量");
            int offset=scan.nextInt();
            scan.nextLine();
            encrypt((-1)*offset,inputFileName,outputFileName);
            System.out.println("解密完成");
        }else
            return;
    }

    //加解密
    //输入的参数是偏移量和要加密的文件路径
    public static void encrypt(int key,String input,String output) throws IOException {
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
            //对字符进行偏移
            sb.append(offset((char)tempchar,key));
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


    //对字符进行加解密
    public static char offset(char c,int k){
        k=k%26;
        //判断是正偏还是负偏
        if(k<0){
            k=26+k;
        }
        //根据ASCII码判断
        if (c>='A'&&c<='Z'){
            c=(char)(((int)c-65+k)%26+65);
            return c;
        }else if (c>='a'&& c<='z'){
            c=(char)(((int)c-97+k)%26+97);
            return c;
        }else {
            return c;
        }
    }
}
