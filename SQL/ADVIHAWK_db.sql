Create database ADVIHAWK;
use ADVIHAWK;

CREATE TABLE IF NOT EXISTS users(
    user VARCHAR(70) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    carrera ENUM('tic','dn','en','mec','nano','in','conta','enf','crimi','tf','sr','di') NOT NULL,
    grado VARCHAR(50) NOT NULL,
    tipo TINYINT(3) NOT NULL,
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO users(user,nombre,carrera,grado,tipo) 
VALUES
('dieloxes@gmail.com','Diego Bolaños Pardo','tic',5,0),
('1717110635@gmail.com','Mario Alberto Nieto Lopez','tic',3,1);

CREATE TABLE sessions(
    session_id char(128) UNIQUE NOT NULL,
    atime timestamp NOT NULL default current_timestamp,
    data text
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

Create table Asesor(
    id_as int(8) unsigned auto_increment primary key,
    correo varchar(70) not null,
    horario blob null,
    habilidades text(250)null,
    validado enum('Si','No') not null default('No'),
    constraint fk_asesor foreign key (correo) references users(user)
);

insert into asesor(correo, habilidades, validado) values
('correo1@prueba.com','programacion, base de datos, algoritmos',2),
('correo2@prueba.com','programacion, redes, algoritmos',1),
('correo4@prueba.com','calculo, electricidad, programación',1),
('correo5@prueba.com','quimica, calculo,algebra, física',2);

/* 
+-------+--------------------+---------+-----------------------------------------+----------+
| id_as | correo             | horario | habilidades                             | validado |
+-------+--------------------+---------+-----------------------------------------+----------+
|     1 | correo1@prueba.com | NULL    | programacion, base de datos, algoritmos | No       |
|     2 | correo2@prueba.com | NULL    | programacion, redes, algoritmos         | Si       |
|     3 | correo4@prueba.com | NULL    | calculo, electricidad, programación     | Si       |
|     4 | correo5@prueba.com | NULL    | quimica, calculo,algebra, física        | No       |
+-------+--------------------+---------+-----------------------------------------+----------+
*/

select id_as, nombre, apellidos, asesor.correo,validado, habilidades from asesor join registro on asesor.correo = registro.correo;
-- nombre apellido, id, asesor, validado, habilidades por asesor.
/* Detalle de asesores(nombre mas datos generales)
+-------+---------------+---------------+--------------------+----------+-----------------------------------------+
| id_as | nombre        | apellidos     | correo             | validado | habilidades                             |
+-------+---------------+---------------+--------------------+----------+-----------------------------------------+
|     1 | Peter         | Parker        | correo1@prueba.com | No       | programacion, base de datos, algoritmos |
|     2 | James         | Logan         | correo2@prueba.com | Si       | programacion, redes, algoritmos         |
|     3 | Natasha       | Romanoff      | correo5@prueba.com | Si       | calculo, electricidad, programación     |
|     4 | Matt          | Murdock       | correo4@prueba.com | No       | quimica, calculo,algebra, física        |
+-------+---------------+---------------+--------------------+----------+-----------------------------------------+
*/
--  Vista
Create View detalle_asesor AS SELECT a.id_as, r.nombre as n_asesor,r.apellidos as ape_asesor, a.correo, a.validado,a.habilidades from registro r, asesor a where a.correo = r.correo;
-- Consulta 
select * from detalle_asesor;

create table asesorias(
    num_as int(10) unsigned auto_increment primary key,
    dia date not null,
    hora time not null,
    estado enum('Pendiente','Rechazado','Aceptado','Finalizado'),
    solicitante varchar(50) not null, 
    asesor int(8) unsigned not null,
    tema text(40) not null,
    constraint fk_idas foreign key (asesor) references Asesor(id_as),
    constraint fk_asesorado foreign key (solicitante) references users(user)
);

insert into asesorias(solicitante, asesor,dia,estado,hora,tema)values
('correo3@prueba.com',1,'2019-04-06',2,'8:00','Correccion de Base de datos'),
('correo10@prueba.com',1,'2019-04-06',4,'14:30','Serie Fibonacci'),
('correo9@prueba.com',2,'2019-04-06',4,'15:00','¿Qué es el IPv6?'),
('correo10@prueba.com',4,'2019-04-07',4,'9:00','Algebra Lineal'),
('correo10@prueba.com',3,'2019-04-08',3,'11:00','Electromagnetismo'),
('correo3@prueba.com',1,'2019-04-08',3,'13:00','Modelo E-R Base de datos'),
('correo3@prueba.com',1,'2019-04-09',1,'14:00','Envio de correos'),
('correo9@prueba.com',2,'2019-04-09',1,'14:00','Algoritmo con variables de opcion'),
('correo10@prueba.com',4,'2019-04-10',3,'15:00','Nomenclatura');
/*
+--------+------------+----------+------------+---------------------+--------+-----------------------------------+
| num_as | dia        | hora     | estado     | solicitante         | asesor | tema                              |
+--------+------------+----------+------------+---------------------+--------+-----------------------------------+
|      1 | 2019-04-06 | 08:00:00 | Rechazado  | correo3@prueba.com  |      1 | Correccion de Base de datos       |
|      2 | 2019-04-06 | 14:30:00 | Finalizado | correo10@prueba.com |      1 | Serie Fibonacci                   |
|      3 | 2019-04-06 | 15:00:00 | Finalizado | correo9@prueba.com  |      2 | ¿Qué es el IPv6?                  |
|      4 | 2019-04-07 | 09:00:00 | Finalizado | correo10@prueba.com |      4 | Algebra Lineal                    |
|      5 | 2019-04-08 | 11:00:00 | Aceptado   | correo10@prueba.com |      3 | Electromagnetismo                 |
|      6 | 2019-04-08 | 13:00:00 | Aceptado   | correo3@prueba.com  |      1 | Modelo E-R Base de datos          |
|      7 | 2019-04-09 | 14:00:00 | Pendiente  | correo3@prueba.com  |      1 | Envio de correos                  |
|      8 | 2019-04-09 | 14:00:00 | Pendiente  | correo9@prueba.com  |      2 | Algoritmo con variables de opcion |
|      9 | 2019-04-10 | 15:00:00 | Aceptado   | correo10@prueba.com |      4 | Nomenclatura                      |
+--------+------------+----------+------------+---------------------+--------+-----------------------------------+
*/
select num_as,nombre as n_sol,apellidos as ape_sol, solicitante as mail_sol, dia, hora, estado, tema,n_asesor as nom_as,ape_asesor as ape_as from asesorias inner join detalle_asesor inner join registro where registro.correo = solicitante and asesor = id_as ;
/* consulta cuyo detalles muestra datos de la asesoria junto con nombre del asesorado y del asesor.
+--------+-------+---------+---------------------+------------+----------+------------+-----------------------------------+---------+----------+
| num_as | n_sol | ape_sol | mail_sol            | dia        | hora     | estado     | tema                              | nom_as  | ape_as   |
+--------+-------+---------+---------------------+------------+----------+------------+-----------------------------------+---------+----------+
|      1 | Susan | Storm   | correo3@prueba.com  | 2019-04-06 | 08:00:00 | Rechazado  | Correccion de Base de datos       | Peter   | Parker   |
|      2 | Kitty | Pride   | correo10@prueba.com | 2019-04-06 | 14:30:00 | Finalizado | Serie Fibonacci                   | Peter   | Parker   |
|      3 | Bruce | Banner  | correo9@prueba.com  | 2019-04-06 | 15:00:00 | Finalizado | ¿Qué es el IPv6?                  | James   | Logan    |
|      4 | Kitty | Pride   | correo10@prueba.com | 2019-04-07 | 09:00:00 | Finalizado | Algebra Lineal                    | Matt    | Murdock  |
|      5 | Kitty | Pride   | correo10@prueba.com | 2019-04-08 | 11:00:00 | Aceptado   | Electromagnetismo                 | Natasha | Romanoff |
|      6 | Susan | Storm   | correo3@prueba.com  | 2019-04-08 | 13:00:00 | Aceptado   | Modelo E-R Base de datos          | Peter   | Parker   |
|      7 | Susan | Storm   | correo3@prueba.com  | 2019-04-09 | 14:00:00 | Pendiente  | Envio de correos                  | Peter   | Parker   |
|      8 | Bruce | Banner  | correo9@prueba.com  | 2019-04-09 | 14:00:00 | Pendiente  | Algoritmo con variables de opcion | James   | Logan    |
|      9 | Kitty | Pride   | correo10@prueba.com | 2019-04-10 | 15:00:00 | Aceptado   | Nomenclatura                      | Matt    | Murdock  |
+--------+-------+---------+---------------------+------------+----------+------------+-----------------------------------+---------+----------+
*/
-- Vista creada con detalles de consulta anterior.
Create view detalle_asesoria AS SELECT a.num_as, r.nombre as n_sol,r.apellidos as ap_sol,a.solicitante as correo_solicitante, a.dia,a.hora,a.estado,a.tema, ad.n_asesor as nom_as, ad.ape_asesor as ape_as from registro r, asesorias a, detalle_asesor ad where r.correo = a.solicitante and a.asesor = ad.id_as;
Select * from detalle_asesoria;

create table Valor_ase(
    num int(8) unsigned auto_increment primary key,
    valor double(4,2) not null,
    asesoria int(10) unsigned not null,
    constraint fk_valorar foreign key (asesoria) references asesorias(num_as)
);
insert into valor_ase(valor,asesoria) values
(8,2),
(10,3),
(9,4);

/*
+-----+-------+----------+
| num | valor | asesoria |
+-----+-------+----------+
|   1 |  8.00 |        2 |
|   2 | 10.00 |        3 |
|   3 |  9.00 |        4 |
+-----+-------+----------+
*/
-- Consulta para vista --
 select asesoria,dia,nombre as nom_asesor,apellidos as ape_asesor,hora,tema,valor from valor_ase inner join asesorias inner join asesor inner join registro where num_as = asesoria and asesor = id_as and asesor.correo = registro.correo;
/* Vista para consultas de asesorias
+----------+------------+------------+------------+----------+------------------+-------+
| asesoria | dia        | nom_asesor | ape_asesor | hora     | tema             | valor |
+----------+------------+------------+------------+----------+------------------+-------+
|        2 | 2019-04-06 | Peter      | Parker     | 14:30:00 | Serie Fibonacci  |  8.00 |
|        3 | 2019-04-06 | James      | Logan      | 15:00:00 | ¿Qué es el IPv6? | 10.00 |
|        4 | 2019-04-07 | Matt       | Murdock    | 09:00:00 | Algebra Lineal   |  9.00 |
+----------+------------+------------+------------+----------+------------------+-------+
*/
create view calificacion as select c.num,c.asesoria,a.tema,a.dia,a.hora,r.nombre as nom_asesor,r.apellidos as ape_asesor,c.valor from asesor ad, asesorias a, valor_ase c, registro r where a.num_as = c.asesoria and a.asesor = ad.id_as and ad.correo = r.correo;

select * from calificacion;
-- Muestra los mismos datos anteriores pero en una tabla nueva(vista)
create table Validacion(
    num_val int(6) unsigned auto_increment primary key,
    Tipo enum('Si','No') not null,
    ase int(8) unsigned not null,
    prof varchar(50) not null,
    constraint fk_ase foreign key (ase) references Asesor(id_as),
    constraint fk_prof foreign key (prof) references users(user)
);

create table Certificaciones(
    num_cert int(8) unsigned auto_increment primary key,
    nombre varchar(90) not null, 
    tipo enum('Certificado','Diplomado') not null,
    descripcion text(255) not null,
    imagen blob null,
    id_asesor int(8) unsigned not null,
    constraint fk_certificado foreign key (id_asesor) references Asesor(id_as)
);

insert into certificaciones(nombre,tipo,descripcion,id_asesor) values
('Unix',1,'Conocimiento de uso de terminales tipo Unix',1),
('Unix',1,'Conocimiento de uso de terminales tipo Unix',2),
('Cisco',1,'Conocimiento y manejo de routing y switching',2);
/*
+----------+--------+-------------+----------------------------------------------+--------+-----------+
| num_cert | nombre | tipo        | descripcion                                  | imagen | id_asesor |
+----------+--------+-------------+----------------------------------------------+--------+-----------+
|        1 | Unix   | Certificado | Conocimiento de uso de terminales tipo Unix  | NULL   |         1 |
|        2 | Unix   | Certificado | Conocimiento de uso de terminales tipo Unix  | NULL   |         2 |
|        3 | Cisco  | Certificado | Conocimiento y manejo de routing y switching | NULL   |         2 |
+----------+--------+-------------+----------------------------------------------+--------+-----------+
*/

CREATE USER 'advihawk'@'localhost' IDENTIFIED BY 'advihawk.2019';
GRANT ALL PRIVILEGES ON ADVIHAWK.* TO 'advihawk'@'localhost';
FLUSH PRIVILEGES;
