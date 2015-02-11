package com.openbravo.pos.printer;

import com.openbravo.pos.forms.AppLocal;

public class DeviceDisplayNull implements DeviceDisplay {

    private String m_sName;
    private String m_sDescription;

    /**
     * Creates a new instance of DeviceDisplayNull */
    public DeviceDisplayNull() {
        this(null);
    }

    /**
     * Creates a new instance of DeviceDisplayNull
     * @param desc */
    public DeviceDisplayNull(String desc) {
        m_sName = AppLocal.getIntString("Display.Null");
        m_sDescription = desc;
    }

    @Override
    public String getDisplayName() {
        return m_sName;
    }

    @Override
    public String getDisplayDescription() {
        return m_sDescription;
    }

    @Override
    public javax.swing.JComponent getDisplayComponent() {
        return null;
    }

    @Override
    public void clearVisor() {
    }

    @Override
    public void writeVisor(String sLine1, String sLine2) {
    }

    @Override
    public void writeVisor(int animation, String sLine1, String sLine2) {
    }
}
