
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class MainClass {
    public static void main(String[] args) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String URL = "jdbc:mariadb://localhost:3306/universidadg1";
            String usuario ="root";
            String password = "";
            Connection con = DriverManager.getConnection(URL,usuario, password);
//Dar de alta un alumno
          /*  String sql = "INSERT INTO alumno(dni, apellido, nombre, fechaDeNacimiento, estado) "
                    + "VALUES (42744243, 'Amengual', 'Alberto','2000-12-20',true)";*/
            /* String sql = "INSERT INTO alumno(dni, apellido, nombre, fechaDeNacimiento, estado) "
                    + "VALUES (59340361, 'Amengual', 'Ciro','2022-07-14',true)";*/
             /* String sql = "INSERT INTO alumno(dni, apellido, nombre, fechaDeNacimiento, estado) "
                    + "VALUES (42744000, 'Herrera', 'Matias','2000-11-09',true)";*/
             /*String sql=  "INSERT INTO materia(nombre, anio, estado)"
              +" vALUES('Matematicas',2018,true)";*/
             //Obtener datos
            /* String sql=  "INSERT INTO materia(nombre, anio, estado)"
              +" vALUES('Sociología',2018,false)";*/
           /* String sql=  "INSERT INTO materia(nombre, anio, estado)"
              +" vALUES('Lengua',2015,true)";
           PreparedStatement ps = con.prepareStatement(sql);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Alumno agregado");
            }*/
            /*String sql = "SELECT * FROM alumno WHRE estado=true";
//Como meter un objeto
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                    int id=rs.getInt("idAlumno");
                    int dni=rs.getInt("dni");
                    String apellido=rs.getString("apellido");
                    String nombre=rs.getString("nombre");
                    LocalDate fechN=rs.getDate("fecha").toLocalDate();

                    //sout("Mostrar Datos");

}*/
            //Distinct para que no repita filas con el mismo alumno en este caso
            
String sql= "SELECT DISTINCT alumno.*, materia.nombre FROM alumno "
            +"JOIN inscripcion ON (alumno.idAlumno = inscripcion.idAlumno) JOIN materia ON(materia.idMateria = inscripcion.idMateria)"
            + "WHERE inscripcion.nota > 8";

            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();//este método trae los datos en una tabla
             while(rs.next()){
                    int id=rs.getInt("idAlumno");
                    int dni=rs.getInt("dni");
                    String apellido=rs.getString("apellido");
                    String nombre=rs.getString("nombre");
                    LocalDate fechN=rs.getDate("fechaDeNacimiento").toLocalDate();
                    boolean estado = rs.getBoolean("estado");
                    String materia = rs.getString("materia.nombre");
                           

                    //sout("Mostrar Datos)
                    
                   System.out.println("----------Datos-----------");
                   System.out.println("Nombre: "+ nombre+apellido);
                   System.out.println("Id: "+id);
                   System.out.println("Dni" + dni);
                   System.out.println("Fecha: "+fechN.toString());
                   System.out.println("Estado: "+estado);
                   System.out.println("Materia: "+materia);
                   System.out.println("/////////////////");
                 
             }
             
             String sql1 = "DELETE FROM inscripcion WHERE idAlumno = 6 AND idMateria = 3";
                   PreparedStatement pc =con.prepareStatement(sql1);
                  int delete = pc.executeUpdate();
                  if(delete > 0){
                     JOptionPane.showMessageDialog(null, "Borrado");
                  }
             
             
               /*String sql = "INSERT INTO inscripcion(nota, idAlumno, idMateria) VALUES (9,6,1)";
                PreparedStatement ps = con.prepareStatement(sql);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Alumno agregado");
            }*/

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al conectar el Driver");

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error de conexion");
            ex.printStackTrace(); //
            int error=ex.getErrorCode();
            if(error==1146){
                JOptionPane.showMessageDialog(null,"Tabla inexistente");
            }else if(error==1062){

                JOptionPane.showMessageDialog(null,"Dni duplicado");
            }else if(error==1049){

                JOptionPane.showMessageDialog(null,"BD inexistente");
            }else {

                JOptionPane.showMessageDialog(null,"Error SQL");

            }
    }
}
}
