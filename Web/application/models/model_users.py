import web
import app

db = app.db

def validate_user(user):
    try:
        return db.select('users', where='user=$user', vars=locals())[0]
    except Exception as e:
        print(("Model get all Error {}".format(e.args)))
        print(("Model get all Message {}".format(e.message)))
        return None



def get_all_users():
    try:
        return db.select('users') 
    except Exception as e:
        print(("Model get all Error {}".format(e.args)))
        print(("Model get all Message {}".format(e.message)))
        return None


def get_users(user):
    try:
        return db.select('users', where='user=$user', vars=locals())[0] 
    except Exception as e:
        print(("Model get Error {}".format(e.args)))
        print(("Model get Message {}".format(e.message)))
        return None

def edit_grado(user,grado):
    try:
        return db.update('users',
            user=user,
            grado=grado,
            where='user=$user',
            vars=locals())
    except Exception as e:
        print("Model update Error {}".format(e.args))
        print("Model updateMessage {}".format(e.message))
        return None