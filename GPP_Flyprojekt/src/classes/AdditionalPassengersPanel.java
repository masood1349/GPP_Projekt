package classes;

import interfaces.IValidatable;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel includes all information about additional passengers, and
 * a panel to edit and add new passengers
 * @author Jakob Lautrup Nysom (jaln@itu.dk)
 * @version 08-Dec-2013
 */
public class AdditionalPassengersPanel extends JPanel {

    final private PassengerList dataList;
    final private ValidatedListPanel editPanel;

    public AdditionalPassengersPanel(int width, int height) {
        super(new BorderLayout());
        JPanel pasListPanel = new JPanel(new BorderLayout());
        JPanel pasEditPanel = new JPanel(new BorderLayout());
        int pasEditWidth = (width > 500)? 200: width/3;
        int pasListWidth = (width > 500)? width-200: (width/3)*2;

        JPanel infoLabelPanel = new JPanel(new GridBagLayout());
        JLabel infoLabel = new JLabel("Yderligere Passagerer");
        infoLabelPanel.add(infoLabel);

        int panelHeight = height-infoLabel.getPreferredSize().height;

        dataList = new PassengerList(new Validator(), 
                pasListWidth, panelHeight, "Redigér Passager", "Slet Passager");
        pasListPanel.add(dataList);

        // Editor panel
        JButton pasEditButton = new JButton("Tilføj Passager");

        int pasEditHeight = panelHeight - pasEditButton.getPreferredSize().height;
        editPanel = new ValidatedListPanel(new Validator(), 
                pasEditWidth, pasEditHeight, PersonData.values());

        // Let the button add stuff :)
        pasEditButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<IValidatable, String> data = editPanel.getData();
                HashMap<PersonData, String> person = new HashMap<PersonData, String>();
                for (IValidatable key : data.keySet()) {
                    if (PersonData.class.isInstance(key)) {
                        person.put((PersonData)key, data.get(key));
                    }
                }
                dataList.addPerson(person);
                editPanel.setData(new HashMap<PersonData, String>());
            }
        });

        dataList.setEditor(editPanel);
        pasEditPanel.add(editPanel, BorderLayout.NORTH);
        pasEditPanel.add(pasEditButton, BorderLayout.SOUTH);

        this.add(infoLabelPanel, BorderLayout.NORTH);
        this.add(pasListPanel, BorderLayout.WEST);
        this.add(pasEditPanel, BorderLayout.EAST);
    }
    
    /**
     * Returns the additional passengers currently in the list
     * @return An ArrayList of HashMaps representing passengers
     */
    public ArrayList<HashMap<PersonData, String>> getPersons() {
        return dataList.getPersons();
    }
    
    /**
     * Adds an additional passenger to the panel
     * @param person The passenger to add
     */
    public void addPerson(HashMap<PersonData, String> person) {
        dataList.addPerson(person);
    }
}