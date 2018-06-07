package com.company.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.ArrayList;
import database_objects.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelectOsiagnieciaControler {

    @FXML
    private Button button;
    @FXML
    private MenuButton DziedzinaWiedczyMenu, EtykietyMenu, KategorieMenu, StopnieMenu, TypMenu;
    @FXML
    private TableView table;

    static private final double columnWith = 110.0;

    public SelectOsiagnieciaControler() {
        System.out.println("jestem kontrolerem");
    }


    private void addTyp(ArrayList<TypyOsiagniec> typy) {
        for (TypyOsiagniec t : typy) {
            CheckMenuItem new_item = new CheckMenuItem(t.getTyp());
            new_item.setId(Integer.toString(t.getId()));
            TypMenu.getItems().add(new_item);
        }
    }

    private void addEtykiety(ArrayList<Etykiety> etykiety) {
        for (Etykiety e : etykiety) {
            CheckMenuItem new_item = new CheckMenuItem(e.getEtykieta());
            new_item.setId(Integer.toString(e.getId()));
            EtykietyMenu.getItems().add(new_item);
        }
    }

    private void addDziedzinaWiedzy(ArrayList<DziedzinaWiedzy> dziedzinyWiedzy) {
        for (DziedzinaWiedzy d : dziedzinyWiedzy) {
            CheckMenuItem new_item = new CheckMenuItem(d.getNazwa_dziedziny());
            new_item.setOnAction((ActionEvent event) -> {
                dziedzinaWiedzyMenuButtonAction(event);
            });
            new_item.setId(Integer.toString(d.getId()));
            DziedzinaWiedczyMenu.getItems().add(new_item);
        }

    }

    private void addKategories(ArrayList<String> kategories) {
        for (String k : kategories) {
            CheckMenuItem new_item = new CheckMenuItem(k);
            new_item.setOnAction((ActionEvent event) -> {
                kategoriaMenuButtonAction(event);
            });
            KategorieMenu.getItems().add(new_item);
        }
    }

    private void addStopnie(ArrayList<SlownikStopni> stopnie) {
        for (SlownikStopni s : stopnie) {
            CheckMenuItem new_item = new CheckMenuItem(s.getNazwa_stopnia());
            new_item.setId(Integer.toString(s.getId()));
            StopnieMenu.getItems().add(new_item);
        }

    }

    private void addColumns() {
        for (String n : Osiagniecia.getAllColumns()) {
            TableColumn column = new TableColumn(n);
            column.setPrefWidth(columnWith);
            column.setCellValueFactory(new PropertyValueFactory<Osiagniecia,String>(n));
            table.getColumns().add(column);
        }
    }

    @FXML
    void initialize() {
        addDziedzinaWiedzy(DziedzinaWiedzy.getAll());
        addEtykiety(Etykiety.getAll());
        addKategories(SlownikStopni.getAllKategories());
        addTyp(TypyOsiagniec.getAll());
        addColumns();
        // StopnieMenu.hide();

    }

    private void kategoriaMenuButtonAction(ActionEvent event) {
        List<MenuItem> items = KategorieMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        StopnieMenu.getItems().clear();
        for (MenuItem i : items) {
            addStopnie(SlownikStopni.getByKategory(i.getText()));
        }

    }

    private void dziedzinaWiedzyMenuButtonAction(ActionEvent event) {
        List<MenuItem> items = DziedzinaWiedczyMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        EtykietyMenu.getItems().clear();

        if (items.isEmpty())
            addEtykiety(Etykiety.getAll());
        else {
            for (MenuItem i : items)
                addEtykiety(DziedzinaWiedzy.getEtykietyByIdDziedziny(Integer.parseInt(i.getId())));
        }
    }

    public void DziedzinaWiedzyMenu() {

    }

    public void EtykietyMenuAction() {

    }

    public void KategorieMenuAction() {

    }

    public void buttonAction() {
        List<MenuItem> itemsD = DziedzinaWiedczyMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        List<MenuItem> itemsE = EtykietyMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        List<MenuItem> itemsType = TypMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        List<MenuItem> itemsStopnie = StopnieMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        Osiagniecia.initQuery();

        Osiagniecia.makeQueryByDziedzina(itemsD);
        Osiagniecia.makeQueryByEtykieta(itemsE);
        Osiagniecia.makeQueryByType(itemsType);
        Osiagniecia.makeQueryByStopien(itemsStopnie);

        ArrayList <Osiagniecia> osiagniecia = Osiagniecia.executeQuery();

        int i = 0;
        table.getItems().clear();
        for (Osiagniecia o : osiagniecia) {
            table.getItems().add(i++, o);
        }

    }
}
