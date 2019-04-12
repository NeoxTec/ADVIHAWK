import web
import app 

db = app.db


def get_all_registro():
    try:
        return db.select('users')
    except Exception as e:
        print "Model get all Error {}".format(e.args)
        print "Model get all Message {}".format(e.message)
        return None


def get_registro(correo):
    try:
        return db.select('users', where='correo=$user', vars=locals())[0]
    except Exception as e:
        print "Model get Error {}".format(e.args)
        print "Model get Message {}".format(e.message)
        return None


def delete_registro(correo):
    try:
        return db.delete('users', where='correo=$user', vars=locals())
    except Exception as e:
        print "Model delete Error {}".format(e.args)
        print "Model delete Message {}".format(e.message)
        return None


def insert_registro(correo,nombre,carrera,grado,tipo):
    try:
        return db.insert('users',
        user=correo,
        nombre=nombre,
        carrera=carrera,
        grado=grado,
        tipo=tipo)
    except Exception as e:
        print "Model insert Error {}".format(e.args)
        print "Model insert Message {}".format(e.message)
        return None


def edit_registro(correo,nombre,carrera,grado,tipo):
    try:
        return db.update('users',correo=correo,
nombre=nombre,
carrera=carrera,
grado=grado,
tipo=tipo,
                  where='correo=$user',
                  vars=locals())
    except Exception as e:
        print "Model update Error {}".format(e.args)
        print "Model updateMessage {}".format(e.message)
        return None
