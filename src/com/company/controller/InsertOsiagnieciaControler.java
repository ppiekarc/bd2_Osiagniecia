package com.company.controller;

import database_objects.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InsertOsiagnieciaControler {
    @FXML
    private Button button;

    @FXML
    private MenuButton DziedzinaWiedczyMenu, EtykietyMenu, KategorieMenu, StopnieMenu,
            JednostkaMenu, TypMenu;

    @FXML
    private TextField CzasSzkoleniaText, NazwaSzkolyText, NumerLicencjiText,
            IdUrzytkownikaText, NazwaOsiagnieciaText, NazwaPodmiotuText,
            MiejsceUzyskaniaText, OcenaText, OpisText;

    @FXML
    private DatePicker DataUzyskania, DataWygasniecia;


    private ToggleGroup TypToggleGroup = new ToggleGroup();
    private ToggleGroup JednostkaToggleGroup = new ToggleGroup();


    private void addTyp(ArrayList<TypyOsiagniec> typy) {
        for (TypyOsiagniec t : typy) {
            RadioMenuItem new_item = new RadioMenuItem(t.getTyp());
            new_item.setId(Integer.toString(t.getId()));
            new_item.setToggleGroup(TypToggleGroup);

            new_item.setOnAction((ActionEvent event) -> {
                TypMenuMenuButtonAction(event);
            });

            TypMenu.getItems().add(new_item);
        }
    }

    private void addJednostki(ArrayList<Jednostki> jednostki) {
        for (Jednostki j : jednostki) {
            RadioMenuItem new_item = new RadioMenuItem(j.getRodzaj_skali());
            new_item.setId(Integer.toString(j.getId()));
            new_item.setToggleGroup(JednostkaToggleGroup);
            JednostkaMenu.getItems().add(new_item);
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

    private void TypMenuMenuButtonAction(ActionEvent event) {

        RadioMenuItem item = (RadioMenuItem )TypToggleGroup.getSelectedToggle();
        String value = item.getText();

        CzasSzkoleniaText.setDisable(true);
        NazwaSzkolyText.setDisable(true);
        NumerLicencjiText.setDisable(true);

        System.out.println(value);

        switch (value) {
            case "Wykształcenie":
                NazwaSzkolyText.setDisable(false);
                break;
            case "Licencja/Uprawnienie":
                NumerLicencjiText.setDisable(false);
                break;
            case "Szkolenie":
                CzasSzkoleniaText.setDisable(false);

        }
    }

    @FXML
    void initialize() {
        addDziedzinaWiedzy(DziedzinaWiedzy.getAll());
        addEtykiety(Etykiety.getAll());
        addKategories(SlownikStopni.getAllKategories());
        addTyp(TypyOsiagniec.getAll());
        addJednostki(Jednostki.getAll());

        // StopnieMenu.hide();

    }

    public void buttonAction() {
        StringBuilder errorText = new StringBuilder();
        List<MenuItem> itemsD = DziedzinaWiedczyMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        List<MenuItem> itemsE = EtykietyMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());

        RadioMenuItem itemType = (RadioMenuItem )TypToggleGroup.getSelectedToggle();
        RadioMenuItem itemJednostka = (RadioMenuItem )JednostkaToggleGroup.getSelectedToggle();
        List<MenuItem> itemsStopnie = StopnieMenu.getItems().filtered(item -> CheckMenuItem.class.isInstance(item)
                && CheckMenuItem.class.cast(item).isSelected());


        Oceny ocena = new Oceny();
        int typ = 3;
        try {
            typ = Integer.parseInt(itemType.getId());
        }
        catch (Exception e) {
            errorText.append("Wybierz typ osiągnięcia \n");
        }
        Osiagniecia osiagniecie = (Osiagniecia) TableFactory.CreateOsiagnieciaObj(typ);

        try {
            ocena.setWartosc(Integer.parseInt(OcenaText.getCharacters().toString()));
        }
        catch (Exception e) {
            errorText.append("Ocena musi byc liczba\n");
        }

        try {
            ocena.setId_jednostki(Integer.parseInt(itemJednostka.getId()));
        }
        catch (Exception e) {
            errorText.append("Ocena musi posiadac jednostke\n");
        }

        ocena.insert();

        if (DataUzyskania.getValue() != null)
            osiagniecie.setData_uzyskania(Timestamp.valueOf(DataUzyskania.getValue().atStartOfDay()));
        if (DataWygasniecia.getValue() != null)
            osiagniecie.setData_wygasniecia(Timestamp.valueOf(DataWygasniecia.getValue().atStartOfDay()));

        osiagniecie.setId_oceny(ocena.getId());
        try {
            osiagniecie.setId_uzytkownika(Integer.parseInt(IdUrzytkownikaText.getText()));
        }
        catch (Exception e) {
            errorText.append("Id uzytkownika musi byc liczbą oraz nie moze pozostac puste\n");
        }
        osiagniecie.setId_typu(typ);
        osiagniecie.setMiejsce_uzyskania(MiejsceUzyskaniaText.getText());
        if (NazwaOsiagnieciaText.getText().length() > 0)
            osiagniecie.setNazwa(NazwaOsiagnieciaText.getText());
        else
            errorText.append("Nazwa Osiągnięcia nie może pozostać pusta\n");

        osiagniecie.setNazwa_podmiotu(NazwaPodmiotuText.getText());
        if (!NazwaSzkolyText.isDisable())
            osiagniecie.setNazwa_szkoly(NazwaSzkolyText.getText());

        if (!NumerLicencjiText.isDisable()) {
            try {
                osiagniecie.setNumer_licencji(Integer.parseInt(NumerLicencjiText.getText()));
            }
            catch (Exception e) {
                errorText.append("Numer licencji musi byc liczba, oraz nie moze postać puste");
            }
        }

        if (!CzasSzkoleniaText.isDisable()) {
            try {
                String czasSzkolenia = CzasSzkoleniaText.getText();
                if (czasSzkolenia.length() > 0)
                    osiagniecie.setCzas_szkolenia(Integer.parseInt(czasSzkolenia));
            }
            catch (Exception e) {
                errorText.append("Czas szkolenia musi byc liczba\n");
            }
        }

        osiagniecie.setOpis(OpisText.getText());

        if (errorText.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText("Blad");
            alert.setContentText(errorText.toString());

            alert.showAndWait();
            return;
        }

        osiagniecie.insert();

        for (MenuItem i : itemsD)
            osiagniecie.insertDziedziny(Integer.parseInt(i.getId()));

        for (MenuItem i : itemsE) {
            StopnieZaawansowania s = new StopnieZaawansowania();
            s.setId_etykiety(Integer.parseInt(i.getId()));
            s.setId_osiagniecia(osiagniecie.getId());
            s.setStopien(" f ");
            s.insert();
        }

        for (MenuItem i : itemsStopnie) {
            StopnieZaawansowania s = new StopnieZaawansowania();
            s.setId_stopnia(Integer.parseInt(i.getId()));
            s.setId_osiagniecia(osiagniecie.getId());
            s.setStopien(" f ");
            s.insert();
        }
    }
}
