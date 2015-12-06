package br.com.coffeebeans.repositorio;

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

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.RepositorioJaExistenteException;
import br.com.coffeebeans.exception.RepositorioNaoEncontradoException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

/**
 * Created by AndréFillipe on 06/12/2015.
 */
public class RepositorioDaoWs implements IRepositorioDAO {
    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private Repositorio repositorio;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RepositorioDaoWs() throws Exception {

        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
        objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(RepositorioCircular.class,
                RepositorioRetangular.class);

    }

    @Override
    public void cadastrar(Repositorio repositorio) throws SQLException, RepositorioJaExistenteException, RepositorioException {
        if (repositorio instanceof RepositorioCircular) {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/circular/add");
            webResource.type(MediaType.APPLICATION_JSON).post(repositorio);
        } else if (repositorio instanceof RepositorioRetangular) {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/retangular/add");
            webResource.type(MediaType.APPLICATION_JSON).post(repositorio);

        }
    }

    @Override
    public List<Repositorio> listar() throws SQLException, RepositorioException {
        List<Repositorio> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/all");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<Repositorio> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<Repositorio>>() {
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
    public Repositorio procurar(int id) throws SQLException, RepositorioNaoEncontradoException {
        Object repositorioo = null;
        Repositorio r = null;
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/procurar/" + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            repositorioo = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Object>() {
                    });

            r = (Repositorio) repositorioo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;

    }

    @Override
    public void atualizar(Repositorio repositorio) throws RepositorioNaoEncontradoException, SQLException {
        webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/update");
        webResource.type(MediaType.APPLICATION_JSON).put(repositorio);

        if (repositorio instanceof RepositorioCircular) {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/circular/update/circular");
            webResource.type(MediaType.APPLICATION_JSON).post(repositorio);
        } else if (repositorio instanceof RepositorioRetangular) {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/retangular/update/retangular");
            webResource.type(MediaType.APPLICATION_JSON).post(repositorio);

        }

    }

    @Override
    public void excluir(int id) throws SQLException, RepositorioNaoEncontradoException {
        webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/excluir/" + id);
        webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

    }

    @Override
    public Repositorio procurar(String descricao) throws SQLException, RepositorioNaoEncontradoException {
        Object repositorioo = null;
        Repositorio r = null;
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS3/repositorio/procurar2/" + descricao.replace(" ", "%20"));

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            repositorioo = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Object>() {
                    });

            r = (Repositorio) repositorioo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;

    }
}
