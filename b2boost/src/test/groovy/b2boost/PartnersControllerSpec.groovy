package b2boost

import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(PartnersController)
class PartnersControllerSpec extends Specification {


    def baseURL = "http://localhost:8080/api/partners"
    RestBuilder restBuilder = new RestBuilder()

    def "Get partners collection"() {
        given:
            restBuilder
        when: "execution request"
            def response = restBuilder.get("${baseURL}/")
        then: "List the partners collection"
            response.status == 200
    }

    def "Get a given Partner by id"() {
        given:
            def ID = 3
            restBuilder
        when: 'Pasisng the member id '
            def response = restBuilder.get("${baseURL}/${ID}")
        then: 'Get the member object on given id'
            response.status == 200
    }

    def "Get a given Partner by wrong id"() {
        given:
        def ID = 899
        restBuilder
        when: 'Passing the member id not existed'
        def response = restBuilder.get("${baseURL}/${ID}")
        then: 'Get the member object on given id'
        response.status == 404
    }

    def "Create a Partner resource"() {
        given:
            restBuilder
        when: 'Sending request'
            def response = restBuilder.post("${baseURL}/") {
                header("Content-Type", "application/json");
                json(
                            "name"              : "Bells & Whistles",
                            "reference"         : "xxxxxx",
                            "locale"            : "en_ES",
                            "expirationTime"    : "2017-10-03T12:18:46+00:00"
                )
            }
        then: 'Create the member and return the member'
            response.status == 201
    }

    def "Create an existed Partner resource"() {
        given:
        restBuilder
        when: 'Sending request'
            def response = restBuilder.post("${baseURL}/") {
                header("Content-Type", "application/json");
                json(
                        "name"              : "Bells & Whistles",
                        "reference"         : "xxxxxx",
                        "locale"            : "en_ES",
                        "expirationTime"    : "2017-10-03T12:18:46+00:00"
                )
            }
        then: 'A validation error occurred or partner already exists'
            response.status == 400
    }

    def "Update an existed Partner resource"() {
        given:
            def ID = 5
            restBuilder
        when: 'Sending request'
            def response = restBuilder.put("${baseURL}/${ID}") {
                header("Content-Type", "application/json");
                json(
                        "name"              : "Bells & Whistles",
                        "reference"         : "yyyyyy",
                        "locale"            : "en_ES",
                        "expirationTime"    : "2017-10-03T12:18:46+00:00"
                )
            }
        then: 'The Partner entity was modified in the database'
            response.status == 200
    }

    def "Update an existed Partner resource - Not Found"() {
        given:
        def ID = 899
        restBuilder
        when: 'Sending request'
        def response = restBuilder.put("${baseURL}/${ID}") {
            header("Content-Type", "application/json");
            json(
                    "name"              : "Bells & Whistles",
                    "reference"         : "yyyyyy",
                    "locale"            : "en_ES",
                    "expirationTime"    : "2017-10-03T12:18:46+00:00"
            )
        }
        then: 'No partner of given id could be found'
        response.status == 404
    }

    def "Delet a a given Partner"() {
        given:
        def ID = 5
        restBuilder
        when: 'Sending request'
        def response = restBuilder.delete("${baseURL}/${ID}")
        then: 'The partner with the given id has been deleted'
        response.status == 200
    }

    def "Delet a a given Partner - Not Found"() {
        given:
        def ID = 899
        restBuilder
        when: 'Sending request'
        def response = restBuilder.delete("${baseURL}/${ID}")
        then: 'No partner of given id could be found'
        response.status == 404
    }
}
