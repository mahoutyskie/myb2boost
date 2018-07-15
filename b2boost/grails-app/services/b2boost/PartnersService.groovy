package b2boost

import grails.transaction.Transactional

import java.time.Instant
import java.time.OffsetDateTime

@Transactional
class PartnersService {

    def b2SearchByParams(Integer from, Integer size){
        Partner.list(max: size, offset: from)
    }

    def b2SearchById(Long id){
        Partner.findById(id)
    }

    def b2Save(def name, def ref, def locale ,def expiration){
        def partner = new Partner(companyName: name, ref: ref, locale: locale, expires: parseStrDateToDateTZ(expiration))
        partner.save(flush: true, failOnError: true)

    }

    def b2Delete(Long id){
        def partner = Partner.get(id)
        partner.delete(flush: true, failOnError: true)

    }

    def b2Update(Long id, def name, def ref, def locale ,def expiration){
        def partners = Partner.get(id)
        if(!partners)
            return

        partners.companyName = name
        partners.ref = ref
        partners.locale = new Locale(locale)
        partners.expires = parseStrDateToDateTZ(expiration)

        partners.save(flush:true, failOnError: true)

    }


    Date parseStrDateToDateTZ(def jsonStrDate){
        OffsetDateTime odt = OffsetDateTime.parse( jsonStrDate )
        Instant instant = odt.toInstant()
        java.util.Date date = java.util.Date.from(instant)
    }

}
