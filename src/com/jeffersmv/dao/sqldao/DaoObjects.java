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
    protected String getSelectQuery() {return "SELECT id, object FROM objects";}

    @Override
    protected String getCreateQuery() {return "INSERT INTO objects (id, object) VALUES (?, ?)";}

    @Override
    protected String getUpdateQuery() {return "UPDATE objects SET object  = ? WHERE id = ?";}

    @Override
    protected String getDeleteQuery() {return "DELETE FROM objects WHERE id = ?";}

    @Override
    protected List<ObjectsDTO> parseResultSet(ResultSet rs) throws DaoException {
        List<ObjectsDTO> lst = new LinkedList<>();
        try {
            while (rs.next()) {
                ObjectsDTO objectsDTO = new ObjectsDTO();
                objectsDTO.setId(rs.getInt(1));
                objectsDTO.setObject(rs.getString(2));
                lst.add(objectsDTO);
            }
        } catch (Exception sqlE) {
            throw new DaoException(sqlE);
        }
        return lst;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, ObjectsDTO object) throws DaoException {
        try {
            ps.setInt(1, 0);
            ps.setString(2, object.getObject());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, ObjectsDTO object) throws DaoException {
        try {
            ps.setString(1, object.getObject());
            ps.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
