package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;

public class QBFBuilder implements ISQLBuilderStatic {

    private final String m_sSentNullFilter;   // la sentencia que se devuelve cuando el filtro es vacio
    private final String
    m_sSentBeginPart;  // La sentencia que se devuelve es m_sSentBeginPart + ( filtro ) + m_sSentEndPart
    private final String m_sSentEndPart;

    private final String[] m_asFindFields;

//    public QBFBuilder(TableDefinition tb, String[] asFindFields) {
//        StringBuilder sent = new StringBuilder();
//        sent.append("select ");
//        for (int i = 0; i < tb.getFields().length; i ++) {
//            if (i > 0) {
//                sent.append(", ");
//            }
//            sent.append(tb.getFields()[i]);
//        }
//        sent.append(" from ");
//        sent.append(tb.getTableName());
//        m_sSentNullFilter = sent.toString();
//        sent.append(" where ");
//        m_sSentBeginPart = sent.toString();
//        m_sSentEndPart = "";
//        m_asFindFields = asFindFields;
//    }

    public QBFBuilder(String sSentence, String[] asFindFields) {
        int iPos = sSentence.indexOf("?(QBF_FILTER)");
        if (iPos < 0) {
            m_sSentBeginPart = sSentence;
            m_sSentEndPart = "";
            m_sSentNullFilter = sSentence;
        } else {
            m_sSentBeginPart = sSentence.substring(0, iPos);
            m_sSentEndPart = sSentence.substring(iPos + 13);
            m_sSentNullFilter = m_sSentBeginPart + "(1=1)" + m_sSentEndPart;
        }
        m_asFindFields = asFindFields;
    }

    @Override
    public String getSQL(SerializerWrite sw, Object params) throws BasicException {

        QBFParameter mydw = new QBFParameter(m_asFindFields);
        if (sw == null || params == null) {
            return m_sSentNullFilter;
        } else {
            sw.writeValues(mydw, params);
            String sFilter = mydw.getFilter();
            if (sFilter.length() == 0) {
                return m_sSentNullFilter; // no hay filtro
            } else {
                return m_sSentBeginPart + "(" + sFilter + ")" + m_sSentEndPart; // incluimos el filtro
            }
        }
    }

    private static class QBFParameter implements DataWrite {

        private final String[] m_asFindFields;
        private final QBFCompareEnum[] m_aiCondFields;
        private final String[] m_aParams;

        public QBFParameter(String[] asFindFields) {
            m_asFindFields = asFindFields;
            m_aiCondFields = new QBFCompareEnum[asFindFields.length];
            m_aParams = new String[asFindFields.length];

            for (int i = 0; i < m_aParams.length; i++) {
                m_aParams[i] = DataWriteUtils.getSQLValue((Object) null);
            }
        }

        @Override
        public void setDouble(int paramIndex, Double dValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(dValue);
            }
        }
        @Override
        public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(bValue);
            }
        }
        @Override
        public void setInt(int paramIndex, Integer iValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(iValue);
            }
        }
        @Override
        public void setString(int paramIndex, String sValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(sValue);
            }
        }
        @Override
        public void setTimestamp(int paramIndex, java.util.Date dValue) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(dValue);
            }
        }
//        public void setBinaryStream(int paramIndex, java.io.InputStream in, int length) throws DataException{
//            if ((paramIndex - 1) % 2 == 0) {
//                throw new DataException("Expected comparator for QBF");
//            } else {
//                throw new DataException("Param type not allowed");
//            }
//        }
        @Override
        public void setBytes(int paramIndex, byte[] value) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                throw new BasicException(LocalRes.getIntString("exception.nocompare"));
            } else {
                throw new BasicException("Param type not allowed");
            }
        }
        @Override
        public void setObject(int paramIndex, Object value) throws BasicException {
            if ((paramIndex - 1) % 2 == 0) {
                if (value instanceof QBFCompareEnum) {
                    m_aiCondFields[(paramIndex - 1) / 2] = (QBFCompareEnum) value;
                } else {
                    throw new BasicException(LocalRes.getIntString("exception.nocompare"));
                }
            } else {
                m_aParams[(paramIndex - 1) / 2] = DataWriteUtils.getSQLValue(value);
            }
        }

        public String getFilter() {
            // El retorno debe ser siempre una expresion valida puesto que no se donde sera insertada.

            StringBuilder sFilter = new StringBuilder();

            String sItem;
            for (int i = 0; i < m_asFindFields.length; i ++) {
                sItem = m_aiCondFields[i].getExpression(m_asFindFields[i], m_aParams[i]);
                if (sItem != null) {
                    if (sFilter.length() > 0) {
                        sFilter.append(" AND ");
                    }
                    sFilter.append(sItem);
                }
            }

            return sFilter.toString();
        }
    }
}
