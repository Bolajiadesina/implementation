package com.security.implementation.security.basicAuth;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeService implements EmployeeRepository {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class); 

    public Employee findByUsername(String username){


        
        Employee employee = new Employee();
        Roles role;
        List<Roles> roles= new ArrayList<>();
        Set<Roles> userRoles;
        DatabaseConnection dbConnection;
        CallableStatement callableStatement1;
        CallableStatement callableStatement2;

        if(username == null && username.isEmpty()){
            return null;
        }
        logger.info("Here in EmployeeService class");
        // Step 1: Establishing a Connection
        try {

           
            dbConnection = getDBConnection();
           
            callableStatement1 = ((Connection) dbConnection).prepareCall("{?=call public.findByUsername(?)}");
            callableStatement2 = ((Connection) dbConnection).prepareCall("{?=call public.getUserRoles(?)}");
        
            callableStatement1.setString(1, username);  
            callableStatement2.setString(1, username);   
            //register multiple output parameters to match all return values
            callableStatement1.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement2.registerOutParameter(1, java.sql.Types.REF_CURSOR);

            callableStatement1.execute();
            callableStatement2.execute();
            ResultSet rs1 = (ResultSet) callableStatement1.getObject(1);
            ResultSet rs2 = (ResultSet) callableStatement1.getObject(1);

            // Step 4: Process the ResultSet object.
            while (rs1.next()) {
                employee.setId(rs1.getLong("role_id"));
              employee.setEmail(rs1.getString("email"));
              employee.setName(rs1.getString("employee_name"));
               employee.setEmployeeid(rs1.getLong("id"));
            }


            while (rs2.next()) {
                role= new Roles();
                role.setName(rs2.getString("role"));
                roles.add(role);
            }
            userRoles = new HashSet<Roles>(roles);
            employee.setRoles(userRoles);


        } catch (SQLException e) {
            
        
        }





        return null;

    }

    private DatabaseConnection getDBConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDBConnection'");
    }

    @Override
    public List<Employee> getAllEmployee() {
        Employee employee;
        List<Employee> employeeList= new ArrayList <>();
        DatabaseConnection dbConnection;
        CallableStatement callableStatement;

      

        // Step 1: Establishing a Connection
        try {

            String callableSQL = "{?=call public.getAllEmployees()}";
            dbConnection = getDBConnection();
           
            callableStatement = ((Connection) dbConnection).prepareCall(callableSQL);
        
         
        
            //register multiple output parameters to match all return values
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
           
        
            callableStatement.execute();
            ResultSet rs = (ResultSet) callableStatement.getObject(1);

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                employee= new Employee();
                employee.setId(rs.getLong("role_id"));
              employee.setEmail(rs.getString("email"));
              employee.setName(rs.getString("employee_name"));
               employee.setEmployeeid(rs.getLong("id"));

               employeeList.add(employee);
            }
        } catch (SQLException e) {
            
            
        }




        return null;
    }

    
   
    
    
}