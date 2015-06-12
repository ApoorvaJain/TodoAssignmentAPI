package todoassignmentapi

class Todo {
    String title
    Date dueDate

    static constraints = {
        title blank: false
    }
}
