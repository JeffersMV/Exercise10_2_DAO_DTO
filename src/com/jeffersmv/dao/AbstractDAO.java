package com.jeffersmv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Абстрактный класс предоставляющий базовую реализацию CRUD операций с использованием JDBC.
 *
 * @param <E> тип объекта персистенции
 * @param <K> тип первичного ключа
 */
public abstract class AbstractDAO<E, K>{
    private Connection connection;


    public AbstractDAO(Connection connection) throws DaoException {
        this.connection = connection;
    }

    // Возвращает sql запрос для получения всех записей SELECT * FROM [Table]
    protected abstract String getSelectQuery();

    // Возвращает sql запрос для вставки новой записи в базу данных. INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
    protected abstract String getCreateQuery();

    // Возвращает sql запрос для обновления записи. UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
    protected abstract String getUpdateQuery();

    // Возвращает sql запрос для удаления записи из базы данных. DELETE FROM [Table] WHERE id= ?;
    protected abstract String getDeleteQuery();

    // Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
    protected abstract List<E> parseResultSet(ResultSet rs) throws DaoException;

    // Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
    protected abstract void prepareStatementForInsert(PreparedStatement ps, E object) throws DaoException;

    // Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
    protected abstract void prepareStatementForUpdate(PreparedStatement ps, E object) throws DaoException;

//    public abstract List<E> getAll() throws DaoException; // Получить всех студентов, предметов, оценки.
//    public abstract E getEntityByK(K key) throws DaoException; // получить id
//    public abstract void create(E object) throws DaoException; // Добавить
//    public abstract void update(E object) throws DaoException; // Обновить
//    public abstract void delete(K key) throws DaoException; // Удалить

    public List<E> getAll() throws DaoException {
        List<E> lst;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            lst = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return lst;
    }//Получаю список всех объектов


//    public E getEntityByK(K key) throws DaoException {
//        List<E> lst;
//        try (PreparedStatement ps = createPreparedStatement(connection, key);
//             ResultSet rs = ps.executeQuery()) {
//            lst = parseResultSet(rs);
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//        return lst.iterator().next();
//    }
//
//    private PreparedStatement createPreparedStatement(Connection con, K key) throws SQLException {
//        String sql = getSelectQuery() + " WHERE id = ?";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setInt(1, (Integer) key);
//        return ps;
//    }



    public E getEntityByK(K key) throws DaoException {
        List<E> lst;
        String sql = getSelectQuery() + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (Integer) key);
            try (ResultSet rs = ps.executeQuery()){
                lst = parseResultSet(rs);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        if (lst == null || lst.size() == 0) {
            throw new DaoException("Запись с K " + key + " не найдена.");
        } else if (lst.size() > 1) {
            throw new DaoException("Поступило более одной записи.");
        }
        return lst.iterator().next();
    }//Получаю один объект по id

    public void create(E object) throws DaoException {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException("Сохраняются изменения более чем на 1 запись: " + count);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }//Сохранение объекта с его последующим возвращением

    public void update(E object) throws DaoException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException("Обновляются именения более чем на 1 запись: " + count);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }//Обновление объекта

    public void delete(K key) throws DaoException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, (Integer) key);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoException("Удаляются именения более чем на 1 запись: "+ count);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }// Удаление объекта
}
