package com.security.implementation.security.basicAuth;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public Employee findByUsername(String username){

        Employee employee;
        Roles role= new Roles();
        List<Roles> roles= new ArrayList<>();
        DatabaseConnection dbConnection;
        CallableStatement callableStatement;

        if(username == null && username.isEmpty()){
            return null;
        }

        // Step 1: Establishing a Connection
        try {

            String callableSQL = "{call findByUsername(?)}";
            dbConnection = getDBConnection();
            callableStatement = dbConnection.(callableSQL);
            callableStatement = dbConnection.prepareCall("{?=call eco_rapidtransfer_pkg.getSenderEmail(?)}");
        
            callableStatement.setString(1, username);
        
            //register multiple output parameters to match all return values
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
           
        
            callableStatement.execute();
            ResultSet rs = callableStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                employee= new Employee();
                employee.setId(rs.getLong("id"));
              employee.setEmail(rs.getString("email"));
              employee.setName(rs.getString("name"));
               employee.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            
        
        }





        return null;

    }

    private DatabaseConnection getDBConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDBConnection'");
    }

   
    
    
}