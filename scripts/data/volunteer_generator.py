import json
import sys

import numpy as np

# Nombres de mujeres
nombres_mujeres = np.array([
    'MARIA CARMEN', 'MARIA', 'CARMEN', 'ANA MARIA', 'LAURA', 'MARIA PILAR', 'MARIA DOLORES', 'ISABEL',
    'JOSEFA', 'MARIA TERESA', 'ANA', 'MARTA', 'CRISTINA', 'MARIA ANGELES', 'LUCIA', 'MARIA ISABEL',
    'MARIA JOSE', 'FRANCISCA', 'ANTONIA', 'DOLORES', 'PAULA', 'SARA', 'ELENA', 'MARIA LUISA', 'RAQUEL',
    'ROSA MARIA', 'MANUELA', 'MARIA JESUS', 'PILAR', 'CONCEPCION', 'MERCEDES', 'JULIA', 'BEATRIZ', 'ALBA',
    'SILVIA', 'NURIA', 'IRENE', 'PATRICIA', 'ROSARIO', 'JUANA', 'ROCIO', 'ANDREA', 'TERESA', 'ENCARNACION',
    'MONTSERRAT', 'MONICA', 'ALICIA', 'MARIA MAR', 'SANDRA', 'SONIA', 'MARINA', 'SUSANA', 'ANGELA',
    'NATALIA', 'ROSA', 'YOLANDA', 'SOFIA', 'CLAUDIA', 'MARGARITA', 'MARIA JOSEFA', 'CARLA', 'EVA',
    'INMACULADA', 'MARIA ROSARIO', 'MARIA MERCEDES', 'ANA ISABEL', 'NOELIA', 'ESTHER', 'VERONICA',
    'DANIELA', 'NEREA', 'CAROLINA', 'INES', 'MIRIAM', 'EVA MARIA', 'MARTINA', 'MARIA VICTORIA',
    'LORENA', 'ANGELES', 'ANA BELEN', 'MARIA ELENA', 'MARIA ROSA', 'VICTORIA', 'MARIA CONCEPCION',
    'ALEJANDRA', 'AMPARO', 'MARIA ANTONIA', 'LIDIA', 'CELIA', 'CATALINA', 'FATIMA', 'MARIA NIEVES',
    'AINHOA', 'OLGA', 'CLARA', 'CONSUELO', 'MARIA CRISTINA', 'ADRIANA', 'GLORIA', 'MARIA SOLEDAD'
])

# Nombres de hombres
nombres_hombres = np.array([
    'ANTONIO', 'MANUEL', 'JOSE', 'FRANCISCO', 'DAVID', 'JUAN', 'JAVIER', 'DANIEL', 'JOSE ANTONIO',
    'FRANCISCO JAVIER', 'JOSE LUIS', 'CARLOS', 'ALEJANDRO', 'JESUS', 'MIGUEL', 'JOSE MANUEL',
    'MIGUEL ANGEL', 'RAFAEL', 'PABLO', 'PEDRO', 'ANGEL', 'SERGIO', 'FERNANDO', 'JOSE MARIA',
    'JORGE', 'LUIS', 'ALBERTO', 'ALVARO', 'JUAN CARLOS', 'ADRIAN', 'DIEGO', 'JUAN JOSE', 'RAUL',
    'IVAN', 'RUBEN', 'JUAN ANTONIO', 'OSCAR', 'ENRIQUE', 'RAMON', 'ANDRES', 'JUAN MANUEL',
    'SANTIAGO', 'VICENTE', 'MARIO', 'VICTOR', 'JOAQUIN', 'EDUARDO', 'ROBERTO', 'MARCOS', 'JAIME',
    'FRANCISCO JOSE', 'HUGO', 'IGNACIO', 'JORDI', 'ALFONSO', 'RICARDO', 'SALVADOR', 'MARC',
    'GUILLERMO', 'MOHAMED', 'GABRIEL', 'MARTIN', 'GONZALO', 'EMILIO', 'JOSE MIGUEL', 'JULIO',
    'JULIAN', 'TOMAS', 'NICOLAS', 'AGUSTIN', 'LUCAS', 'SAMUEL', 'JOSE RAMON', 'ISMAEL', 'CRISTIAN',
    'JOAN', 'AITOR', 'FELIX', 'HECTOR', 'ALEX', 'IKER', 'JUAN FRANCISCO', 'JOSE CARLOS', 'MATEO',
    'SEBASTIAN', 'CESAR', 'JOSEP', 'ALFREDO', 'MARIANO', 'RODRIGO', 'JOSE ANGEL', 'DOMINGO',
    'VICTOR MANUEL', 'JOSE IGNACIO', 'FELIPE', 'LUIS MIGUEL', 'JOSE FRANCISCO', 'XAVIER', 'ALBERT',
    'JUAN LUIS'
])

# Apellidos
apellidos = np.array([
    'GARCIA', 'RODRIGUEZ', 'GONZALEZ', 'FERNANDEZ', 'LOPEZ', 'MARTINEZ', 'SANCHEZ', 'PEREZ',
    'GOMEZ', 'MARTIN', 'JIMENEZ', 'HERNANDEZ', 'RUIZ', 'DIAZ', 'MORENO', 'ALVAREZ',
    'ROMERO', 'GUTIERREZ', 'ALONSO', 'NAVARRO', 'TORRES', 'DOMINGUEZ', 'RAMOS', 'VAZQUEZ',
    'RAMIREZ', 'GIL', 'SERRANO', 'MORALES', 'MOLINA', 'BLANCO', 'SUAREZ', 'CASTRO', 'ORTEGA',
    'DELGADO', 'ORTIZ', 'MARIN', 'RUBIO', 'MEDINA', 'SANZ', 'CASTILLO', 'IGLESIAS',
    'CORTES', 'GARRIDO', 'SANTOS', 'GUERRERO', 'LOZANO', 'CANO', 'CRUZ', 'MENDEZ', 'FLORES',
    'PRIETO', 'HERRERA', 'LEON', 'MARQUEZ', 'CABRERA', 'GALLEGO', 'CALVO', 'VIDAL',
    'CAMPOS', 'REYES', 'VEGA', 'FUENTES', 'CARRASCO', 'DIEZ', 'AGUILAR', 'CABALLERO', 'NIETO',
    'SANTANA', 'VARGAS', 'PASCUAL', 'GIMENEZ', 'HERRERO', 'HIDALGO', 'MONTERO', 'LORENZO',
    'SANTIAGO', 'BENITEZ', 'DURAN', 'ARIAS', 'MORA', 'FERRER', 'CARMONA', 'VICENTE',
    'ROJAS', 'SOTO', 'CRESPO', 'ROMAN', 'PASTOR', 'VELASCO', 'PARRA', 'SAEZ', 'MOYA', 'BRAVO',
    'RIVERA', 'GALLARDO', 'SOLER', 'ESTEBAN', 'SILVA', 'FRANCO', 'PARDO', 'RIVAS', 'LARA',
    'MERINO', 'ESPINOSA', 'MENDOZA', 'CAMACHO', 'VERA', 'RIOS', 'IZQUIERDO', 'ARROYO', 'CASADO',
    'SIERRA', 'REDONDO', 'LUQUE', 'MONTES', 'REY', 'GALAN', 'CARRILLO', 'OTERO', 'SEGURA',
    'HEREDIA', 'BERNAL', 'MARCOS', 'SORIANO', 'ROBLES', 'CONTRERAS', 'MARTI', 'PALACIOS',
    'VALERO', 'GUERRA', 'MACIAS', 'VILA', 'VARELA', 'PEREIRA', 'EXPOSITO', 'MIRANDA', 'ROLDAN',
    'BENITO', 'MATEO', 'BUENO', 'ANDRES', 'GUILLEN', 'VILLAR', 'AGUILERA', 'SALAZAR', 'ACOSTA',
    'ESCOBAR', 'BLAZQUEZ', 'PACHECO', 'MANZANO', 'VILLANUEVA', 'SANTAMARIA', 'ROCA', 'COSTA',
    'RUEDA', 'SERRA', 'CUESTA', 'MIGUEL', 'MESA', 'TOMAS', 'LUNA', 'DE LA FUENTE',
    'MALDONADO', 'SIMON', 'ALARCON', 'ZAMORA', 'MILLAN', 'PAREDES', 'LAZARO'
])

# Verificar si se proporcionó un argumento para activar el atributo de género
generar_genero = False
if len(sys.argv) > 1 and sys.argv[1].lower() == 'gender=true':
    generar_genero = True


# Generar un valor aleatorio para firstName en el formato "Nombre"
def generar_nombre():
    nombres = np.concatenate((nombres_mujeres, nombres_hombres))
    nombre = np.random.choice(nombres)
    nombre = nombre.title().split(" ")[0]  # Convertir primera letra en mayúscula y obtener primer nombre
    return nombre

# Generar un valor aleatorio para lastName en el formato "Apellido1 Apellido2"
def generar_apellido():
    apellido1 = np.random.choice(apellidos).title()
    apellido2 = np.random.choice(apellidos).title()
    apellido = apellido1 + " " + apellido2
    return apellido

# Generar un valor aleatorio para startDate en el formato "YYYY-MM-DD"
def generar_start_date():
    year = np.random.choice([2021, 2022])
    month = np.random.choice([2, 9])
    start_date = f"{year:04d}-{month:02d}-01"
    return start_date

# Generar un número N de instancias diferentes
N = 100  # Número de instancias a generar

volunteers = []  # Lista para almacenar las instancias de voluntarios

for _ in range(N):
    # Generar una instancia de voluntario
    nombre = generar_nombre()
    apellido = generar_apellido()
    start_date = generar_start_date()
    email = f"{nombre.lower()}.{apellido.split()[0].lower()}@poppy-test.com"

    # Crear el objeto JSON
    volunteer = {
        "firstName": nombre,
        "lastName": apellido,
        "email": email,
        "password": "",
        "role": "USER",
        "startDate": start_date
    }

    # Agregar el atributo de género si está habilitado
    if generar_genero:
        gender = "FEMALE" if nombre.upper() in nombres_mujeres else "MALE"
        volunteer["gender"] = gender

    volunteers.append(volunteer)  # Agregar el objeto a la lista de voluntarios

# Guardar las instancias de voluntarios en un archivo JSON
with open("volunteers.json", "w") as file:
    json.dump(volunteers, file, indent=4)

print("Archivo 'volunteers.json' generado con éxito.")