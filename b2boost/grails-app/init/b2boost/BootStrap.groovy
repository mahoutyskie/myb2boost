package b2boost

class BootStrap {

    InitMockService initMockService

    def init = { servletContext ->
        initMockService.dataB2Mock()
    }

    def destroy = {
    }
}
