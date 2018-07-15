package b2boost


class Partner {

    String companyName

    String ref

    Locale locale

    Date expires

    static constraints = {
        companyName nullable: false, blank: false
        ref unique: true, nullable: false, blank: false
        locale nullable: false, blank : false
        expires nullable:  false, blank : false
    }
}
