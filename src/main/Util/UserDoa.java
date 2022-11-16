package main.Util;
import sun.security.util.Password;
import java.io.*;

public class UserDoa {
    //用户的注册
    public static boolean UserRegisrer(String username, String psw) {
        boolean u = false;
        BufferedWriter bf = null;
        try {
            String temp = "";
            bf = new BufferedWriter(new FileWriter("C:\\Users\\yourhuaa\\Desktop\\file_manager\\src\\main\\user.txt"));
            bf.write("username=" + username);
            bf.newLine();
            bf.write("password=" + psw);
            u = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    //用户登录
    public static boolean userLogin(String username, String psw) {
        System.out.println("开始登录");
        boolean rst = false;
        BufferedReader br = null;
        String line = null;
        boolean ub = false;
        boolean pb = false;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\yourhuaa\\Desktop\\file_manager\\src\\main\\user.txt"));
            while ((line = br.readLine()) != null) {
                if (line.equals("username=" + username))//是名字才会比对。
                {
                    System.out.println("ERROR1");

                    ub = true;
                }
                if (line.equals("password=" + psw)) {
                    System.out.println("ERROR2");

                    pb = true;
                }
            }
            if (ub && pb) {
                System.out.println("成功");
                rst = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception q) {
                q.printStackTrace();
            }
        }

        return rst;

    }

    //修改密码
    public static boolean caangepsw(String oldpsw, String psw) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter("C:\\Users\\yourhuaa\\Desktop\\file_manager\\src\\main\\user.txt", true); // 传一个true，表示添加数据的方式是追加
            bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("password=" + psw);
            bw.flush();
            System.out.println("修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw == null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    //注销
    public static boolean withdraws() {

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("C:\\Users\\yourhuaa\\Desktop\\file_manager\\src\\main\\user.txt"));
            bw.write("");
            bw.flush();
            System.out.println("注销成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bw == null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return  true;
    }

}







