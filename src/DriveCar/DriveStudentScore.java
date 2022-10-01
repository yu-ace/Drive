package DriveCar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Scanner;

/** 驾校成绩管理系统
 * 1、录入学生姓名和成绩  <br>
 * 2、生成成绩列表 <br>
 * 3、输出平均分、最高分和最低分 <br>
 * 4、生成每个分数段的人数 <br>
 * 5、输入学生姓名，查询成绩 <br>
 * 6、输入数字，查询大于等于该数字的分数 <br>
 * 7、保存数据到磁盘 <br>
 * 8、从磁盘中读取 <br>
 * 9、 有四个科目
 * 10、 录入成绩时，不需要一次性录入所有成绩
 * 11、 查看多少学生尚未通过全部科目（在读学生）
 * 12、 毕业学生
 *
 *
 * ---------------
 * 1、main函数以外的调用，需添加static；
 * 2、调用运用数组的函数，需要新建一个数组接收函数；
 * 3、没有返回值的函数，用void，调用时直接填函数名；
 * 4、外部函数调用： import org.apache.commons.io.FileUtils;
 * 5、读取文件时，需声明文件的位置；
 * 6、字符分割后，建立新的字符串接收；
 * 7、for循环中，注意循环体循环的条件；
 * 8、步入、步过的使用方法；
 * 9、main函数的写法顺序：函数调用的框架——main函数框架——补充函数调用的内容——补充main函数；
 * 10、数组不能直接做加减乘除运算，需先取值，后运算；
 */

public class DriveStudentScore {

    static String[] students = new String[50];

    static int[][] grades = new int[50][4];

    static int size = 0;

    public static void main(String[] args) {
        load();
        Scanner scan = new Scanner(System.in);
        printHelp();
        while(true){
            System.out.println("请输入数字，输入h查看帮助");
            String a = scan.nextLine();
            if(a.equals("h")){
                printHelp();
            }else if(a.equals("1")){
                System.out.println("输入学生姓名：");
                String student = scan.nextLine();
                System.out.println("输入科目一成绩：");
                int grade1 = scan.nextInt();
                System.out.println("输入科目二成绩：");
                int grade2 = scan.nextInt();
                System.out.println("输入科目三成绩：");
                int grade3 = scan.nextInt();
                System.out.println("输入科目四成绩：");
                int grade4 = scan.nextInt();
                int[] grade = new int[4];
                grade[0] = grade1;
                grade[1] = grade2;
                grade[2] = grade3;
                grade[3] = grade4;
                addStudents(student,grade);
            }else if (a.equals("2")){
                printStudentList();
            }else if (a.equals("3")){
                double[] ave = average();
                for(int i = 0;i < ave.length;i++){
                    System.out.println(ave[i]);
                }
                int[] maxL = max();
                for(int i = 0;i < maxL.length;i++){
                    System.out.println(maxL[i]);
                }
                int[] minL =min();
                for(int i = 0;i < maxL.length;i++){
                    System.out.println(minL[i]);
                }
            }else if (a.equals("4")){
                int[][] gradeGroup = studentGroupByGrade();
                int [] group0 = gradeGroup[0];
                for(int i = 0;i < group0.length;i++){
                    System.out.println(group0[i]);
                }
                int [] group1 = gradeGroup[1];
                for(int i = 0;i < group0.length;i++){
                    System.out.println(group1[i]);
                }
                int [] group2 = gradeGroup[2];
                for(int i = 0;i < group0.length;i++){
                    System.out.println(group2[i]);
                }
                int [] group3 = gradeGroup[3];
                for(int i = 0;i < group0.length;i++){
                    System.out.println(group3[i]);
                }
                int [] group4 = gradeGroup[4];
                for(int i = 0;i < group0.length;i++){
                    System.out.println(group4[i]);
                }
            }else if (a.equals("5")){
                System.out.println("输入姓名：");
                int[] studentGBN = studentGradeByName(scan.nextLine());
                for(int i = 0;i < studentGBN.length;i++){
                    System.out.println(studentGBN[i]);
                }
            }else if (a.equals("6")){
                break;
            }else if (a.equals("q")){
                break;
            }else if(a.equals("s")){
                save();
            }else if(a.equals("7")){
                System.out.println(gradepass());
            }else if(a.equals("8")){
                System.out.println(size - gradepass());
            }
        }
    }

    /**
     * 帮助列表
     */
    public static void printHelp(){
        System.out.println("欢迎来到成绩管理系统：");
        System.out.println("输入1，录入学生成绩和姓名：");
        System.out.println("输入2，生成成绩列表：");
        System.out.println("输入3，计算平均分、最高分和最低分：");
        System.out.println("输入4，生成每个分数段的人数：");
        System.out.println("输入5，根据学生姓名查询分数：");
        System.out.println("输入6，输入分数，查询大于等于该分数的成绩");
        System.out.println("输入s，保存文件");
        System.out.println("输入q，退出");
    }

    /**
     * 录入学生的姓名和成绩
     * @param student 学生姓名
     * @param grade 学生成绩
     */
    public static void addStudents(String student,int[] grade){
        students[size] = student;
        grades[size] = grade;
        size++;
    }

    /**
     * 输出学生成绩单
     */
    public static void printStudentList(){
        for(int i = 0;i < size;i++){
            System.out.print(students[i]+":\t");
            for(int j = 0;j < grades[i].length ;j++){
                System.out.print(grades[i][j]+"\t");
            }
            System.out.println();
        }
    }

    /**
     * 计算学生平均分
     * @return 平均分
     */
    public static double[] average(){
        double sum[] = new double[4];
        int num[] = new int[4];
        for(int i = 0;i < size;i++){
            for(int j = 0;j < 4;j++){
                sum[j] = sum[j] + grades[i][j];
                if(grades[i][j]>0){
                    num[j] = num[j]+1;
                }
            }
        }
        for(int j = 0;j < 4;j++){
            sum[j] = sum[j]/num[j];
        }
        return sum;
    }

    /**
     * 计算学生最高分
     * @return 最高分
     */
    public static int[] max(){
        int[] max= grades[0];
        for(int i = 0;i < size;i++){
            for(int j = 0;j < grades[i].length;j++){
                if(max[j] < grades[i][j]){
                    max[j] = grades[i][j];
                }
            }
        }
        return max;
    }

    /**
     * 计算最低分
     * @return 最低分
     */
    public static int[] min(){
        int[] min = grades[0];
        for(int i = 0;i < size;i++){
            for(int j = 0;j < 4;j++){
                if(min[j] > grades[i][j]){
                    min[j] = grades[i][j];
                }
            }
        }
        return min;
    }

    /**
     * 按分数段统计学生人数
     * @return 各分数段学生人数
     */
    public static int[][] studentGroupByGrade(){
        int[][] group = new int[5][4];
        for(int i = 0;i < size;i++){
            for(int j = 0;j < 4;j++){
                int score = grades[i][j];
                int grade = score / 10;
                switch(grade){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        group[0][j]++;
                        break;
                    case 6:
                        group[1][j]++;
                        break;
                    case 7:
                        group[2][j]++;
                        break;
                    case 8:
                        group[3][j]++;
                        break;
                    default:
                        group[4][j]++;
                }
            }
        }
        return group;
    }

    /**
     * 按输入的姓名查询成绩
     * @param name 学生姓名
     * @return 学生成绩
     */
    public static int[] studentGradeByName(String name){
        for(int i = 0;i <size;i++){
            if(students[i].equals(name)){
                return grades[i];
            }
        }
        return null;
    }

/*
    public static void studentGradeByNumber(int n,int score){
        for(int i = 0;i < size;i++){
            for(int j = 0;j < 4;j++){
                if(grades[i][j] >= number[j]){
                    System.out.println(grades[i][j]);
                }
            }

        }
    }
*/

    public static int sum(){
        int sum = 0;
        for(int i = 0;i < 100;i++){
            sum =sum +i;
        }
        return sum;
    }

    /**
     * 保存
     */
    public static void save(){
        File studentNameList = new File(FileUtils.getUserDirectoryPath()+"/StudentGrade/data/studentName.dat");
        File studentGradeList = new File(FileUtils.getUserDirectoryPath()+"/StudentGrade/data/studentScore.dat");
        FileUtils.deleteQuietly(studentNameList);
        FileUtils.deleteQuietly(studentGradeList);
        try{
            for(int i = 0;i < size;i++){
                FileUtils.write(studentNameList,students[i]+"\n","utf8",true);
            }
            for (int i = 0; i < size; i++) {
                String grade = "";
                for(int j = 0;j < grades[i].length;j++){
                    grade = grade + grades[i][j] + ",";
                }
                FileUtils.write(studentGradeList,grade+"\n","utf8",true);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取
     */
    public static void load(){
        File studentNameList = new File(FileUtils.getUserDirectoryPath()+"/StudentGrade/data/studentName.dat");
        File studentGradeList = new File(FileUtils.getUserDirectoryPath()+"/StudentGrade/data/studentScore.dat");
        try {
            String studentName = FileUtils.readFileToString(studentNameList,"utf8");
            String studentGrade = FileUtils.readFileToString(studentGradeList,"utf8");
            String[] stuName = studentName.split("\n");
            String[] stuGrade = studentGrade.split("\n");
            for(int i = 0;i < stuName.length;i++){
                String name = stuName[i];
                String[] splitStr = stuGrade[i].split(",");
                int[] g = new int[4];



                for (int i1 = 0; i1 < splitStr.length; i1++) {
                    int grade = Integer.parseInt(splitStr[i1]);
                    g[i1] = grade;
                }
                addStudents(name,g);
            }
        } catch (IOException e) {
        }
    }


    public static int gradepass(){
        int[][] grade = new int[50][4];
        int a = 0;
        for(int i = 0;i < grade.length;i++){
            for(int j = 0;j < 4;j++){
                if(grade[i][j] < 60){
                    a++;
                    break;
                }
            }
        }
        return a;
    }
}


