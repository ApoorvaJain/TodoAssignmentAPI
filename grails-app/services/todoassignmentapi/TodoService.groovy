package todoassignmentapi

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional
class TodoService {

    def serviceMethod() {
        "helloService"
    }

    def getListAsJson() {
        def responseData = Todo.list().collect{
            [
                    id  :it.id,
                    title :it.title,
                    dueDate: it.dueDate
            ]
        }

        responseData
    }

    def getListItem(Todo todoInstance){
        todoInstance
    }

    def saveTodoInstance(Todo todoInstance){
        if (todoInstance == null) {
            notFound()
            return
        }

        if (todoInstance.hasErrors()) {
            respond todoInstance.errors, view:'create'
            return
        }

        todoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'todo.label', default: 'Todo'), todoInstance.id])
                redirect todoInstance
            }
            '*' { respond todoInstance, [status: CREATED] }
        }
    }

    def editTodoInstance(Todo todoInstance){
        todoInstance
    }

    def updateTodoInstance(Todo todoInstance){
        if (todoInstance == null) {
            notFound()
            return
        }

        if (todoInstance.hasErrors()) {
            respond todoInstance.errors, view:'edit'
            return
        }

        todoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Todo.label', default: 'Todo'), todoInstance.id])
                redirect todoInstance
            }
            '*'{ respond todoInstance, [status: OK] }
        }
    }

    def deleteTodoInstance(Todo todoInstance){
        if (todoInstance == null) {
            notFound()
            return
        }
        todoInstance.delete()

    }
}
