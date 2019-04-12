import web
import app 

db = app.db

'''
Metodo para seleccionar todos los registros de la tabla asesoria
'''
def get_asesoria():
    try:
        return db.select('asesorias') # selecciona todos los registros de la tabla de clientes
    except Exception as e:
        print "Model select_asesoria Error ()",format(e.args)
        print "Model select_asesoria Message {}",format(e.message)
        return None

'''
Metodo para mostrar el id_as
'''
def get_id_as(asesor):
    try:
        return db.select('asesor', what="id_as", where='asesor = $asesor') #selecciona el primer registro que coincida con el nombre
    except Exception as e:
        print "Model select_num_as Error ()",format(e.args)
        print "Model select_num_as Message {}",format(e.message)
        return None

'''
Metodo para seleccionar un registro que coincida con el asesor de asesoria dado
'''
def get_solicitado(asesor):
    try:
        return db.select('asesorias', where= 'asesor = $asesor', vars=locals())[0] #selecciona el primer registro que coincida con el nombre
    except Exception as e:
        print "Model select_num_as Error ()",format(e.args)
        print "Model select_num_as Message {}",format(e.message)
        return None

'''
Metodo para seleccionar un registro que coincida con el asesor de asesoria dado
'''
def get_solicitante(solicitante):
    try:
        return db.select('asesorias', where= 'solicitante = $solicitante', vars=locals())[0] #selecciona el primer registro que coincida con el nombre
    except Exception as e:
        print "Model select_num_as Error ()",format(e.args)
        print "Model select_num_as Message {}",format(e.message)
        return None

'''
Metodo para seleccionar un registro que coincida con el solicitante de asesoria dado
'''
def num_as(num_as):
    try:
        return db.select('asesorias', where= 'num_as = $num_as', vars=locals())[0] #selecciona el primer registro que coincida con el nombre
    except Exception as e:
        print "Model select_num_as Error ()",format(e.args)
        print "Model select_num_as Message {}",format(e.message)
        return None

'''
Metodo para insertar un nuevo registro 
'''
def insert_asesoria(dia,hora,solicitante,asesor,tema):
    try:
        return db.insert('asesorias',dia=dia,hora=hora,estado='Pendiente',solicitante=solicitante,asesor=asesor,tema=tema) # inserta un registro en clientes
    except Exception as e:
        print "Model insert_asesoria Error ()",format(e.args)
        print "Model insert_asesoria Message {}",format(e.message)
        return None

'''
Metodo para eliminar un registro que coincida con el rfc recibido
'''
def delete_asesoria(num_as):
    try:
        return db.delete('asesorias', where='num_as=$num_as', vars=locals()) # borra un registro de personas
    except Exception as e:
        print "Model delete_asesoria Error ()",format(e.args)
        print "Model delete_asesoria Message {}",format(e.message)
        return None

'''
Metodo para actualizar el nombre,telefono,correo y direccion recibidos
'''
def update_asesoria(num_as,estado): # actualiza el registro
    try:
            return db.update('asesorias',
            estado=estado,
            where='num_as=$num_as',
            vars=locals())
    except Exception as e:
        print "Model update_asesoria Error ()",format(e.args)
        print "Model update_asesoria Message {}",format(e.message)
        return None

