//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta
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

package com.openbravo.pos.payment;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.RoundUtils;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class JPaymentCheque extends javax.swing.JPanel implements JPaymentInterface {

    private JPaymentNotifier m_notifier;

    private double m_dPaid;
    private double m_dTotal;
    private Boolean priceWith00;

    /**
     * Creates new form JPaymentCash
     * @param notifier */
    public JPaymentCheque(JPaymentNotifier notifier) {

        m_notifier = notifier;

        initComponents();

        m_jTendered.addPropertyChangeListener("Edition", new RecalculateState());
        m_jTendered.addEditorKeys(m_jKeys);

 /* added JDL 11.05.13
        AppConfig m_config =  new AppConfig(new File((System.getProperty("user.home")), AppLocal.APP_ID + ".properties"));
        m_config.load();
        priceWith00 =("true".equals(m_config.getProperty("till.pricewith00")));
        if (priceWith00) {
            // use '00' instead of '.'
            m_jKeys.dotIs00(true);
        }
        m_config=null;
*/


    }

    @Override
    public void activate(CustomerInfoExt customerext, double dTotal, String transID) {

        m_dTotal = dTotal;


        m_jTendered.reset();
        m_jTendered.activate();

        printState();

    }

    @Override
    public PaymentInfo executePayment() {
        return new PaymentInfoTicket(m_dPaid, "cheque");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void printState() {

        Double value = m_jTendered.getDoubleValue();
        if (value == null) {
            m_dPaid = m_dTotal;
        } else {
            m_dPaid = value;
        }

        m_jMoneyEuros.setText(Formats.CURRENCY.formatValue(new Double(m_dPaid)));

        int iCompare = RoundUtils.compare(m_dPaid, m_dTotal);

        // if iCompare > 0 then the payment is not valid
        m_notifier.setStatus(m_dPaid > 0.0 && iCompare <= 0, iCompare == 0);
    }

    private class RecalculateState implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            printState();
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel3 = new javax.swing.JPanel();
        m_jTendered = new com.openbravo.editor.JEditorCurrencyPositive();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        m_jMoneyEuros = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(m_jKeys);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        m_jTendered.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jPanel3.add(m_jTendered, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.add(jPanel1, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("Label.InputCash")); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel4.add(jLabel8);
        jLabel8.setBounds(10, 4, 100, 30);

        m_jMoneyEuros.setBackground(new java.awt.Color(204, 255, 51));
        m_jMoneyEuros.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        m_jMoneyEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jMoneyEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jMoneyEuros.setOpaque(true);
        m_jMoneyEuros.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(m_jMoneyEuros);
        m_jMoneyEuros.setBounds(120, 4, 180, 30);

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JLabel m_jMoneyEuros;
    private com.openbravo.editor.JEditorCurrencyPositive m_jTendered;
    // End of variables declaration//GEN-END:variables

}
