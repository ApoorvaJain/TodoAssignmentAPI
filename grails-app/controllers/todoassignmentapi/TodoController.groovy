package todoassignmentapi

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional()
class TodoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", ping: "OPTIONS"]
    static responseFormats = ['json', 'xml']

    def TodoService

    def ping(){
        response.status = 200
    }

    def index() {

        respond TodoService.getListAsJson()
    }

    def show(Todo todoInstance) {
        respond TodoService.getListItem(todoInstance)
    }

    def create() {
        respond new Todo(params)
    }

    @Transactional
    def save(Todo todoInstance) {
        TodoService.saveTodoInstance(todoInstance)
    }

    def edit(Todo todoInstance) {
        respond TodoService.editTodoInstance(todoInstance)
    }

    @Transactional
    def update(Todo todoInstance) {
        TodoService.updateTodoInstance(todoInstance)
    }


    def delete(Todo todoInstance) {
        try{
            TodoService.deleteTodoInstance(todoInstance)
            respond (['success' : true] as JSON)
        }catch (Exception e){
            println(e.message)
            render (['success' : false] as JSON)
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'todo.label', default: 'Todo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
