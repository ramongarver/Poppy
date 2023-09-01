USE poppy;

SHOW TABLES;

SELECT id, dtype, email, first_name, last_name, role, start_date, end_date, password FROM user;
SELECT id, name, description, place, local_date_time, number_of_coordinators, activity_package_id FROM activity;
SELECT id, name, description, type, availability_end_date, availability_start_date, are_volunteers_assigned, is_visible FROM activity_package;
SELECT id, name, description, short_name FROM work_group;
SELECT activity_id, volunteer_id FROM activity_volunteer;
SELECT workgroup_id, volunteer_id FROM work_group_volunteer;
SELECT id, activity_package_id, volunteer_id FROM volunteer_availability;
SELECT volunteer_availability_id, activity_id FROM volunteer_availability_activity;

# DROP DATABASE poppy;
# CREATE DATABASE poppy;


# CREATE SCHEMA poppy;
# DROP SCHEMA poppy;

USE poppy;

CREATE USER 'user'@'%' IDENTIFIED BY '';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER ON poppy.* TO 'poppydb_api'@'%';

SELECT User, Host FROM mysql.user;

SHOW GRANTS FOR 'poppydb_admin'@'%';
SHOW GRANTS FOR 'poppydb_api'@'%';

DROP USER 'poppydb_apiuser'@'%';

