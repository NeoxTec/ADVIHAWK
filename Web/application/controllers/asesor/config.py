# -*- coding:utf-8 -*-
import web
import application.models.model_asesor as model_asesor # importa el model de personas
render = web.template.render('application/views/asesor/', base='master') # configura la ubicación de las vistas