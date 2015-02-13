package com.openbravo.pos.scripting;

import com.openbravo.pos.forms.AppView;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;

/**
 *
 * Created on 5 de marzo de 2007, 19:57
 *
 */
class ScriptEngineVelocity implements ScriptEngine {

    private static VelocityEngine m_ve = null;

    private VelocityContext c = null;
    private AppView m_App;


    public ScriptEngineVelocity() throws ScriptException {

        if (m_ve == null) {
            // Inicializo Velocity
            m_ve = new VelocityEngine();
            // ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, this);

            //        m_ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
            //        // m_ve.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
            //        // m_ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            //        m_ve.setProperty("class.resource.loader.class", "com.openbravo.pos.forms.SystemResourceLoader");
            //        m_ve.setProperty("class.resource.loader.description", "Velocity Resource Loader");
            //        m_ve.setProperty("class.resource.loader.appresources", this);

            m_ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
                             "org.apache.velocity.runtime.log.NullLogSystem");
            m_ve.setProperty(VelocityEngine.ENCODING_DEFAULT, "UTF-8");
            m_ve.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
            try {
                m_ve.init();
            } catch (Exception e) {
                throw new ScriptException("Cannot initialize Velocity Engine", e);
            }
        }
        c = new VelocityContext();
    }

    @Override
    public void put(String key, Object value) {
        c.put(key, value);
    }
    @Override
    public Object get(String key) {
        return c.get(key);
    }

    @Override
    public Object eval(String src) throws ScriptException {
        if (m_ve == null) {
            throw new ScriptException("Velocity engine not initialized.");
        } else {
            Writer w = new StringWriter();
            try {
                if (m_ve.evaluate(c, w, "log", new StringReader(src))) {
                    return w.toString();
                } else {
                    throw new ScriptException("Velocity engine unexpected error.");
                }
            } catch (ParseErrorException e) {
                throw new ScriptException(e.getMessage(), e);
            } catch (MethodInvocationException e) {
                throw new ScriptException(e.getMessage(), e);
            } catch (Exception e) {
                throw new ScriptException(e.getMessage(), e);
            }
        }
    }
}
