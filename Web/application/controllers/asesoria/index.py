# -*- coding:utf-8 -*-
import web 
import config as config
"""
    Clase de Index la cual permite mandar los registros de los clientes al frontend
"""
class Index:
    def __init__(self):
        pass

    def GET(self):
        asesoria = config.model_asesoria.select_asesoria().list() # selecciona todos los registros de clientes
        return config.render.index_asesoria(asesoria) # Envia todos los registros y renderiza index.html


