# -*- coding:utf-8 -*-
import web
import config as config
import app
"""
    Clase para mostrar cada campo a detalle de los registros en el index
"""
class Perfil():
    def __init__(self):
        pass
    
    def GET(self,user):
        session_user = app.session.user
        session_privilege = app.session.tipo
        session_picture = app.session.picture
        params = {}
        params['user'] = session_user
        params['tipo'] = session_privilege
        params['picture'] = session_picture
        registro = config.model_users.get_users(session_user)
        asesor = config.model_asesor.get_asesor(params['user'])
        return config.render.perfil(asesor,registro,params)