package com.fighting.Tool;

import java.util.ArrayList;
import java.util.Random;
import com.fighting.domain.User;

public class Tool {
    public static ArrayList<User> users = new ArrayList<>();
    public static int loginTime = 3;
    public static StringBuilder code = new StringBuilder();;
    // 自动生成id
    public static String generateId() {
        Random random = new Random();
        // 生成一个五位数的随机数
        int min = 10000;
        int max = 99999;
        return "zc" + (random.nextInt(max - min + 1) + min);
    }

    // 校验用户名
    public static boolean checkUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            System.out.println("用户名格式不正确，请重新输入");
            return false;
        }

        // 必须由字母、数字组成，不能纯数字
        boolean hasLetter = false;
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) { // 只能由字母或数字组成
                System.out.println("用户名必须由字母或数字组成，请重新输入");
                return false;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }
        if (!hasLetter) {
            System.out.println("用户名不能为纯数字，请重新输入");
            return false;
        }

        for (User user : users) {
            if (user.getName().equals(username)) {
                System.out.println("用户名已存在，请重新输入");
                return false;
            }
        }
        return true;
    }

    // 校验密码
    public static boolean checkPassword(String password) {
        if (password == null || password.length() < 3 || password.length() > 8) {
            System.out.println("密码格式不正确，请重新输入");
            return false;
        }
        return true;

    }

    // 校验用户
    public static boolean checkUser(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        for (User user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //查找用户
    public static User findUser(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // 生成一个验证码
    public static void generateCode() {
        code.setLength(0);// 先清空
        Random random = new Random();
        String zimu = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(zimu.length());
            char c = zimu.charAt(index);
            code.append(c);
        }
        int index = random.nextInt(code.length() );
        int num = random.nextInt(10);
        code.setCharAt(index, (char)(num + '0'));
        System.out.println("请输入验证码：" + code);
    }

    public static boolean checkCode(String inputCode) {
        return code.toString().equals(inputCode);
    }

    // 校验用户状态
    public static boolean checkState() {
        return true;
    }
}
