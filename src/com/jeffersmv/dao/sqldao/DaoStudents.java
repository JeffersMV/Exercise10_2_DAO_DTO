package com.jeffersmv.dao.sqldao;

import com.jeffersmv.dao.AbstractDAO;
import com.jeffersmv.dao.DaoException;
import com.jeffersmv.dto.StudentsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoStudents extends AbstractDAO<StudentsDTO, Integer> {
    private String psSQL = "SELECT id, firstName, lastName FROM students";


    @Override
    public String getSelectQuery() {
        return "SELECT id, firstName, lastName FROM students";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO students (id, firstName, lastName) VALUES (?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE students SET firstName  = ?, lastName = ? WHERE id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM students WHERE id = ?";
    }




    public DaoStudents(Connection connection) throws DaoException {
        super(connection);
    }

    @Override
    public List<StudentsDTO> getAll() throws DaoException {
        List<StudentsDTO> lst = new LinkedList<>();
        try (PreparedStatement ps = getPrepareStatement(psSQL);
             ResultSet rs = ps.executeQuery()) {
//            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudentsDTO studentsDTO = new StudentsDTO();
                studentsDTO.setId(rs.getInt(1));
                studentsDTO.setFirstName(rs.getString(2));
                studentsDTO.setLastName(rs.getString(3));
                lst.add(studentsDTO);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
        return lst;
    } //Получаю список всех студентов

    @Override
    public StudentsDTO getEntityById(Integer studentId) throws DaoException {
        StudentsDTO studentsDTO = new StudentsDTO();
        studentsDTO.setId(studentId);
        try (PreparedStatement ps = getPrepareStatement(psSQL +" WHERE id= ?")) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            studentsDTO.setId(rs.getInt(1));
            studentsDTO.setFirstName(rs.getString(2));
            studentsDTO.setLastName(rs.getString(3));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return studentsDTO;
    }//Получаю одного студента по id

    @Override
    public void create(StudentsDTO studentsDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement("INSERT INTO students (id, firstName, lastName) VALUES (?, ?, ?);")) {
            ps.setInt(1, 0);
            ps.setString(2, studentsDTO.getFirstName());
            ps.setString(3, studentsDTO.getLastName());
            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
    } // Добавляю или создаю студента

    @Override
    public void update(StudentsDTO studentsDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement
                ("UPDATE students SET firstName  = ?, lastName = ? WHERE id = ?")) {

            ps.setString(1, studentsDTO.getFirstName());
            ps.setString(2, studentsDTO.getLastName());
            ps.setInt(3, studentsDTO.getId());
            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }// Обновление данных студента

    @Override
    public void delete(Integer studentId) throws DaoException {
        try (PreparedStatement psStudent = getPrepareStatement("DELETE FROM students WHERE id = ?")) {
            psStudent.setInt(1, studentId);
            int count = psStudent.executeUpdate();
            if (count != 1) {
                throw new DaoException("Удаление Студента. Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // Удаление студента, его предметы и его оценки.

}
