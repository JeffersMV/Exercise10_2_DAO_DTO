package com.jeffersmv.dao.sqldao;

import com.jeffersmv.dao.*;
import com.jeffersmv.dto.*;

import java.sql.*;
import java.util.*;

public class DaoGrade extends AbstractDAO<GradeDTO, Integer>{

    public DaoGrade(Connection connection) throws DaoException {
        super(connection);
    }

    @Override
    protected String getSelectQuery() {return "SELECT id, grade, objectId, studentId FROM grade";}

    @Override
    protected String getCreateQuery() {return "INSERT INTO grade (id, grade, objectId, studentId) VALUES (?, ?, ?, ?);";}

    @Override
    protected String getUpdateQuery() {return "UPDATE grade SET grade = ?, objectId = ?, studentId = ? WHERE id = ?";}

    @Override
    protected String getDeleteQuery() {return "DELETE FROM grade WHERE id = ?";}

    @Override
    protected List<GradeDTO> parseResultSet(ResultSet rs) throws DaoException {
        List<GradeDTO> lst = new LinkedList<>();
        try {
            while (rs.next()) {
                GradeDTO gradeDTO = new GradeDTO();
                gradeDTO.setId(rs.getInt(1));
                gradeDTO.setGrade(rs.getInt(2));
                gradeDTO.setObjectId(rs.getInt(3));
                gradeDTO.setStudentId(rs.getInt(4));
                lst.add(gradeDTO);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
        return lst;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, GradeDTO gradeDTO) throws DaoException {
        try {
            ps.setInt(1, 0);
            ps.setInt(2, gradeDTO.getGrade());
            ps.setInt(3, gradeDTO.getObjectId());
            ps.setInt(4, gradeDTO.getStudentId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, GradeDTO gradeDTO) throws DaoException {
        try {
            ps.setInt(1, gradeDTO.getGrade());
            ps.setInt(2, gradeDTO.getObjectId());
            ps.setInt(3, gradeDTO.getStudentId());
            ps.setInt(4, gradeDTO.getId());
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
