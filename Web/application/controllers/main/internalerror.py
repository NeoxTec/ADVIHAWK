import application.controllers.main.config  as config
import web
import app


class InternalError:
    
    def __init__(self):
        pass

    def GET(self):
        if app.session.loggedin is True:
            user = app.session.user
            privilege = app.session.tipo
            picture = app.session.picture
            params = {}
            params['user'] = user
            params['tipo'] = privilege
            params['picture'] = picture
        else:
            params = {}
            params['user'] = 'anonymous'
            params['tipo'] = '-1'
            params['picture'] = None

        return config.render.internalerror(params)
