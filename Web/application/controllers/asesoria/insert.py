import web
import config as config
import app
"""
    Clase para insertar registros a la base de datos por medio de un formulario en la webapp
"""
class Insert:

    def __init__(self):
        pass
    
    def GET(self):
        return config.render.insert_asesoria() # renderiza la pagina insert.html
    
    def POST(self):
        session_user = app.session.user
        id_as = app.session.id_as
        formulario = web.input() # almacena los datos del formulario
        dia = formulario['dia'] # almacena el dia seleccionado en el input
        hora = formulario['hora'] # almacena el nombre escrito en el input
        tema = formulario ['tema'] # almacena el telefono escrito en el input
        config.model_asesoria.insert_asesoria(dia,hora,session_user,id_as,tema) # llama al metodo insert_cliente y le pasa los datos guardados 
        raise web.seeother('/index_asesor') # redirecciona el HTML 
