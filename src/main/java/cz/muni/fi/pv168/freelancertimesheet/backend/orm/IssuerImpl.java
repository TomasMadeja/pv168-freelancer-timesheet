package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllIssuers",
                        query = "from IssuerImpl"
                )
        }
)
@javax.persistence.Entity
@Table(name="issuers")
public class IssuerImpl extends EntityImpl implements Issuer {

    public IssuerImpl(String name, String address, String ico, String dic, String phoneNumber, String email) {
        super(name, address, ico, dic, phoneNumber, email);
    }

    public IssuerImpl() {
        super();
    }

    public static Entity createEntity(String name, String address, String ico, String dic, String phoneNumber, String email) {
        return new IssuerImpl(name, address, ico, dic, phoneNumber, email);
    }

    public String toXML() {
        String xml = String.format(
                "            <h2 class=\"title\">%s</h2>\n" +
                "            <p>\n" +
                "                %s\n" +
                "            </p>\n" +
                "            <p><b>ICO:</b> %s</p>\n" +
                "            <p><b>DIC:</b> %s</p>\n",
                name,
                address,
                ico,
                dic
        );
        return xml;
    }
}
