package com.mindtree.doctorService.entity;

import java.io.Serializable;
import java.sql.*;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomDoctorIDGenerator implements IdentifierGenerator {
	private String prefix = "Doc_";
	private String suffix;
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		
		 try {
			   Connection connection = session.connection();
			   Statement statement = connection.createStatement();
			   ResultSet resultSet = statement.executeQuery("select id from Doctor_TB2 ORDER BY ID DESC LIMIT 1");
			   if(resultSet.next()) {
				   System.out.println(resultSet);
				   String id = resultSet.getString("id");
			       suffix = id.split("_", 2)[1];
			       
			       int temp = Integer.parseInt(suffix);  
			       suffix= String.format("%05d", temp+1);
			   }
			   else {
				   suffix= String.format("%05d", 0);
			   }
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			  return prefix + suffix ;


	}

}
