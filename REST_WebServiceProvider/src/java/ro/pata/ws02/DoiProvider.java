/*
 * Folosit doar ca exemplu pentru a demonstra cum se adauga si al doilea endpoint.
 */
package ro.pata.ws02;

import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.http.HTTPBinding;

/**
 *
 * @author adi
 */
@WebServiceProvider
@ServiceMode(javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(HTTPBinding.HTTP_BINDING)
public class DoiProvider implements Provider<Source>{

    @Override
    public Source invoke(Source request) {
        return new StreamSource(new StringReader("<msg>Test</msg>"));
    }
    
}
