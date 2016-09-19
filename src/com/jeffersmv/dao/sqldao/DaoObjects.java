package com.jeffersmv.dao.sqldao;

import com.jeffersmv.dao.*;
import com.jeffersmv.dto.*;

import java.sql.*;
import java.util.*;

public class DaoObjects extends AbstractDAO<ObjectsDTO, Integer> {

    public DaoObjects(Connection connection) throws DaoException {
        super(connection);
    }

    @Override
    public List<ObjectsDTO> getAll() throws DaoException {
        List<ObjectsDTO> lst = new LinkedList<>();
        try (PreparedStatement ps = getPrepareStatement("SELECT id, object FROM objects")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ObjectsDTO objectsDTO = new ObjectsDTO();
                objectsDTO.setId(rs.getInt(1));
                objectsDTO.setObject(rs.getString(2));
                lst.add(objectsDTO);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
        return lst;
    }

    @Override
    public ObjectsDTO getEntityById(Integer objectId) throws DaoException {
        ObjectsDTO objectDTO = new ObjectsDTO();
        try (PreparedStatement ps = getPrepareStatement("SELECT id, object FROM objects WHERE id = ?")){
            ps.setInt(1, objectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectDTO.setId(rs.getInt(1));
                objectDTO.setObject(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return objectDTO;
    }

    @Override
    public void create(ObjectsDTO objectsDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement("INSERT INTO objects (id, object) VALUES (?, ?);")){
            ps.setInt(1, 0);
            ps.setString(2, objectsDTO.getObject());
            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            throw new DaoException(sqlE);
        }
    }

    @Override
    public void update(ObjectsDTO objectsDTO) throws DaoException {
        try (PreparedStatement ps = getPrepareStatement
                ("UPDATE objects SET object = ? WHERE id =" + objectsDTO.getId())){
            ps.setString(1, objectsDTO.getObject());
            int count = ps.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    @Override
    public void delete(Integer objectId) throws DaoException {
        try (PreparedStatement psObject = getPrepareStatement("DELETE FROM students WHERE id=?")){
            psObject.setInt(1, objectId);
            int count = psObject.executeUpdate();
            if (count != 1) {
                throw new DaoException("Удаление Предмета. Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
