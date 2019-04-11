# -*- coding:utf-8 -*-
import web
import config as config
import app
"""
    Clase para mostrar cada campo a detalle de los registros en el index
"""
class Editar():
    def __init__(self):
        pass

    def GET(self,num_as):
        asesoria = config.model_asesoria.num_as(num_as)
        return config.render.edit_asesoria(asesoria)

    def POST(self,num_as):
        formulario = web.input() # almacena los datos del formulario web
        num_as = formulario['num_as'] # almacena el rfc del input 
        estado = formulario['estado'] # almacena el telefono del input 
        print num_as
        print estado
        config.model_asesoria.update_asesoria(num_as,estado)
        raise web.seeother('/index_asesoria') # redirecciona al index