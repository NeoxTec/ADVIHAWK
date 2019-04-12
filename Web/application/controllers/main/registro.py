import config as config
import web
import app


class Insert():

    def __init__(self):
        pass

    def GET(self):
        message = "Porfavor registrate para poder hacer uso de la plataforma" 
        return config.render.registro(message) # render insert.html

    def POST(self):
        formulario = web.input() # get form data
        # call model insert_registro and try to insert new data
        correo = formulario['user']
        nombre= formulario['nombre']
        carrera = formulario['carrera']
        grado = formulario['grado']
        tipo = formulario['tipo']
        config.model_registro.insert_registro(correo,nombre,carrera,grado,tipo)
        raise web.seeother('/') # render registro index.html