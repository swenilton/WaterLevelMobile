package br.com.coffeebeans.leitura;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.server.impl.cdi.InitializedLater;
import com.sun.jersey.spi.service.ServiceFinder;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.sql.SQLException;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

/**
 * Created by AndréFillipe on 07/12/2015.
 */
public class LeituraDaoWs implements ILeituraDAO {
    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private Double leitura;

   public LeituraDaoWs() throws Exception {
       ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
       ClientConfig clientConfig = new DefaultClientConfig();
       clientConfig.getFeatures().put(
               JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
       clientConfig.getClasses().add(JacksonJsonProvider.class);
       client = Client.create(clientConfig);
       objectMapper=new ObjectMapper();

   }

    @Override
    public Double getUltimaLeitura(int idRepositorio) throws SQLException, DAOException {
        try{
            webResource = client
                    .resource("http://localhost:8080/WaterLevel/WS6/leitura/getUltimaLeitura/"+idRepositorio);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            double leitura = objectMapper.readValue(
                    response.getEntity(String.class),
                    new TypeReference<Double>() {
                    });

        }catch(Exception e) {
            throw new DAOException(e);
        }
        return leitura;
    }
}
