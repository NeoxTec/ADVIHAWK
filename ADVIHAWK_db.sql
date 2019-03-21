Create database ADVIHAWK;
use ADVIHAWK;

create table Registro(
    correo varchar(50) primary key not null,
    pass varchar(25) not null,
    nombre varchar(50) not null,
    apellidos varchar(50) not null,
    carrera enum('Contaduria','Criminalistica','Desarrollo e inovacion empresarial','Diseño digital','Energias renovables','Enfermeria','Industrial','Mecatronica','Nanotecnologia','Terapia Fisica','Tecnologias de la informacion y comunicacion','Salud reproductiva') not null,
    foto blob null,
    tipo enum('Regular','Asesor','Profesor') not null
);

Create table Regular(
    id_al int(8) unsigned auto_increment primary key,
    correo varchar(50) not null,
    grado varchar(20) not null,
    constraint fk_estandar foreign key (correo) references Registro(correo)
);

Create table Asesor(
    id_as int(8) unsigned auto_increment primary key,
    correo varchar(50) not null,
    horario blob null,
    habilidades text(250)null,
    grado varchar(20) not null,
    validado enum('Si','No') not null default('No'),
    constraint fk_asesor foreign key (correo) references Registro(correo)
);

create table Profesor(
    id_prof int(8) unsigned auto_increment primary key,
    correo varchar(50) not null,
    constraint fk_profesor foreign key (correo) references Registro(correo)
);

create table Asesorias(
    num_as int(10) unsigned auto_increment primary key,
    dia date not null,
    hora time not null,
    estado enum('Pendiente','Rechazado','Aceptado','Finalizado'),
    solicitante varchar(50) not null, 
    asesor int(8) unsigned not null,
    tema text(40) not null,
    constraint fk_idas foreign key (asesor) references Asesor(id_as),
    constraint fk_asesorado foreign key (solicitante) references Registro(correo)
);

create table Valor_ase(
    num int(8) unsigned auto_increment primary key,
    valor double(4,2) not null,
    asesoria int(10) unsigned not null,
    constraint fk_valorar foreign key (asesoria) references asesorias(num_as)
);

create table Validacion(
    num_val int(6) unsigned auto_increment primary key,
    Tipo enum('Si','No') not null,
    ase int(8) unsigned not null,
    prof int(8) unsigned not null,
    constraint fk_ase foreign key (ase) references Asesor(id_as),
    constraint fk_prof foreign key (prof) references Profesor(id_prof)
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

CREATE USER 'advihawk'@'localhost' IDENTIFIED BY 'ADVI.2019';
GRANT ALL PRIVILEGES ON ADVIHAWK.* TO 'advihawk'@'localhost';
FLUSH PRIVILEGES;