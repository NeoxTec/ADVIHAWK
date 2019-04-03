import config as config # importa el archivo config

db = config.db # crea un objeto del objeto db creado en config 

'''
Metodo para seleccionar todos los registros de la tabla asesor
'''
def select_asesor():
    try:
        return db.select('asesor') # selecciona todos los registros de la tabla de asesor
    except Exception as e:
        print "Model select_clientes Error ()",format(e.args)
        print "Model select_clientes Message {}",format(e.message)
        return None

'''
Metodo para seleccionar un registro que coincida con el id dado
'''
def select_id_as(id_as):
    try:
        return db.select('asesor', where= 'id_as = $id_as', vars=locals())[0] #selecciona el primer registro que coincida con el nombre
    except Exception as e:
        print "Model select_id_as Error ()",format(e.args)
        print "Model select_id_as Message {}",format(e.message)
        return None

'''
Metodo para insertar un nuevo registro 
'''
def insert_asesor(id_as,correo,habilidades,grado):
    try:
        return db.insert('asesor',id_as=id_as,correo=correo,habilidades=habilidades,grado=grado) # inserta un registro en clientes
    except Exception as e:
        print "Model insert_asesor Error ()",format(e.args)
        print "Model insert_asesor Message {}",format(e.message)
        return None

'''
Metodo para eliminar un registro que coincida con el rfc recibido
'''
def delete_asesor(id_as):
    try:
        return db.delete('asesor', where='id_asesor=$id_asesor', vars=locals()) # borra un registro de personas
    except Exception as e:
        print "Model delete_asesor Error ()",format(e.args)
        print "Model delete_asesor Message {}",format(e.message)
        return None

'''
Metodo para actualizar el nombre,telefono,correo y direccion recibidos
'''
def update_asesor(id_as,correo,habilidades,grado): # actualiza el registro
    try:
            return db.update('clientes',
            correo=correo,
            habilidades=habilidades,
            grado=grado,
            where='id_as=$id_as',
            vars=locals())
    except Exception as e:
        print "Model update_asesor Error ()",format(e.args)
        print "Model update_asesor Message {}",format(e.message)
        return None
