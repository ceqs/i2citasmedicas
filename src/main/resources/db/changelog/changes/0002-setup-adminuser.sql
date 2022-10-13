-- Insertamos roles
INSERT INTO ROLES(id_rol, nombre, descripcion) values('ADMINISTRADOR', 'Administrador', 'Rol de administrador del sistema');

-- CREAMOS UN USUARIO PACIENTE
INSERT INTO USUARIOS(usuario, password, id_rol) VALUE('ADMIN', '$2a$10$ufZjORdgB5ltNpqniwMY3OBR73GL03Y7/THmj9bkwDRR.RLQzuIeW', 'ADMINISTRADOR');