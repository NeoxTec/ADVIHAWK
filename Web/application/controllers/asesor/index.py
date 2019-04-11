# -*- coding:utf-8 -*-
import web 
import config as config
import app
"""
    Clase de Index la cual permite mandar los registros de los clientes al frontend
"""
class Index:
    def __init__(self):
        pass

    def GET(self):
        session_user = app.session.user
        session_privilege = app.session.tipo
        session_picture = app.session.picture
        params = {}
        params['user'] = session_user
        params['tipo'] = session_privilege
        params['picture'] = session_picture
        asesor = config.model_asesor.get_all_asesor().list() # selecciona todos los registros de clientes
        return config.render.index_asesor(asesor,params) # Envia todos los registros y renderiza index.html


