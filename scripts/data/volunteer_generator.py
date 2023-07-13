import json
import sys

import numpy as np

# Women's names
women_names = np.array([
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

# Men's names
men_names = np.array([
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

# Last names
last_names = np.array([
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

# Check if an argument was provided to activate the gender attribute
generate_gender = False
if len(sys.argv) > 1 and sys.argv[1].lower() == 'gender=true':
    generate_gender = True

# Generate a random value for firstName in the format "Name"
def generate_name():
    names = np.concatenate((women_names, men_names))
    name = np.random.choice(names)
    name = name.title().split(" ")[0]  # Convert first letter to uppercase and get first name
    return name

# Generate a random value for lastName in the format "LastName1 LastName2"
def generate_last_name():
    last_name1 = np.random.choice(last_names).title()
    last_name2 = np.random.choice(last_names).title()
    last_name = last_name1 + " " + last_name2
    return last_name

# Generate a random value for startDate in the format "YYYY-MM-DD"
def generate_start_date():
    year = np.random.choice([2021, 2022])
    month = np.random.choice([2, 9])
    start_date = f"{year:04d}-{month:02d}-01"
    return start_date

# Generate a number N of different instances
N = 100  # Number of instances to generate

volunteers = []  # List to store volunteer instances

for _ in range(N):
    # Generate a volunteer instance
    name = generate_name()
    last_name = generate_last_name()
    start_date = generate_start_date()
    email = f"{name.lower()}.{last_name.split()[0].lower()}@poppy-test.com"

    # Create the JSON object
    volunteer = {
        "firstName": name,
        "lastName": last_name,
        "email": email,
        "password": "",
        "role": "USER",
        "startDate": start_date
    }

    # Add the gender attribute if enabled
    if generate_gender:
        gender = "FEMALE" if name.upper() in women_names else "MALE"
        volunteer["gender"] = gender

    volunteers.append(volunteer)  # Add the object to the volunteers list

# Save the volunteer instances to a JSON file
with open("volunteers.json", "w") as file:
    json.dump(volunteers, file, indent=4)

print("File 'volunteers.json' successfully generated.")