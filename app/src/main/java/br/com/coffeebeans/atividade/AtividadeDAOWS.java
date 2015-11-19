package br.com.coffeebeans.atividade;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

public class AtividadeDAOWS implements IAtividadeDAO {
    private Client client;
    private WebResource webResource;
    public AtividadeDAOWS() throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
    }

    @Override
    public boolean existe(String descricao) throws SQLException, DAOException {
        webResource = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/existe/"
                        + descricao.replace(" ", "%20"));

        ClientResponse response = webResource.type(
                MediaType.APPLICATION_JSON).get(ClientResponse.class);

        Boolean existe = null;
        try {
            existe = new ObjectMapper().readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() { });
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return existe;
    }

    @Override
    public void cadastrar(Atividade atividade) throws SQLException, DAOException {
        webResource = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/add");
        webResource.type(MediaType.APPLICATION_JSON).post(atividade);
    }

    @Override
    public List<Atividade> listar() throws SQLException, DAOException {
        List<Atividade> list2 = new ArrayList<>();
        webResource = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/all");

        ClientResponse response = webResource.type(
                MediaType.APPLICATION_JSON).get(ClientResponse.class);

        Collection<Atividade> list = null;
        try {
            list = new ObjectMapper().readValue(
                    response.getEntity(String.class),
                    new TypeReference<ArrayList<Atividade>>() {
                    });
        if (list instanceof List)
            list2 = (List)list;
        else
            list2 = new ArrayList(list);
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return list2;
    }

    @Override
    public Atividade procurar(int id) throws SQLException, DAOException {
        WebResource webResource4 = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/procurar/" + id);

        ClientResponse response3 = webResource4.type(
                MediaType.APPLICATION_JSON).get(ClientResponse.class);

        Atividade atividade = null;
        try {
            atividade = new ObjectMapper().readValue(
                    response3.getEntity(String.class),
                    new TypeReference<Atividade>() {
                    });
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return atividade;
    }

    @Override
    public void atualizar(Atividade atividade) throws  SQLException, DAOException {
        webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/update");
        webResource.type(MediaType.APPLICATION_JSON).put(atividade);
    }

    @Override
    public void excluir(int id) throws SQLException,DAOException {
        WebResource webResource6 = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividade/excluir/" + id);
        ClientResponse response = webResource6.type(
                MediaType.APPLICATION_JSON).delete(ClientResponse.class);
    }
}
