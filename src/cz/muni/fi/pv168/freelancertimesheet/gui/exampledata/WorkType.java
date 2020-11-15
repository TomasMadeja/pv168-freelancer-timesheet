package cz.muni.fi.pv168.freelancertimesheet.gui.exampledata;

import java.util.ArrayList;
import java.util.List;

public class WorkType {
    public final String name;
    public final double rate;
    public final String description;

    public WorkType(String name, double rate, String description) {
        this.name = name;
        this.rate = rate;
        this.description = description;
    }

    public static List<WorkType> getExampleData() {
        return new ArrayList<>(List.of(
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
