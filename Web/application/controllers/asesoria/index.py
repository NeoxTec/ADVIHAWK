# -*- coding:utf-8 -*-
import web 
import config as config
import app
"""
    Clase de Index la cual permite mandar los registros frontend
"""
class Index:
    def __init__(self):
        pass

    def GET(self):
        session_user = app.session.user
        session_privilege = app.session.tipo
        params = {}
        params['user'] = session_user
        params['tipo'] = session_privilege
        asesor = session_user
        asesorias = config.model_asesoria.get_asesoria().list()
        return config.render.index_asesoria(asesorias,params) # Envia todos los registros y renderiza index.html