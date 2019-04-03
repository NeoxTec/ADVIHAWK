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
        asesor = config.model_asesor.select_asesor().list() # selecciona todos los registros de clientes
        return config.render.index_asesor(asesor) # Envia todos los registros y renderiza index.html


