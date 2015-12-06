package br.com.coffeebeans.bomba;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.BombaJaExistenteException;
import br.com.coffeebeans.exception.BombaNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

/**
 * Created by AndréFillipe on 06/12/2015.
 */
public class BombaDaoWs implements IBombaDAO {

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private Bomba bomba;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public BombaDaoWs() throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
        objectMapper=new ObjectMapper();

    }

    @Override
    public void cadastrar(Bomba bomba) throws SQLException, BombaJaExistenteException, ViolacaoChaveEstrangeiraException, RepositorioException {
        webResource = client
                .resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/add");
        webResource.type(MediaType.APPLICATION_JSON).post(bomba);

    }

    @Override
    public List<Bomba> listar() throws SQLException, ListaVaziaException {
        List<Bomba> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/all");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<Bomba> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<Bomba>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list2;

    }

    @Override
    public Bomba procurar(int id) throws SQLException, BombaNaoEncontradaException {
        try {

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/procurar/" + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            bomba = null;
            bomba = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Bomba>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bomba;

    }

    @Override
    public Bomba procurarPorRepositorio(int idRepositorio) throws SQLException, BombaNaoEncontradaException {
        try {

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/procurarPorRep/" + idRepositorio);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            bomba = null;
            bomba = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Bomba>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bomba;
    }

    @Override
    public void atualizar(Bomba bomba) throws BombaNaoEncontradaException, SQLException {
        webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/update");
        webResource.type(MediaType.APPLICATION_JSON).put(bomba);
    }

    @Override
    public void excluir(int id) throws SQLException, BombaNaoEncontradaException {
        webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/excluir/" + id);
        webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

    }

    @Override
    public Bomba procurar(String descricao) throws SQLException, BombaNaoEncontradaException {
        try {

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS5/bomba/procurar2/" + descricao.replace(" ", "%20"));

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            bomba = null;
            bomba = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Bomba>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bomba;
    }
}
