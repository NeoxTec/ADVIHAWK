# -*- coding:utf-8 -*-
import web 
import config as config
"""
    Clase para poder renderizar y mostrar le contenido del index
"""
class Home:
    def __init__(self):
        pass

    def GET(self):
        return config.render.index() # Renderiza index.html
