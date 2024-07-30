CREATE TABLE EMPLOYEE (
    EMPLOYEEID SERIAL PRIMARY KEY,   
  Name VARCHAR(100) NOT NULL,      
 Password VARCHAR(255) NOT NULL,
EMAIL VARCHAR(100) NOT NULL  );

CREATE TABLE ROLEs (	
    ROLEID SERIAL PRIMARY KEY,   
  Name VARCHAR(100) NOT NULL );

CREATE TABLE EMPLOYEE_ROLEs (
	EMPLOYEEID INTEGER, 
    ROLEID INTEGER);

INSERT INTO employee VALUES
(1,'john','$2a$12$.DNBDk4w3VlG6Fn3sNmUOeCw6VXuVa7O3oDCfvRrfMucwMn5VthQK','john@gmail.com'),
(2,'sam','$2a$12$FujPv5E0QKVRVt1zIIu3GuTMxkvLs9dEgTQOx0z8WYfyjYJAZF0Zm','sam@gmail.com');

INSERT INTO roles VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');

INSERT INTO employee_roles VALUES (1,1),(2,2);

SELECT * FROM EMPLOYEE;
SELECT * FROM ROLES;
SELECT * FROM EMPLOYEE_ROLES;

findByUsername

CREATE OR REPLACE FUNCTION findByUsername(user_name TEXT, INOUT v_result REFCURSOR DEFAULT NULL)
RETURNS REFCURSOR AS $$
BEGIN
    -- Open the cursor with the query result
    OPEN v_result FOR
    SELECT e.employeeid,er.roleid,e.name,e.email FROM EMPLOYEE e, EMPLOYEE_ROLES er WHERE
	e.name = user_name and e.employeeid=er.employeeid;

   -- RETURN v_result;
END;
$$ LANGUAGE plpgsql;
