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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

/**
 * Created by AndréFillipe on 05/12/2015.
 */
public class AtividadeRealizadaDaoWs implements IAtividadeRealizadaDAO {
    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private AtividadeRealizada ar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public AtividadeRealizadaDaoWs() throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
    }

    @Override
    public void cadastrar(AtividadeRealizada atividadeRealizada) throws SQLException, AtividadeJaExistenteException, ViolacaoChaveEstrangeiraException, RepositorioException {
        try {

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/add");

            String dtHoraInicio = sdf.format(atividadeRealizada.getDataHoraInicio());
            String dtHoraFim = sdf.format(atividadeRealizada.getDataHoraFim());

            atividadeRealizada.setDateHoraInicio(dtHoraInicio);
            atividadeRealizada.setDateHoraFim(dtHoraFim);

            webResource.type(MediaType.APPLICATION_JSON).post(atividadeRealizada);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
    }

    @Override
    public List<AtividadeRealizada> listar() throws SQLException, ListaVaziaException, RepositorioException {
        List<AtividadeRealizada> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/all");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<AtividadeRealizada> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<AtividadeRealizada>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return list2;
    }

    @Override
    public AtividadeRealizada procurar(int id) throws SQLException, AtividadeNaoEncontradaException, RepositorioException {
        ar = null;
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/procurar/" + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);


            ar = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<AtividadeRealizada>() {
                    });

            Date dateHoraInicio = sdf.parse(ar.getDateHoraInicio());
            Date dateHoraFim = sdf.parse(ar.getDateHoraFim());

            ar.setDataHoraInicio(dateHoraInicio);
            ar.setDataHoraFim(dateHoraFim);

        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return ar;
    }

    @Override
    public List<AtividadeRealizada> procurar(String descricao) throws SQLException, AtividadeNaoEncontradaException, RepositorioException {
        List<AtividadeRealizada> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/procurar2" + descricao.replace(" ", "%20"));

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<AtividadeRealizada> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<AtividadeRealizada>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return list2;
    }


    @Override
    public void atualizar(AtividadeRealizada atividadeRealizada) throws AtividadeNaoEncontradaException, SQLException, RepositorioException {
        try {
            webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/update");
            String dtHoraInicio = sdf.format(atividadeRealizada.getDataHoraInicio());
            String dtHoraFim = sdf.format(atividadeRealizada.getDataHoraFim());
            atividadeRealizada.setDateHoraInicio(dtHoraInicio);
            atividadeRealizada.setDateHoraFim(dtHoraFim);
            webResource.type(MediaType.APPLICATION_JSON).put(atividadeRealizada);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void excluir(int id) throws AtividadeNaoEncontradaException, SQLException, RepositorioException {
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/excluir/" + id);
            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
    }

    @Override
    public List<AtividadeRealizada> getUltimasAtividades() throws RepositorioException, SQLException {
        List<AtividadeRealizada> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/getUltimasAtividades");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<AtividadeRealizada> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<AtividadeRealizada>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }

        for (AtividadeRealizada ar : list2) {

            Date date5 = null;
            Date date6 = null;

            try {
                date5 = sdf.parse(ar.getDateHoraInicio());
                date6 = sdf.parse(ar.getDateHoraFim());

                ar.setDataHoraInicio(date5);
                ar.setDataHoraFim(date6);

            } catch (ParseException e) {
                throw new RepositorioException(e);
            }

        }

        return list2;
    }

    @Override
    public List<AtividadeRealizada> listar(int id) throws SQLException, ListaVaziaException, RepositorioException {
        List<AtividadeRealizada> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/all/ " + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<AtividadeRealizada> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<AtividadeRealizada>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return list2;
    }

    @Override
    public boolean existe(int id_usuario, int id_atividade, Date dataHoraInicio, Date dataHoraFim) throws SQLException, DAOException {
        Boolean existe = null;

        try {
            String dt1 = sdf.format(dataHoraInicio);
            String dt2 = sdf.format(dataHoraFim);

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS2/atividadeRealizada/existe/"
                            + dt1.replace(" ", "%20") + "/" + dt2.replace(" ", "%20") + "/" + id_atividade + "/" + id_usuario);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);


            existe = new ObjectMapper().readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return existe;

    }
}
