package b2boost

import grails.transaction.Transactional
import org.grails.web.util.WebUtils

@Transactional
class InitMockService {


    def dataB2Mock(){
        if (grails.util.Environment.current == grails.util.Environment.DEVELOPMENT) {
            new Partner(companyName: 'b21', ref: 'b1', locale: new Locale('es','ES'), expires: new Date()).save()
            new Partner(companyName: 'b22', ref: 'b2', locale: new Locale('gb','GB'), expires: new Date()).save()
            new Partner(companyName: 'b23', ref: 'b3', locale: new Locale('fr','FR'), expires: new Date()).save()
            new Partner(companyName: 'b24', ref: 'b4', locale: new Locale('pl','PL'), expires: new Date()).save()

        }

    }
}
