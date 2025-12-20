package iteratordesignpattern;

public class CourseRepository implements Collection{

    private Course[] courses = new Course[10];

    private int index;

    public void addCourse(Course course) {
        courses[index++] = course;
    }

    @Override
    public Iterator createIterator(){
        return new CourseIterator(courses);
    }

    // Through inner class
    // 1. Do not user CourseIterator
    // 2. Un-comment the below code
    /*
    private class CourseIterator implements Iterator {

        int position = 0;

        @Override
        public boolean hasNext() {
            return position < courses.length && courses[position] != null;
        }

        @Override
        public Object next() {
            return courses[position++];
        }
    }
     */
}
