package cz.muni.fi.pv168.freelancertimesheet.gui.exampledata;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import java.time.ZonedDateTime;

public class RandomDataGenerator {

    public static void generateWorkData(TableModel<Work> table) {
//        table.addRow(new WorkImpl().createWork(
//                "Funny name",
//                "This is a funny description",
//                ZonedDateTime.now(),
//                ZonedDateTime.now(),
//                WorkTypeImpl.createWorkType(
//                        "Work Type",
//                        "Generic description",
//                        "10.5"
//                )
//        ));
//        table.addRow(new WorkImpl().createWork(
//                "Funny name2",
//                "This is a funny description2",
//                ZonedDateTime.now(),
//                ZonedDateTime.now(),
//                WorkTypeImpl.createWorkType(
//                        "Work Type",
//                        "Generic description",
//                        "10.5"
//                )
//        ));
    }
}
