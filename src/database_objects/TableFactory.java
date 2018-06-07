package database_objects;

import java.sql.ResultSet;

public class TableFactory {

    static public TableObj CreateTableObj(TableType type, ResultSet rs) {
        try {
            switch (type) {
                case Etykiety:
                    return new Etykiety();
                case DziedzinaWiedzy:
                    return new DziedzinaWiedzy();
                case Jednostki:
                    return new Jednostki();
                case Oceny:
                    return new Oceny();
                case SlownikStopni:
                    return new SlownikStopni();
                case StopnieZaawansowania:
                    return new StopnieZaawansowania();
                case PlikiPotwierdzajace:
                    return new PlikiPotwierdzajace();
                case Uzytkownicy:
                    return new Uzytkownicy();
                case TypyOsiagniec:
                    return new TypyOsiagniec();
                case Osiagniecia: //return new Osiagniecia();
                    return CreateOsiagnieciaObj(rs.getInt("id_typu"));

                default:
                    return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public TableObj CreateOsiagnieciaObj(int type_id) {
        TypyOsiagniec type = TypyOsiagniec.getById(type_id);

        switch (type.getTyp()) {
            case "Wykształcenie": return new Wyksztalcenie();
            case "Szkolenie": return new Szkolenia();
            case "Licencja/Uprawnienie": return new Uprawnienia();
            default: return new Osiagniecia();
        }
    }
}
