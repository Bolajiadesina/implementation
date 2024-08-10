package com.security.implementation.security.basicAuth.services;

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

import com.security.implementation.security.basicAuth.model.Employee;
import com.security.implementation.security.basicAuth.model.EmployeeRequest;
import com.security.implementation.security.basicAuth.model.Response;
import com.security.implementation.security.basicAuth.model.Roles;
import com.security.implementation.security.basicAuth.repository.EmployeeRepository;
import com.security.utils.DatabaseConnection;

@Repository
public class EmployeeService implements EmployeeRepository {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class); 

    
    

    public Employee findByUsername(String username){


        
        Employee employee = null;
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
                employee = new Employee();
                employee.setId(rs1.getString("role_id"));
              employee.setEmail(rs1.getString("email"));
              employee.setName(rs1.getString("employee_name"));
               employee.setEmployeeId(rs1.getString("id"));
               employee.setPassword(rs1.getString("employee_password"));
               employee.setUserName(username);
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
                if (rs1 != null) {
                    rs1.close();
                }
                if (callableStatement2 != null) {
                    callableStatement2.close();
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }





        return employee;

    }

   
    @Override
    public Response getAllEmployee() {
        Employee employee;
        List<Employee> employeeList= new ArrayList <>();
        Response response= new Response();
        
        CallableStatement callableStatement= null;
        Connection connection= null;
        DatabaseConnection dbConnection= new DatabaseConnection();

      

        // Step 1: Establishing a Connection
        try {

            
            try {
                connection = dbConnection.getDBConnection();
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            callableStatement = connection.prepareCall("{?=call public.getAllEmployees()}");
        
         
        
            //register multiple output parameters to match all return values
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
           
        
            callableStatement.execute();
            ResultSet rs = (ResultSet) callableStatement.getObject(1);

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                employee= new Employee();
                employee.setId(rs.getString("id"));
              employee.setEmail(rs.getString("email"));
              employee.setName(rs.getString("employee_name"));
               employee.setEmployeeId(rs.getString("id"));
               employee.setUserName(rs.getString("employee_username"));

               employeeList.add(employee);
            }


            response.setResponseCode("00");
            response.setResponseMessage("Success");
            response.setData(employeeList);
        } catch (SQLException e) {
            
            
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }

        return response;
    }

    @Override
    public Response saveUser(EmployeeRequest employee) {
        Connection connection= null;
        DatabaseConnection dbConnection= new DatabaseConnection();
        CallableStatement callableStatement= null;
        Response response= new Response();

        // Step 1: Establishing a Connection
        try {

            
            try {
                connection = dbConnection.getDBConnection();
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException e) { 
               
                e.printStackTrace();
            }
           logger.info(employee.toString());
            callableStatement = connection.prepareCall("{?=call public.saveEmployee(?,?,?,?,?)}");
        
          //register multiple output parameters to match all return values
            
            callableStatement.setString(1, employee.getEmployeeId() == null ? "" : employee.getEmployeeId());
            callableStatement.setString(2, employee.getName()== null ? "" : employee.getName());
            callableStatement.setString(3, employee.getEmail()== null ? "" :employee.getEmail());
            callableStatement.setString(4, employee.getPassword()  == null ? "" : employee.getPassword());
            callableStatement.setString(5, employee.getUserName() == null ? "" :employee.getUserName());
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
          
        
            callableStatement.execute();
            
            // Step 4: Process the ResultSet object.
            response.setResponseCode((String) callableStatement.getObject(1));
            
            response.setResponseMessage("Success");
            // Commit the transaction
        connection.commit();
           
        } catch (SQLException e) {
            try {
            if (connection != null) {
                connection.rollback();  // Rollback transaction in case of error
            }
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        response.setResponseCode("ERROR");
        response.setResponseMessage("Failed: " + e.getMessage());
        e.printStackTrace();

        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }
        return response;

       
    }

    @Override
    public Response updateUser(EmployeeRequest employee) {
        Connection connection= null;
        DatabaseConnection dbConnection= new DatabaseConnection();
        CallableStatement callableStatement= null;
        Response response= new Response();

        // Step 1: Establishing a Connection
        try {

            
            try {
                connection = dbConnection.getDBConnection();
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException e) {
               
                e.printStackTrace();
            }
           
            callableStatement = connection.prepareCall("{?=call public.updateuser(?,?,?,?,?,?)}");
        
         
            callableStatement.setString(2, employee.getEmployeeId()  == null ? "" : employee.getEmployeeId());
            callableStatement.setString(3, employee.getName()== null ? "" : employee.getName());
            callableStatement.setString(4, employee.getEmail()== null ? "" :employee.getEmail());
            callableStatement.setString(5, employee.getEmployeeId()== null ? "" :employee.getEmployeeId());
            callableStatement.setString(6, employee.getUserName() == null ? "" :employee.getUserName());
            callableStatement.setString(7, employee.getPassword()  == null ? "" : employee.getPassword());
        
            //register multiple output parameters to match all return values
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
           
        
            callableStatement.execute();
            ResultSet rs = (ResultSet) callableStatement.getObject(1);

            // Step 4: Process the ResultSet object.
            response.setResponseCode("00");
            response.setResponseMessage("Success");
            
           
        } catch (SQLException e) {
            
            
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }
        return response;
    }

    
   
}
    
