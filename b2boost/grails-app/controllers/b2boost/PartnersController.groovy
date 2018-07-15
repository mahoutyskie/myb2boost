package b2boost

import grails.converters.JSON
import grails.rest.RestfulController
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus


@CompileStatic
class PartnersController extends RestfulController<Partner> {
	static responseFormats = ['json', 'xml']

    PartnersService partnersService

    PartnersController() {
        super(Partner)
    }

    def index(Integer from, Integer size) {
        def result
        try{
            from = from ? from : 0
            size = size ? size : 2
            result = partnersService.b2SearchByParams(from, size)
        } catch (Exception e){
            internalError()
            return
        }
        render result as JSON

    }


    def show(Long id) {
        def result = partnersService.b2SearchById(id)

        if (!result){
            notFound()
            return
        }
        render result as JSON

    }

    def save() {
        def result
        try{
            def name = request.JSON['name']
            def reference = request.JSON['reference']
            def locale = request.JSON['locale']
            def expiration = request.JSON['expirationTime']
            result = partnersService.b2Save(name, reference, locale, expiration)
            response.status = HttpStatus.CREATED.value()

        }catch (grails.validation.ValidationException e){
            badRequest()
            return
        }catch (Exception e1){
            internalError()
            return
        }

        render result as JSON

    }


    def update(Long id) {
        def result
        try {
            def name = request.JSON['name']
            def reference = request.JSON['reference']
            def locale = request.JSON['locale']
            def expiration = (request.JSON['expirationTime'])
            result = partnersService.b2Update(id, name, reference, locale, expiration)

            if(!result){
                notFound()
                return
            }

        }catch (grails.validation.ValidationException e1){
            badRequest()
            return
        }catch (Exception e){
            internalError()
            return
        }
        render result as JSON

    }



    def delete(Long id) {
        try{
            partnersService.b2Delete(id)

        } catch (NullPointerException e1){
            notFound()
            return
        } catch (Exception e){
            internalError()
            return
        }
        response.status = HttpStatus.OK.value()
        response as JSON

    }


    protected void badRequest() {
        def result
        response.status = HttpStatus.BAD_REQUEST.value()
        result = [code: 400, message: "... A string representing the validation error that occurred ..."]
        render result as JSON
    }

    protected void notFound() {
        def result
        response.status = HttpStatus.NOT_FOUND.value()
        result = [code: 404, message : "Partner with id ... not found."]
        render result as JSON
    }

    protected void internalError() {
        def result
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        result = [code: 500, 'message' : "... whatever internal error happened ..."]
        render result as JSON
    }
}
