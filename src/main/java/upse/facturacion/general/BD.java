package upse.facturacion.general;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.function.Function;

public class BD {
    private Connection conexion;
    private Statement sentenciaSQL;
    private ResultSet resulSet;

    public BD() {
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Statement getSentenciaSQL() {
        return sentenciaSQL;
    }

    public void setSentenciaSQL(Statement sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    public ResultSet getResulSet() {
        return resulSet;
    }

    public void setResulSet(ResultSet resulSet) {
        this.resulSet = resulSet;
    }

    //funciones para trabajar con la base de datos
    public boolean conectarBD() {
        String servidor, basedatos, usuario, clave, classNombre, cadenaConexion;

        //conecta con SQLSErver
        servidor = "EMI-CRUZ28";
        basedatos = "BD2026_1";
        usuario = "usu_appvisual";
        clave = "usu_appvisual";
        //General.Mod_General.str_nombreBD = "SQL Server";
        classNombre = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        cadenaConexion = "jdbc:sqlserver://"
                + servidor + ":1433;"
                + "database=" + basedatos + ";"
                + "user=" + usuario + ";"
                + "password=" + clave + ";"
                + "encrypt=false; loginTimeout=30;";
        try {
            Class.forName(classNombre);
            //System.out.println(cadenaConexion);
            Connection conexion = DriverManager.getConnection(cadenaConexion);
            //conexion.setAutoCommit(false);
            this.setConexion(conexion);
            // System.out.println("ConectarBD");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//funciones

    public void desconectarBD() {
        try {
            this.conexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void iniciarTransaccion() {
        try {
            this.conexion.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void commit() {
        try {
            this.conexion.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void rollback() {
        try {
            this.conexion.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //crear una función que me permita ejecutar sentencias sql
    // select
    public void ejecutarConsultaSql(String cadenaSQL) {
        try {
            this.sentenciaSQL = this.conexion.createStatement();
            this.resulSet = this.sentenciaSQL.executeQuery(cadenaSQL);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //crear una función para Insertar - Update - Delete
    public int ejecutarSQL(String cadenaSQL) {
        int filas = 0;
        try {
            this.sentenciaSQL = this.conexion.createStatement();
            filas = this.sentenciaSQL.executeUpdate(cadenaSQL);
            this.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return filas;
    }

    public <T> ObservableList<T> getListaConsulta(String cadenaSQL, Function<ResultSet, T> mapper) {
        ObservableList<T> obsListAux = FXCollections.observableArrayList();
        try {
            conectarBD(); // conectar a la BD
            ejecutarConsultaSql(cadenaSQL); // ejecutar la consulta
            ResultSet rs = getResulSet(); // obtener el resultado
            while (rs.next()) {
                T obj = mapper.apply(rs); // mapear la fila
                obsListAux.add(obj);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("ERROR en getListaConsulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            desconectarBD();
        }
        return obsListAux;
    }

    public boolean fun_ejecutar(String cadenaSQL) {
        try {
            this.conectarBD();
            int filas = this.ejecutarSQL(cadenaSQL);

            if (filas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            this.desconectarBD();
        }
    }
}
