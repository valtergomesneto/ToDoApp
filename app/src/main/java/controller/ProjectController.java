/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
//import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;
import java.sql.SQLException;


public class ProjectController {
    
    public void save(Project project){
        String sql = "INSERT INTO projects (name,"
                + "description,"      
                + "createdAt,"
                + "updatedAt)"
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto!" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
            
        }
    }
    
    public void update(Project project){
            String sql = "UPDATE projects SET name = ?, "
                    + "description = ?, "
                    + "createdAt = ?, "
                    + "updatedAt = ? "
                    + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);
            //setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            //executando a query
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto!" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
            
    }
    
    public void removeById(int projectId){
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {           
            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);
            //setando os valores do statement
            statement.setInt(1, projectId);
            statement.execute(); 
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto!" + ex.getMessage(), ex);
        } finally {
             ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
        //Lista de tarefas quando a chamada do metodo acontecer
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;
        
        
        
        try {
            //estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);
            //valor retornado da query
            resultSet = statement.executeQuery();
            //enquanto tiverem valores a serem percorridos no resultSet
            while (resultSet.next()){
            
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
                
            }
        } catch (Exception ex) {
             throw new RuntimeException("Erro ao buscar o projeto!" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de tarefas sendo devolvida apos ser criada e carregada do banco
        return projects;
    }
    
}
