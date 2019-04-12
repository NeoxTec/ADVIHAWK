# -*- coding:utf-8 -*-
import web
import application.models.model_asesor as model_asesor # importa el model de personas
import application.models.model_users as model_users
render = web.template.render('application/views/asesor/', base='master') # configura la ubicación de las vistas