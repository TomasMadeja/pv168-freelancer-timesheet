package cz.muni.fi.pv168.freelancertimesheet.gui.exampledata;

import java.util.ArrayList;
import java.util.List;

public class WorkType {
    public String name;
    public double rate;
    public String description;

    public WorkType(String name, double rate, String description) {
        this.name = name;
        this.rate = rate;
        this.description = description;
    }

    public static List<WorkType> getExampleData() {
        List<WorkType> list = new ArrayList<WorkType>(List.of(
                new WorkType(
                        "Coding",
                        10.5,
                        "WritingCode"
                ),
                new WorkType(
                        "GraphicDesign",
                        25,
                        "Doing graphic design"
                )
        ));
        return list;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }
}
