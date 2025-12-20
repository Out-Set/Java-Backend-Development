package com.student.manage;

public class Student {
    private int studentId;
    private String studentName;
    private String StudentPhone;
    private String studentCity;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return StudentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        StudentPhone = studentPhone;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public Student(int studentId, String studentName, String studentPhone, String studentCity) {
        this.studentId = studentId;
        this.studentName = studentName;
        StudentPhone = studentPhone;
        this.studentCity = studentCity;
    }

    public Student(String studentName, String studentPhone, String studentCity) {
        this.studentName = studentName;
        StudentPhone = studentPhone;
        this.studentCity = studentCity;
    }

    public Student() {
        super();
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", StudentPhone='" + StudentPhone + '\'' +
                ", studentCity='" + studentCity + '\'' +
                '}';
    }
}
