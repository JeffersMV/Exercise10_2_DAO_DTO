package com.jeffersmv.dao.sqldao;

import com.jeffersmv.dao.AbstractDAO;
import com.jeffersmv.dao.DaoException;
import com.jeffersmv.dto.StudentsDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DaoStudents extends AbstractDAO<StudentsDTO, Integer> {


    public DaoStudents(Connection connection) throws DaoException {
        super(connection);
    }


    @Override
    protected String getSelectQuery() {return "SELECT id, firstName, lastName FROM students";}

    @Override
    protected String getCreateQuery() {return "INSERT INTO students (id, firstName, lastName) VALUES (?, ?, ?)";}

    @Override
    protected String getUpdateQuery() {return "UPDATE students SET firstName  = ?, lastName = ? WHERE id = ?";}

    @Override
    protected String getDeleteQuery() {return "DELETE FROM students WHERE id = ?";}


    @Override
    protected List<StudentsDTO> parseResultSet(ResultSet rs) throws DaoException {
        LinkedList<StudentsDTO> lst = new LinkedList<>();
        try {
            while (rs.next()) {
                StudentsDTO studentsDTO = new StudentsDTO();
                studentsDTO.setId(rs.getInt(1));
                studentsDTO.setFirstName(rs.getString(2));
                studentsDTO.setLastName(rs.getString(3));
                lst.add(studentsDTO);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return lst;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, StudentsDTO studentsDTO) throws DaoException {
        try {
            ps.setString(1, studentsDTO.getFirstName());
            ps.setString(2, studentsDTO.getLastName());
            ps.setInt(3, studentsDTO.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, StudentsDTO object) throws DaoException {
        try {
            statement.setInt(1, 0);
            statement.setString(2, object.getFirstName());
            statement.setString(3, object.getLastName());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
