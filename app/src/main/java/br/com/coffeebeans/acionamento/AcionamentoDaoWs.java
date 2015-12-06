package br.com.coffeebeans.acionamento;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.AcionamentoJaExistenteException;
import br.com.coffeebeans.exception.AcionamentoNaoEncontradoException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;

/**
 * Created by AndréFillipe on 03/12/2015.
 */
public class AcionamentoDaoWs implements IAcionamentoDAO {
    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private Acionamento acionamento;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AcionamentoDaoWs() throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
    }

    @Override
    public void cadastrar(Acionamento acionamento) throws SQLException, AcionamentoJaExistenteException, RepositorioException {
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/add");

            String dtHoraInicio = sdf.format(acionamento.getDataHoraInicio());
            String dtHoraFim = sdf.format(acionamento.getDataHoraFim());

            acionamento.setDateHoraInicio(dtHoraInicio);
            acionamento.setDateHoraFim(dtHoraFim);

            webResource.type(MediaType.APPLICATION_JSON).post(acionamento);
        } catch (Exception e) {
            throw new RepositorioException(e);
        }
    }


    @Override
    public List<Acionamento> listar() throws SQLException, ListaVaziaException, RepositorioException {
        List<Acionamento> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/all");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<Acionamento> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<Acionamento>>() {
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
    public Acionamento procurar(int id) throws SQLException, AcionamentoNaoEncontradoException, RepositorioException {
        acionamento = null;
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/procurar/" + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);


            acionamento = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Acionamento>() {
                    });

            Date dateHoraInicio = sdf.parse(acionamento.getDateHoraInicio());
            Date dateHoraFim = sdf.parse(acionamento.getDateHoraFim());

            acionamento.setDataHoraInicio(dateHoraInicio);
            acionamento.setDataHoraFim(dateHoraFim);

        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return acionamento;
    }

    @Override
    public Acionamento procurarIni(Date data1, Date data2) throws SQLException, AcionamentoNaoEncontradoException, RepositorioException {
        acionamento = null;

        try {
            String dt1 = sdf.format(data1);
            String dt2 = sdf.format(data2);

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/procurarIni/"
                            + dt1.replace(" ", "%20") + "/" + dt2.replace(" ", "%20"));

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            acionamento = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Acionamento>() {
                    });

            Date date3 = sdf.parse(acionamento.getDateHoraInicio());
            Date date4 = sdf.parse(acionamento.getDateHoraFim());

            acionamento.setDataHoraInicio(date3);
            acionamento.setDataHoraFim(date4);

        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return acionamento;
    }

    @Override
    public Acionamento procurarFim(Date data1, Date data2) throws SQLException, AcionamentoNaoEncontradoException, RepositorioException {
        acionamento = null;

        try {
            String dt1 = sdf.format(data1);
            String dt2 = sdf.format(data2);

            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/procurarFim/"
                            + dt1.replace(" ", "%20") + "/" + dt2.replace(" ", "%20"));

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            acionamento = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Acionamento>() {
                    });

            Date date3 = sdf.parse(acionamento.getDateHoraInicio());
            Date date4 = sdf.parse(acionamento.getDateHoraFim());

            acionamento.setDataHoraInicio(date3);
            acionamento.setDataHoraFim(date4);

        } catch (Exception e) {
            throw new RepositorioException(e);
        }
        return acionamento;
    }

    @Override
    public void atualizar(Acionamento acionamento) throws AcionamentoNaoEncontradoException, SQLException {
        try {
            webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/update");
            String dtHoraInicio = sdf.format(acionamento.getDataHoraInicio());
            String dtHoraFim = sdf.format(acionamento.getDataHoraFim());
            acionamento.setDateHoraInicio(dtHoraInicio);
            acionamento.setDateHoraFim(dtHoraFim);
            webResource.type(MediaType.APPLICATION_JSON).put(acionamento);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void excluir(int id) throws SQLException, AcionamentoNaoEncontradoException, RepositorioException {
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/excluir/" + id);
            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        } catch (Exception e) {
           throw new RepositorioException(e);
        }
    }

    @Override
    public List<Acionamento> getUltimosAcionamentos() throws SQLException, ListaVaziaException, RepositorioException {
        List<Acionamento> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS4/acionamento/getUltimosAcionamentos");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<Acionamento> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<Acionamento>>() {
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
}
