package com.openbravo.pos.sales.simple;

import com.openbravo.pos.forms.*;
import javax.swing.*;
import com.openbravo.pos.sales.*;
import com.openbravo.pos.ticket.TicketInfo;

public class JTicketsBagSimple extends JTicketsBag {

    public JTicketsBagSimple(AppView app, TicketsEditor panelticket) {

        super(app, panelticket);

        initComponents();
    }

    public void activate() {

        m_panelticket.setActiveTicket(new TicketInfo(), null);

        // Authorization
        m_jDelTicket.setEnabled(
            m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));

    }

    public boolean deactivate() {
        m_panelticket.setActiveTicket(null, null);
        return true;
    }

    public void deleteTicket() {
        m_panelticket.setActiveTicket(new TicketInfo(), null);
    }

    protected JComponent getBagComponent() {
        return this;
    }

    protected JComponent getNullComponent() {
        return new JPanel();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jDelTicket = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jDelTicket.setIcon(new javax.swing.ImageIcon(
                                 getClass().getResource("/images/sale_delete.png"))); // NOI18N
        m_jDelTicket.setToolTipText("Delete current Sale");
        m_jDelTicket.setFocusPainted(false);
        m_jDelTicket.setFocusable(false);
        m_jDelTicket.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jDelTicket.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setRequestFocusEnabled(false);
        m_jDelTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDelTicketActionPerformed(evt);
            }
        });
        add(m_jDelTicket);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jDelTicketActionPerformed(java.awt.event.ActionEvent
                                             evt) {//GEN-FIRST:event_m_jDelTicketActionPerformed

        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"),
                                                AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            deleteTicket();
        }

    }//GEN-LAST:event_m_jDelTicketActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_jDelTicket;
    // End of variables declaration//GEN-END:variables

}
