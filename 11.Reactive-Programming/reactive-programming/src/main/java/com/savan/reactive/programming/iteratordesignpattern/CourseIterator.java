package com.savan.reactive.programming.iteratordesignpattern;

public class CourseIterator implements Iterator{

    private Course[] courses;

    private int position = 0;

    public CourseIterator(Course[] courses) {
        this.courses = courses;
    }

    @Override
    public boolean hasNext() {
        return position < courses.length && courses[position] != null;
    }

    @Override
    public Object next() {
        return courses[position++];
    }
}
