package iteratordesignpattern;

public class App {

    public static void main(String[] args) {

        CourseRepository courseRepository = new CourseRepository();
        courseRepository.addCourse(new Course("Spring Boot"));
        courseRepository.addCourse(new Course("Java"));
        courseRepository.addCourse(new Course("Microservices"));
        courseRepository.addCourse(new Course("Design Pattern"));

        Iterator iterator = courseRepository.createIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
