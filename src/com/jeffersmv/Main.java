package com.jeffersmv;

import com.jeffersmv.controller.ControllerDAO;
import com.jeffersmv.dao.DaoException;
import com.jeffersmv.dto.StudentsDTO;

public class Main {

    public static void main(String[] args) throws DaoException {
        ControllerDAO controllerDAO = new ControllerDAO();
        controllerDAO.getAllStudents();
        controllerDAO.getAllObjects();
        controllerDAO.getAllObjectsGradeOneStudent(2);

        StudentsDTO studentsDTO = new StudentsDTO();
        studentsDTO.setId(0);
        studentsDTO.setFirstName("Jeff");
        studentsDTO.setLastName("Jefferson");
        controllerDAO.createStudent(studentsDTO);

        studentsDTO.setId(11);
        studentsDTO.setFirstName("Jeff");
        studentsDTO.setLastName("Jeffe333son");
        controllerDAO.updateStudent(studentsDTO);

        controllerDAO.deleteStudent(39);





        controllerDAO.closeConnection();
    }


}
