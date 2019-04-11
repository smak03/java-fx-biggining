/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DAO.StudentDAO;
import entities.Student;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Sagar
 */
public class ViewController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private ListView<Student> lsvStudents;

    private final ObservableList<Student> studentsList = FXCollections.observableArrayList();

    private final StudentDAO studentDAO = new StudentDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lsvStudents.setItems(studentsList);
        studentsList.addAll(studentDAO.getAllStudent());

        lsvStudents.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                ListCell<Student> listCell = new ListCell() {
                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                        if (item != null) {
                            Student student = (Student) item;
                            setText(student.getName());
                        } else {
                            setText("");
                        }
                    }

                };
                return listCell;

            }

        });
        
        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
            //@override
            public void handle(ActionEvent event) throws Exception {
                if (!txtName.getText().trim().isEmpty()) {
                    Student newStudent = new Student(getID());
              
                    newStudent.setName(txtName.getText());
                
                    studentDAO.addStudent(newStudent);
                    
                    studentsList.add(newStudent);
                }
            }

        });

    }

    private int getID() {
        List<Student> allStudent = studentDAO.getAllStudent();
        int maxID = 0;

        if (!allStudent.isEmpty()) {
            for (Student student : allStudent) {
                if (student.getId() > maxID) {
                    maxID = student.getId();
                }
            }
        }
        return ++maxID;
    }

}
