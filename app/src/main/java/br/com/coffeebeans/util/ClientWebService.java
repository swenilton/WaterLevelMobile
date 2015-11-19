package br.com.coffeebeans.util;
import android.os.AsyncTask;
import android.util.Log;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.service.ServiceFinder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.core.MediaType;
import br.com.coffeebeans.exception.ClientWebServiceException;
import br.com.coffeebeans.usuario.Usuario;

/**
 * Created by AndréFillipe on 06/11/2015.
 */
public class ClientWebService {
    private WebResource webResource;
    private ClientResponse response;
    private Collection<Usuario> listaUsers;

    public void exemploGetUsers() throws ClientWebServiceException {
        try {
            ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            clientConfig.getClasses().add(JacksonJsonProvider.class);

            Client client = Client.create(clientConfig);

            webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS/user/all");
            ThreadResponse threadResponse = new ThreadResponse();
            threadResponse.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } catch (Exception e) {
            throw new ClientWebServiceException(e);
        }
    }

    public class ThreadResponse extends AsyncTask<Void, Void, String> {
        //TODO //como tratar exceções nesse método?
        @Override
        protected String doInBackground(Void... params) {
            try {

                response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                listaUsers = new ObjectMapper().readValue(
                        response.getEntity(String.class),
                        new TypeReference<ArrayList<Usuario>>() {
                        });

            } catch (Exception e) {
                Log.i("", "getMessage :" + e.getMessage());
                Log.i("", "getCause" + String.valueOf(e.getCause()));
                Log.i("", "getStrackTrace " + String.valueOf(e.getStackTrace()));
                e.printStackTrace();

//                throw new ClientWebServiceException(e);
            }
            return "sucesso na execucao da thread";
        }

        @Override
        protected void onPostExecute(String message) {

            Log.i("", message);
            Log.i("", "Resposta do servidor: " + listaUsers.toString());

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
        }
    }

}