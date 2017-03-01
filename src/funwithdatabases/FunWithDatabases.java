/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funwithdatabases;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stegg_000
 */
public class FunWithDatabases
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        FunWithDatabases fwd = new FunWithDatabases();
        try
        {
            fwd.run();
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void run() throws SQLException
    {
        List<String> strings = new ArrayList<String>();

        SQLServerDataSource ssds = new SQLServerDataSource();
        ssds.setServerName("127.0.0.1");
        ssds.setDatabaseName("MyDatabase");
        ssds.setUser("sa");
        ssds.setPassword("secret");

//        // Plain old SELECT:
//        System.out.println("Plain old SELECT:");
//        try (Connection con = ssds.getConnection())
//        {
//            
//            String sql = "SELECT * FROM MyTable;";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            ArrayList<String> arrNames = new ArrayList<>();
//
//            while (rs.next())
//            {
//                String name = rs.getString("Name");
//                int id = rs.getInt(1);
//                
//                arrNames.add(name);
//            }
//            rs.close();
//            st.close();
//            for (String name : arrNames)
//            {
//                System.out.println("Name: " + name);
//            }
//        } // Try with resources will close connection
//        //Plain old INSERT
//        System.out.println("Plain old INSERT");
//        try (Connection con = ssds.getConnection())
//        {
//            //Vi opretter et prepared statement i stedet for et statement:
//            String sql = "INSERT INTO MyTable(name) VALUES(?);"; // ? markerer en variabel.
//            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            ps.setString(1, "Mr. Hardcoder");
//            int rowsAffected = ps.executeUpdate();
//
//            ResultSet keys = ps.getGeneratedKeys();
//            if(keys.next())
//            {
//                System.out.println("Auto gen key: " + keys.getInt(1));
//            }
//        }
//        //Bad insert
//         try (Connection con = ssds.getConnection())
//        {
//            String okName = "Mr. Softcoder'); DELETE FROM MyTable;";
//            
//            //Vi opretter et prepared statement i stedet for et statement:
//            String sql = "INSERT INTO MyTable(name) VALUES ('"+okName; 
//            Statement st = con.createStatement();
//
//            int ok = st.executeUpdate(sql);
//            
//            
//
//        }
//        //Bad Select
//        System.out.println("bad SELECT");
//        try (Connection con = ssds.getConnection())
//        {
////            String okName = "Mr. Hardcoder";
//            String okName = "Mr. Hardcoder' OR '1'='1";
//            
//            //Vi opretter et prepared statement i stedet for et statement:
//            String sql = "SELECT * FROM MyTable WHERE Name='"+okName+"';"; 
//            Statement st = con.createStatement();
//
//            ResultSet rs = st.executeQuery(sql);
//            
//            while(rs.next())
//            {
//                System.out.println("name: " + rs.getString("name"));
//            }
//
//        }
        //Batch INSERT:
        System.out.println("Batch INSERT:");
        try (Connection con = ssds.getConnection())
        {
            String[] names =
            {
                "Bent", "Lars", "Jeppe", "Peter", "Ole", "Henrik", "Stig", "Anne-Mette"
            };

            //Vi opretter et prepared statement i stedet for et statement:
            String sql = "INSERT INTO MyTable(name) VALUES(?);"; // ? markerer en variabel.
            PreparedStatement ps = con.prepareStatement(sql);

            for (int i = 0; i < names.length; i++)
            {
                //Vi tildeler ? variabelen en værdi:
                ps.setString(1, names[i]);
                ps.addBatch();
                if (i % 100 == 0)
                {
                    int[] t = ps.executeBatch();
                }
            }
            int[] result = ps.executeBatch();
            for (int r : result)
            {
                System.out.println("R = " + r);
            }

        }
    }

}
