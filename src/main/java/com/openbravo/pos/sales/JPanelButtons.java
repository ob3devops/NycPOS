package com.openbravo.pos.sales;

import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppUser;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JPanelButtons extends javax.swing.JPanel {
    private static final Logger logger = LoggerFactory.getLogger(JPanelButtons.class);

    private static SAXParser m_sp = null;

    private Properties props;
    private Map<String, String> events;

    private ThumbNailBuilder tnbmacro;

    private JPanelTicket panelticket;

    public JPanelButtons(String sConfigKey, JPanelTicket panelticket) {
        initComponents();

        // Load categories default thumbnail
        tnbmacro = new ThumbNailBuilder(24, 24, "/images/run_script.png");

        this.panelticket = panelticket;

        props = new Properties();
        events = new HashMap<>();

        String sConfigRes = panelticket.getResourceAsXML(sConfigKey);

        if (sConfigRes != null) {
            try {
                if (m_sp == null) {
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    m_sp = spf.newSAXParser();
                }
                m_sp.parse(new InputSource(new StringReader(sConfigRes)), new ConfigurationHandler());

            } catch (ParserConfigurationException ePC) {
                logger.error(LocalRes.getIntString("exception.parserconfig"), ePC);
            } catch (SAXException eSAX) {
                logger.error(LocalRes.getIntString("exception.xmlfile"), eSAX);
            } catch (IOException eIO) {
                logger.error(LocalRes.getIntString("exception.iofile"), eIO);
            }
        }

    }

    public void setPermissions(AppUser user) {
        for (Component c : this.getComponents()) {
            String sKey = c.getName();
            if (sKey == null || sKey.equals("")) {
                c.setEnabled(true);
            } else {
                c.setEnabled(user.hasPermission(c.getName()));
            }
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return props.getProperty(key, defaultvalue);
    }

    public String getEvent(String key) {
        return events.get(key);
    }

    private class ConfigurationHandler extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {}
        @Override
        public void endDocument() throws SAXException {}
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            switch (qName) {
                case "button":
                    // The button title text
                    String titlekey = attributes.getValue("titlekey");
                    if (titlekey == null) {
                        titlekey = attributes.getValue("name");
                    }
                    String title = titlekey == null
                                   ? attributes.getValue("title")
                                   : AppLocal.getIntString(titlekey);
                    // adding the button to the panel
                    JButton btn = new JButtonFunc(attributes.getValue("key"),
                                                  attributes.getValue("image"),
                                                  title);
                    // The template resource or the code resource
                    final String template = attributes.getValue("template");
                    if (template == null) {
                        final String code = attributes.getValue("code");
                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                panelticket.evalScriptAndRefresh(code);
                            }
                        });
                    } else {
                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                panelticket.printTicket(template);
                            }
                        });
                    }
                    add(btn);
                    break;
                case "event":
                    events.put(attributes.getValue("key"), attributes.getValue("code"));
                    break;
                default:
                    String value = attributes.getValue("value");
                    if (value != null) {
                        props.setProperty(qName, attributes.getValue("value"));
                    }
                    break;
            }
        }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {}
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {}
    }

    private class JButtonFunc extends JButton {

        public JButtonFunc(String sKey, String sImage, String title) {

            setName(sKey);
            setText(title);
            logger.info(">>> JButtonFunc(): {}", sImage);
            setIcon(new ImageIcon(tnbmacro.getThumbNail(panelticket.getResourceAsImage(sImage))));
            setFocusPainted(false);
            setFocusable(false);
            setRequestFocusEnabled(false);
            setMargin(new Insets(8, 14, 8, 14));
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
