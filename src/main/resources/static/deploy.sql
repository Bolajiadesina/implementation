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

CREATE OR REPLACE FUNCTION getAllEmployees( INOUT v_result refcursor DEFAULT NULL::refcursor)
 RETURNS refcursor
 LANGUAGE plpgsql
AS $function$
BEGIN
    -- Open the cursor with the query result
    OPEN v_result FOR
    SELECT  e.id,e.employee_name,e.name,e.email,e.employee_username FROM EMPLOYEE e J;

    --RETURN v_result;
END;
$function$
;

CREATE OR REPLACE FUNCTION getUserRoles( employeename text,INOUT v_result refcursor DEFAULT NULL::refcursor)
 RETURNS refcursor
 LANGUAGE plpgsql
AS $function$
BEGIN
    -- Open the cursor with the query result
    OPEN v_result FOR
    SELECT r.name 
FROM roles r 
JOIN employee_roles er ON r.id = er.role_id 
JOIN employee e ON er.employee_id = e.id  
WHERE e.name = employeename ;

    --RETURN v_result;
END;
$function$
;




CREATE OR REPLACE FUNCTION public.saveEmployee(
    p_employeeId VARCHAR,
    p_name VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_userName VARCHAR
) RETURNS VARCHAR AS $$
BEGIN
   INSERT INTO employee(id,email,employee_name,employee_password,employee_username) VALUES
((CAST (p_employeeId AS BIGINT)),p_email,p_name,p_password,p_userName);


    RETURN '00';
EXCEPTION
    WHEN OTHERS THEN
        -- Return an error code or message if something goes wrong
        RETURN '01: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION public.updateEmployee(
    p_employeeId VARCHAR,
    p_name VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_userName VARCHAR
) RETURNS VARCHAR AS $$
BEGIN
    -- Function logic here
    UPDATE EMPLOYEE 
    SET 
        email = p_email,
        employee_name = p_name,
        employee_password = p_password,
        employee_username = p_userName 
    WHERE id = CAST(p_employeeId AS BIGINT);

    RETURN '00';
EXCEPTION
    WHEN OTHERS THEN
        -- Return an error code or message if something goes wrong
        RETURN '01: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;




