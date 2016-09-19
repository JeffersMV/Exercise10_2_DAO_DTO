package com.jeffersmv.controller;

import com.jeffersmv.dao.*;
import com.jeffersmv.dao.sqldao.*;
import com.jeffersmv.dto.*;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

public class ControllerDAO extends DaoException {
    private Connection connection = DaoFactory.getConnection();
    private DaoStudents daoStudents;
    private DaoObjects daoObjects;
    private DaoGrade daoGrade;

    public ControllerDAO() throws DaoException {
        this.daoStudents = new DaoStudents(connection);
        this.daoObjects = new DaoObjects(connection);
        this.daoGrade = new DaoGrade(connection);
    }

    public void getAllStudents() throws DaoException {// Получение всех студентов
        List<StudentsDTO> lst = daoStudents.getAll();
        Iterator<StudentsDTO> studentsDTOIterator = lst.iterator();
        while (studentsDTOIterator.hasNext()) {
            StudentsDTO studentsDTO = studentsDTOIterator.next();
            System.out.println(studentsDTO.getId() + "    " + studentsDTO.getFirstName() + "     " + studentsDTO.getLastName());
        }
        System.out.println("_________________________");
    }

    public void getAllObjects() throws DaoException { //  Получение всех предметов
        List<ObjectsDTO> objectsDTOList = daoObjects.getAll();
        for (ObjectsDTO objectsDTO : objectsDTOList) {
            System.out.println(objectsDTO.getId() + "    " + objectsDTO.getObject());
        }
        System.out.println("_________________________");
    }

    public void getAllObjectsGradeOneStudent(Integer studentId) throws DaoException { // Получение всех предметов и оценок одного студента
        StudentsDTO studentsDTO = daoStudents.getEntityById(studentId);
        System.out.println(studentsDTO.getFirstName() + "     " + studentsDTO.getLastName());
        for (GradeDTO gradeDTO : daoGrade.getAllGradesObjectsIdStudents(studentId)) {
            System.out.println(gradeDTO.getId()+"     "+gradeDTO.getGrade()+"     "+ daoObjects.getEntityById(gradeDTO.getObjectId()).getObject());
        }
    }

    public void createStudent(StudentsDTO studentsDTO) throws DaoException {
       daoStudents.create(studentsDTO);
    }

    public void updateStudent(StudentsDTO studentsDTO) throws DaoException {
        daoStudents.update(studentsDTO);
    }

    public void deleteStudent(Integer studentId) throws DaoException {
        daoStudents.delete(studentId);
    }

    public void createObject(ObjectsDTO objectsDTO) throws DaoException {
        daoObjects.create(objectsDTO);
    }

    public void updateObject(ObjectsDTO objectsDTO) throws DaoException {
        daoObjects.update(objectsDTO);
    }

    public void deleteObject(Integer objectsId) throws DaoException {
        daoObjects.delete(objectsId);
    }

    public void createGrade(GradeDTO gradeDTO) throws DaoException {
        daoGrade.create(gradeDTO);
    }

    public void updateGrade(GradeDTO gradeDTO) throws DaoException {
        daoGrade.update(gradeDTO);
    }

    public void deleteGrade(Integer gradeId) throws DaoException {
        daoGrade.delete(gradeId);
    }



}
