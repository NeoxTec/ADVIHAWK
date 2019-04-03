import web

urls=('/login','application.controllers.login.login.Login')

render = web.template.render('application/views/errores/', base = 'master2')

if __name__ == "__main__": # metodo principal, doble guion = private
    app = web.application(urls, globals())
    web.config.debug = False # Hide debug print
    def NotFound(): # metodo para renderizar una vista notFound en caso de presenatarse el error 404
        return web.notfound(render.notfound())
    app.notfound = NotFound
    def internalerror(): # metodo para renderizar una vista Internalerror en caso de presenatarse el error 500
        return web.internalerror(render.internal())
    app.internalerror = internalerror
    app.run()
