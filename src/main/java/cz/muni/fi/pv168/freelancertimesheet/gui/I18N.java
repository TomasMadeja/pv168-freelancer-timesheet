package cz.muni.fi.pv168.freelancertimesheet.gui;

import java.util.ResourceBundle;

public class I18N {

    private final ResourceBundle bundle;
    private final String prefix;

    I18N(Class<?> clazz) {
        var packagePath = "";
        bundle = ResourceBundle.getBundle(packagePath + "i18n");
        prefix = clazz.getSimpleName() + ".";
    }

    String getString(String key) {
        return bundle.getString(prefix + key);
    }

    <E extends Enum<E>> String getString(E key) {
        return bundle.getString(key.getClass().getSimpleName() + "." + key.name());
    }
}
