//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta & previous Openbravo POS works
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.printer.screen;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

// JG 16 May 12 replace
// import java.awt.*;
// import java.awt.event.*;
// import java.awt.image.*;
// import java.util.*;
// import java.awt.print.*;

// import javax.swing.*;
// import javax.swing.border.*;
// import javax.swing.event.*;

class JTicketContainer extends javax.swing.JPanel {

    protected int H_GAP = 8;
    protected int V_GAP = 8;

    /**
     * Creates new form JTicketContainer */
    public JTicketContainer() {
        initComponents();
        setLayout(null);
    }

    @Override
    public Dimension getPreferredSize() {

        Insets ins = getInsets();
        int iMaxx = 0;
        int iMaxy = ins.top + V_GAP;
        int n = getComponentCount();
        for(int i = 0; i < n; i++) {
            Component comp = getComponent(i);
            Dimension dc = comp.getPreferredSize();
            if (dc.width > iMaxx) {
                iMaxx = dc.width;
            }
            iMaxy += V_GAP + dc.height;
        }

        return new Dimension(iMaxx + 2 * H_GAP + ins.left + ins.right, iMaxy + ins.bottom);
    }

    @Override
    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    @Override
    public void doLayout() {
        Insets ins = getInsets();
        int x = ins.left + H_GAP;
        int y = ins.top + V_GAP;

        int n = getComponentCount();
        for(int i = 0; i < n; i++) {
            Component comp = getComponent(i);
            Dimension dc = comp.getPreferredSize();

            comp.setBounds(x, y, dc.width, dc.height);
            y += V_GAP + dc.height;
        }
    }

    public void addTicket(JTicket ticket) {

        add(ticket);

        doLayout();
        revalidate();
        scrollRectToVisible(new Rectangle(0, getPreferredSize().height - 1, 1, 1));
    }

    public void removeAllTickets() {

        removeAll();

        doLayout();
        revalidate();
        scrollRectToVisible(new Rectangle(0, 0, 1, 1));
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
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
