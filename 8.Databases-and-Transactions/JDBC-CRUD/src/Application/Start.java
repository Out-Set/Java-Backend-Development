package Application;

import com.student.manage.Student;
import com.student.manage.StudentDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the App");
        System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("PRESS 1 to Add student");
            System.out.println("PRESS 2 to Update details");
            System.out.println("PRESS 3 to Delete student");
            System.out.println("PRESS 4 to Display student");
            System.out.println("PRESS 5 to Exit");
            System.out.println("------------------------------");

            System.out.print("Here you go: ");

            int choice = Integer.parseInt(br.readLine());

            if(choice==1){
                //add
                System.out.print("Enter Student Name: ");
                String name = br.readLine();

                System.out.print("Enter Student Phone: ");
                String phone = br.readLine();

                System.out.print("Enter Student City: ");
                String city = br.readLine();

                // create student object to store student
                Student st = new Student(name, phone, city);

                boolean answer = StudentDao.insertStudentToDB(st);
                if(answer){
                    System.out.println("Student Record is Added Successfully");
                } else {
                    System.out.println("Something Went Wrong, Please try Again..");
                }
            }
            else if(choice==2){
                System.out.print("Enter the Student ID: ");
                int id = Integer.parseInt(br.readLine());
                boolean answer = StudentDao.updateExistingStudent(id);

                if(answer){
                    System.out.println("Student Record is Updated Successfully");
                } else {
                    System.out.println("Something Went Wrong, Please try Again..");
                }
            }
            else if(choice==3){
                // delete
                System.out.print("Enter student Id to delete: ");
                int sid = Integer.parseInt(br.readLine());

                boolean answer = StudentDao.deleteStudent(sid);

                if(answer){
                    System.out.println("Student Record is Deleted Successfully");
                } else {
                    System.out.println("Something Went Wrong, Please try Again..");
                }
            }
            else if(choice==4){
                // show all students
                StudentDao.showAllStudent();
            }
            else {
                break;
            }

            System.out.println("Thank You");
            System.out.println("==========================");
            System.out.println();
        }
    }
}
