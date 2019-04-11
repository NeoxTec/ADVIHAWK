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
    
    def GET(self,id_as):
        session_user = app.session.user
        session_privilege = app.session.tipo
        session_picture = app.session.picture
        app.session.id_as = id_as
        params = {}
        params['user'] = session_user
        params['tipo'] = session_privilege
        params['picture'] = session_picture
        params['id_as'] = app.session.id_as
        print app.session.id_as
        asesor = config.model_asesor.select_id_as(id_as) # Selecciona el registro que coincida con el nombre
        return config.render.perfil_asesor(asesor,params) # Envia el registro y renderiza el view.html
