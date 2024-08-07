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

    private DatabaseConnection databaseConnection;

    public EmployeeService(DatabaseConnection databaseConnection){
        this.databaseConnection= databaseConnection;
    }

    public Employee findByUsername(String username){


        
        Employee employee = new Employee();
        Roles role;
        List<Roles> roles= new ArrayList<>();
        Set<Roles> userRoles;
        Connection connection= null;
        DatabaseConnection dbConnection= new DatabaseConnection();
        CallableStatement callableStatement1= null;
        CallableStatement callableStatement2= null;
        ResultSet rs1= null;
        ResultSet rs2= null;

        if(username == null && username.isEmpty()){
            return null;
        }
      
        // Step 1: Establishing a Connection
        try {

           
            try {
                connection = dbConnection.getDBConnection() ;
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.info("Here in EmployeeService class again");
            callableStatement1 = connection.prepareCall("{?=call public.findByUsername(?)}");
            callableStatement2 = connection.prepareCall("{?=call public.getUserRoles(?)}");
            
            callableStatement1.setString(1, username);  
            callableStatement2.setString(1, username);   
            //register multiple output parameters to match all return values
            callableStatement1.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            callableStatement2.registerOutParameter(2, java.sql.Types.REF_CURSOR);

            callableStatement1.execute();
            callableStatement2.execute();
             rs1 = (ResultSet) callableStatement1.getObject(2);
             rs2 = (ResultSet) callableStatement2.getObject(2);

            // Step 4: Process the ResultSet object.
            while (rs1.next()) {
                employee.setId(rs1.getLong("role_id"));
              employee.setEmail(rs1.getString("email"));
              employee.setName(rs1.getString("employee_name"));
               employee.setEmployeeid(rs1.getLong("id"));
            }


            while (rs2.next()) {
                role= new Roles();
                role.setName(rs2.getString("name"));
                roles.add(role);
            }
            userRoles = new HashSet<Roles>(roles);
            employee.setRoles(userRoles);
            logger.info(employee.toString());

        } catch (SQLException esql) {
            
            esql.printStackTrace();
        } finally {
            try {
                if (callableStatement1 != null) {
                    callableStatement1.close();
                }
                if (callableStatement2 != null) {
                    callableStatement2.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }





        return null;

    }

   
    @Override
    public List<Employee> getAllEmployee() {
        Employee employee;
        List<Employee> employeeList= new ArrayList <>();
        DatabaseConnection dbConnection=null;
        CallableStatement callableStatement= null;
        Connection connection= null;

      

        // Step 1: Establishing a Connection
        try {

            String callableSQL = "{?=call public.getAllEmployees()}";
            try {
                connection = dbConnection.getDBConnection();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
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