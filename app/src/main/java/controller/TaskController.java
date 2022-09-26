package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import util.ConnectionFactory;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Tasks;

public class TaskController {

    public void save(Tasks task) {
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa! " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    public void update(Tasks task) {

        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?,  "
                + "notes = ?, "
                + "deadline = ?, "
                + "completed = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //Cria uma conex?o com o banco
            conn = ConnectionFactory.getConnection();
            //Cria um PreparedStatment, classe usada para executar a query
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, task.getIdProject());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getNotes());
            stmt.setDate(5, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setBoolean(6, task.isIsCompleted());
            stmt.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            stmt.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            stmt.setInt(9, task.getId());

            //Executa a sql para inser??o dos dados
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em atualizar a tarefa", ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
    }

    public List<Tasks> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de tarefas quando a chamada do metodo acontecer
        List<Tasks> tasks = new ArrayList<Tasks>();

        try {
            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);
            //setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            //valor retornado da query
            resultSet = statement.executeQuery();
            //enquanto tiverem valores a serem percorridos no resultSet
            while (resultSet.next()) {

                Tasks task = new Tasks();

                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);

            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao Inserir as tarefa!" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        //Lista de tarefas sendo devolvida apos ser criada e carregada do banco
        return tasks;
    }
    
    public void removeById(int id) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa", ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }

    }

}
