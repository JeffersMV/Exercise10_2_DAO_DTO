package com.jeffersmv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Абстрактный класс предоставляющий базовую реализацию CRUD операций с использованием JDBC.
 *
 * @param <E>  тип объекта персистенции
 * @param <K> тип первичного ключа
 */
public abstract class AbstractDAO<E, K> {
    private Connection connection;

    // Возвращает sql запрос для получения всех записей SELECT * FROM [Table]
    public abstract String getSelectQuery();

    // Возвращает sql запрос для вставки новой записи в базу данных. INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
    public abstract String getCreateQuery();

    // Возвращает sql запрос для обновления записи. UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
    public abstract String getUpdateQuery();

    // Возвращает sql запрос для удаления записи из базы данных. DELETE FROM [Table] WHERE id= ?;
    public abstract String getDeleteQuery();




    public AbstractDAO(Connection connection) throws DaoException{
        this.connection = connection;
    }

    public abstract List<E> getAll() throws DaoException; // Получить всех студентов, предметов, оценки.
    public abstract E getEntityById(K id) throws DaoException; // получить id
    public abstract void create(E entity) throws DaoException; // Добавить
    public abstract void update(E entity) throws DaoException; // Обновить
    public abstract void delete(K id) throws SQLException, DaoException; // Удалить



    // Получение экземпляра PrepareStatement
    protected PreparedStatement getPrepareStatement(String sql) throws DaoException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ps;
    }

//    public void delete(E object) throws DaoException {
//        String sql = getDeleteQuery();
//        try (PreparedStatement psStudent = getPrepareStatement(getDeleteQuery())) {
//            psStudent.setInt(1, object.getId());
//            int count = psStudent.executeUpdate();
//            if (count != 1) {
//                throw new DaoException("Удаление объекта. Сохраняются изменения более чем на 1 запись: " + count);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    } // Удаление студента, его предметы и его оценки.

    // Закрытие PrepareStatement
//    protected void close(PreparedStatement ps) throws DaoException {
//        if (ps != null) {
//            try {
//                ps.close();
//            } catch (SQLException e) {
//                throw new DaoException(e);
//            }
//        }
//    }
}
