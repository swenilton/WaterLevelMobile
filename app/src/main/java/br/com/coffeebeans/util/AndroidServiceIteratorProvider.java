package br.com.coffeebeans.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.sun.jersey.spi.service.ServiceFinder.ServiceIteratorProvider;

/**
 * Created by AndréFillipe on 05/11/2015.
 */
public class AndroidServiceIteratorProvider<T> extends ServiceIteratorProvider<T> {
    private static final String TAG = AndroidServiceIteratorProvider.class.getSimpleName();
    private static final String MESSAGE = "Unable to load provider";

    private static final HashMap<String, String[]> SERVICES = new HashMap<String, String[]>();

    private static final String[] com_sun_jersey_spi_HeaderDelegateProvider = {
            "com.sun.jersey.core.impl.provider.header.MediaTypeProvider",
            "com.sun.jersey.core.impl.provider.header.StringProvider"

    };

    private static final String[] com_sun_jersey_spi_inject_InjectableProvider = {/*"com.sun.jersey.core.impl.provider.xml.XMLStreamReaderContextProvider", "com.sun.jersey.core.impl.provider.xml.SAXParserContextProvider",
            "com.sun.jersey.core.impl.provider.xml.DocumentBuilderFactoryProvider", "com.sun.jersey.core.impl.provider.xml.TransformerFactoryProvider"
   */ };

    private static final String[] javax_ws_rs_ext_MessageBodyReader = {
            "com.sun.jersey.core.impl.provider.entity.StringProvider",
            "com.sun.jersey.core.impl.provider.entity.ReaderProvider", "com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$General",
            "com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$General","com.sun.jersey.json.impl.provider.entity.JSONObjectProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONObjectProvider$General"
    };

    private static final String[] javax_ws_rs_ext_MessageBodyWriter = {
            "com.sun.jersey.core.impl.provider.entity.StringProvider",
            "com.sun.jersey.core.impl.provider.entity.ReaderProvider","com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$General",
            "com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$General","com.sun.jersey.json.impl.provider.entity.JSONObjectProvider$App",
            "com.sun.jersey.json.impl.provider.entity.JSONObjectProvider$General"
    };

    static {
        SERVICES.put("com.sun.jersey.spi.HeaderDelegateProvider",
                com_sun_jersey_spi_HeaderDelegateProvider);
        SERVICES.put("com.sun.jersey.spi.inject.InjectableProvider",
                com_sun_jersey_spi_inject_InjectableProvider);
        SERVICES.put("javax.ws.rs.ext.MessageBodyReader",
                javax_ws_rs_ext_MessageBodyReader);
        SERVICES.put("javax.ws.rs.ext.MessageBodyWriter",
                javax_ws_rs_ext_MessageBodyWriter);
        SERVICES.put("jersey-client-components", new String[]{});
        SERVICES.put("com.sun.jersey.client.proxy.ViewProxyProvider", new String[]{});
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Class<T>> createClassIterator(Class<T> service,
                                                  String serviceName, ClassLoader loader,
                                                  boolean ignoreOnClassNotFound) {

        String[] classesNames = SERVICES.get(serviceName);
        int length = classesNames.length;
        ArrayList<Class<T>> classes = new ArrayList<Class<T>>(length);
        for (int i = 0; i < length; i++) {
            try {
                classes.add((Class<T>) Class.forName(classesNames[i]));
            } catch (ClassNotFoundException e) {
                Log.v(TAG, MESSAGE, e);
            }
        }
        return classes.iterator();
    }

    @Override
    public Iterator<T> createIterator(Class<T> service, String serviceName,
                                      ClassLoader loader, boolean ignoreOnClassNotFound) {

        String[] classesNames = SERVICES.get(serviceName);
        int length = classesNames.length;
        ArrayList<T> classes = new ArrayList<T>(length);
        for (int i = 0; i < length; i++) {
            try {
                classes.add(service.cast(Class.forName(classesNames[i])
                        .newInstance()));
            } catch (IllegalAccessException e) {
                Log.v(TAG, MESSAGE, e);
            } catch (InstantiationException e) {
                Log.v(TAG, MESSAGE, e);
            } catch (ClassNotFoundException e) {
                Log.v(TAG, MESSAGE, e);
            }
        }

        return classes.iterator();
    }
}

