urls = (
    '/', 'application.controllers.main.index.Index',
    '/404', 'application.controllers.main.notfound.NotFound',
    '/500', 'application.controllers.main.internalerror.InternalError',
    "/login", "application.controllers.main.login.Login",
    "/loginselection", "application.controllers.main.loginselection.LoginSelection",
    "/logout", "application.controllers.main.logout.Logout",
    "/auth/(google)", "application.controllers.main.login.AuthPage",
    "/auth/(google)/callback", "application.controllers.main.login.AuthCallbackPage",
    '/registro', 'application.controllers.main.registro.Insert',
    '/index_asesor','application.controllers.asesor.index.Index',
    '/index_asesoria','application.controllers.asesoria.index.Index',
    '/insert_asesoria','application.controllers.asesoria.insert.Insert',
    '/perfil_asesor/(.*)','application.controllers.asesor.perfil.Perfil',
    '/editar_asesor/(.*)','application.controllers.asesor.editar.Editar',
    '/perfil/(.*)','application.controllers.asesor.perfil_e.Perfil',
    '/editar_asesoria/(.*)', 'application.controllers.asesoria.edit.Editar'
   )