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
    public List<GradeDTO> getAll() throws DaoException {
        List<GradeDTO> lst = new LinkedList<>();
        try (PreparedStatement ps = getPrepareStatement("SELECT id, grade, objectId, studentId FROM grade")){
            ResultSet rs = ps.executeQuery();
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
    public GradeDTO getEntityById(Integer gradeId) throws DaoException {
        GradeDTO gradeDTO = new GradeDTO();
        try (PreparedStatement ps = getPrepareStatement("SELECT id, grade, objectId, studentId FROM grade WHERE id = ?")){
            ps.setInt(1, gradeId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            gradeDTO.setId(rs.getInt(1));
            gradeDTO.setId(rs.getInt(2));
            gradeDTO.setId(rs.getInt(3));
            gradeDTO.setId(rs.getInt(4));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return gradeDTO;
    }

    @Override
    public void create(GradeDTO gradeDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement("INSERT INTO grade (id, grade, objectId, studentId) VALUES (?, ?, ?, ?);")){
            ps.setInt(1, 0);
            ps.setInt(2, gradeDTO.getGrade());
            ps.setInt(3, gradeDTO.getObjectId());
            ps.setInt(4, gradeDTO.getStudentId());
            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
    }

    @Override
    public void update(GradeDTO gradeDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement
                ("UPDATE grade SET grade = ?, objectId = ?, studentId = ? WHERE id = ?")){
            ps.setInt(1, gradeDTO.getGrade());
            ps.setInt(2, gradeDTO.getObjectId());
            ps.setInt(3, gradeDTO.getStudentId());
            ps.setInt(4, gradeDTO.getId());

            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    @Override
    public void delete(Integer gradeId) throws DaoException {
        try (PreparedStatement psGrade = getPrepareStatement("DELETE FROM grade WHERE id = ?")){
            psGrade.setInt(1, gradeId);
            int count = psGrade.executeUpdate();
            if (count != 1) {
                throw new DaoException("Удаление Оценки. Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<GradeDTO> getAllGradesObjectsIdStudents(Integer studentId) throws DaoException {
        List<GradeDTO> lst = new LinkedList<>();
        try (PreparedStatement psGrade = getPrepareStatement("SELECT id, grade, objectId, studentId FROM grade WHERE id = ?")) {
            psGrade.setInt(1, studentId);
            ResultSet rs = psGrade.executeQuery();
            while (rs.next()) {
                GradeDTO gradeDTO = new GradeDTO();
                gradeDTO.setId(rs.getInt(1));
                gradeDTO.setGrade(rs.getInt(2));
                gradeDTO.setObjectId(rs.getInt(3));
                gradeDTO.setStudentId(rs.getInt(4));
                lst.add(gradeDTO);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return lst;

    }
}
