package jdk.stream;

import java.util.*;
import java.util.stream.Collectors;


class Student{
    private String name;
    private int age;
    private Gender gender;
    private Grade grade;

    public Student () {
    }

    public Student (String name, int age, Gender gender, Grade grade) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public Gender getGender () {
        return gender;
    }

    public void setGender (Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade () {
        return grade;
    }

    public void setGrade (Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString () {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
enum Gender{
    FEMALE,MALE;
}
enum Grade{
    ONE,TWO,THREE,FOUR,FIVE
}
/**
 * 收集器
 */
public class StreamDemo6 {

    public static void main (String[] args) {

        List<Student > students =Arrays.asList(
            new Student("小红",18,Gender.FEMALE,Grade.ONE),
            new Student("小橙",17,Gender.MALE,Grade.ONE),
            new Student("小黄",19,Gender.FEMALE,Grade.TWO),
            new Student("小绿",20,Gender.FEMALE,Grade.FOUR),
            new Student("小青",18,Gender.MALE,Grade.FIVE),
            new Student("小蓝",16,Gender.FEMALE,Grade.FOUR),
            new Student("小紫",19,Gender.MALE,Grade.THREE));

//        List<Integer> collect = students.jdk.stream().map(Student :: getAge).collect(Collectors.toList());
        Collection<Integer> collect = students.stream().map(Student :: getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("所有学生的年龄："+collect);

        // 汇总信息
        IntSummaryStatistics intSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student :: getAge));
        System.out.println("年龄汇总信息："+intSummaryStatistics);

        // 分块
        Map<Boolean, List<Student>> collect1 = students.stream().collect(Collectors.partitioningBy(s -> s.getGender() == Gender.MALE));
        System.out.println("男生列表："+collect1.get(true));
        System.out.println("女生列表："+collect1.get(false));

        // 分组
        Map<Grade, List<Student>> collect2 = students.stream().collect(Collectors.groupingBy(s -> s.getGrade()));
        System.out.println("班级学生列表："+collect2);
        Map<Object, Long> collect3 = students.stream().collect(Collectors.groupingBy(s -> s.getGrade(),Collectors.counting()));
        System.out.println("班级学生个数列表："+collect3);
    }
}
