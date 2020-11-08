package cz.muni.fi.pv168.freelancertimesheet.gui;

public interface GenericElement<T extends GenericElement<T>> {

    public T setupLayout();
    public T setupVisuals();
    public T setupNested();

}
