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
        return config.render.editar(asesor,registro,params)

    def POST(self,id_as):
        formulario = web.input() # almacena los datos del formulario web
        id_as = formulario['id_as'] # almacena el rfc del input 
        correo = formulario['correo'] # almacena el nombre del input email
        grado = formulario['grado'] #almacena el grado
        habilidades = formulario['habilidades'] # almacena el telefono del input 
        config.model_users.edit_grado(correo,grado)
        config.model_asesor.update_asesor(id_as,correo,habilidades)
        raise web.seeother('/') # redirecciona al index
