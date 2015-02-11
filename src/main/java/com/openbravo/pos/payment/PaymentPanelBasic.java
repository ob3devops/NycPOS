package com.openbravo.pos.payment;

import com.openbravo.pos.forms.AppLocal;
import javax.swing.JComponent;

public class PaymentPanelBasic extends javax.swing.JPanel implements PaymentPanel {

    private double m_dTotal;
    private String m_sTransactionID;
    private JPaymentNotifier m_notifier;

    /**
     * Creates new form PaymentPanelSimple
     * @param notifier
     */
    public PaymentPanelBasic(JPaymentNotifier notifier) {

        m_notifier = notifier;
        initComponents();
    }

    @Override
    public JComponent getComponent(){
        return this;
    }

    @Override
    public void activate(String sTransaction, double dTotal) {

        m_sTransactionID = sTransaction;
        m_dTotal = dTotal;

        jLabel1.setText(
                m_dTotal > 0.0
                ? AppLocal.getIntString("message.paymentgatewayext")
                : AppLocal.getIntString("message.paymentgatewayextrefund"));

        m_notifier.setStatus(true, true);
    }

    @Override
    public PaymentInfoMagcard getPaymentInfoMagcard() {

        if (m_dTotal > 0.0) {
            return new PaymentInfoMagcard(
                    "",
                    "",
                    "",
                    null,
                    null,
                    null,
                    m_sTransactionID,
                    m_dTotal);
        } else {
            return new PaymentInfoMagcardRefund(
                    "",
                    "",
                    "",
                    null,
                    null,
                    null,
                    m_sTransactionID,
                    m_dTotal);
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();

        add(jLabel1);

    }
    // </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
