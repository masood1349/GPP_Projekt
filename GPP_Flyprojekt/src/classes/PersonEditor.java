package classes;

import interfaces.IPersonDataList;
import interfaces.IPersonEditor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * The PersonEditor class <More docs goes here>
 * @author Jakob Lautrup Nysom (jaln@itu.dk)
 * @version 22-Nov-2013
 */
public class PersonEditor extends JPanel implements IPersonEditor {
    
    private int WIDTH = 200;
    private int HEIGHT = 300;
    
    private float FNAMEW = 0.3f;
    private int FNAMEH = 32;
    
    GridLayout layout;
    JScrollPane scrollPane;
    JPanel panel;
    final IPersonDataList list;
    HashMap<PersonData, JTextField> textFields;
    
    public PersonEditor (final IPersonDataList list) {
        super();
        this.list = list;
        list.setEditor(this);
        
        textFields = new HashMap<PersonData, JTextField>();
        
        layout = new GridLayout(0, 1);
        panel = new JPanel();
        panel.setLayout(layout);
        
        for (PersonData data : PersonData.values()) {
            JPanel topPanel = new JPanel();
            
            JLabel fieldLabel = new JLabel(data+"");
            topPanel.add(fieldLabel, "West");
            
            JTextField valueField = new JTextField("");
            valueField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
            
            int maxWidth = (int)Math.round((1f-FNAMEW)*WIDTH);
            valueField.setMaximumSize(new Dimension(maxWidth, 16));
            
            textFields.put(data, valueField);
            
            StatusLabel statusLabel = new StatusLabel(valueField, data, new Validator());
            topPanel.add(statusLabel, "East");
            
            valueField.addFocusListener(statusLabel);
            
            panel.add(topPanel);
            panel.add(valueField);
        }
        
        /*SpringUtilities.makeCompactGrid(panel, PersonData.values().length * 2, 
            1, 5, 5, 5, 5);*/
        
        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        this.add(scrollPane, "North");
        
        JButton addPersonButton = new JButton("Add Person");
        addPersonButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                list.addPerson(getPerson());
                editPerson(new HashMap<PersonData, String>());
            }
        });
        addPersonButton.setFocusable(false);
        this.add(addPersonButton, "South");
    }
    
    /**
     * Returns the data for the person currently being edited
     * @return 
     */
    private HashMap<PersonData, String> getPerson() {
        HashMap<PersonData, String> person = new HashMap<PersonData, String>();
        for (PersonData key : textFields.keySet()) {
            String value = textFields.get(key).getText();
            person.put(key, value);
        }
        return person;
    }
    
    @Override
    public void editPerson(HashMap<PersonData, String> person) {
        for (PersonData key : textFields.keySet()) {
            if (person.keySet().contains(key)) {
                textFields.get(key).setText(person.get(key));
            } else {
                textFields.get(key).setText("");
            }   
        }
        prepareFields();
    }
    
    /**
     * Focus cycles through the text fields to update the status labels, and to
     * set it so that the first box is focused when the next entry is to be used
     */
    private void prepareFields() {
        for (PersonData field : PersonData.values()) {
            textFields.get(field).requestFocusInWindow();
        }
        JTextField first = textFields.get(PersonData.values()[0]);
        first.requestFocusInWindow();
    }
}