package br.com.coffeebeans.usuario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.coffeebeans.exception.BDException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.EmailJaExistenteException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.util.AndroidServiceIteratorProvider;
import br.com.coffeebeans.util.CriarDb;

/**
 * Created by AndréFillipe on 06/12/2015.
 */
public class UsuarioDaoWs implements IUsuarioDAO {
    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private ObjectMapper objectMapper;
    private Usuario usuario;
    private static final String NOME_TABELA = "usuario";
    private SQLiteDatabase db;
    private static CriarDb conexao;

    public UsuarioDaoWs(Context context) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
        objectMapper = new ObjectMapper();
        conexao = CriarDb.getInstance(context);
    }

    @Override
    public void cadastrar(Usuario usuario) throws SQLException, UsuarioJaExistenteException, DAOException {
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS/user/add");
            webResource.type(MediaType.APPLICATION_JSON).post(usuario);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Usuario> getLista() throws SQLException, DAOException {
        List<Usuario> list2 = new ArrayList<>();
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS/user/all");

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            Collection<Usuario> list = null;

            list = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<ArrayList<Usuario>>() {
                    });
            if (list instanceof List)
                list2 = (List) list;
            else
                list2 = new ArrayList(list);
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return list2;
    }

    @Override
    public Usuario procurar(int id) throws SQLException, DAOException {
        try {
            webResource = client
                    .resource("http://10.0.2.2:8080/WaterLevel/WS/user/procurar/" + id);

            clientResponse = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            usuario = null;
            usuario = objectMapper.readValue(
                    clientResponse.getEntity(String.class),
                    new TypeReference<Usuario>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return usuario;
    }

    @Override
    public void atualizar(Usuario usuario) throws UsuarioNaoEncontradoException, SQLException, DAOException, SQLiteConstraintException, UsuarioJaExistenteException, EmailJaExistenteException {
        try {
            webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS/user/update");
            webResource.type(MediaType.APPLICATION_JSON).put(usuario);
        } catch (Exception e) {
            throw new DAOException(e);

        }
    }

    @Override
    public void excluir(int id) throws SQLException, UsuarioNaoEncontradoException, DAOException {
        try {
            webResource = client.resource("http://10.0.2.2:8080/WaterLevel/WS/user/excluir/" + id);
            webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean loginFacebook(String email) throws SQLException, DAOException {
        Boolean existe = null;

        try {

            webResource = client
                    .resource("http://10.0.2.2/WaterLevel/WS/user/existe/" + email);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            existe = objectMapper.readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return existe;

    }

    @Override
    public void alterarSenha(int id, String senha) throws UsuarioNaoEncontradoException, SQLException, DAOException {
        try {
            webResource = client
                    .resource("http://10.0.2.2/WaterLevel/WS/user/newPwd/" + id + "/" + senha);

            Login login= new Login(id,senha);
            webResource.type(MediaType.APPLICATION_JSON).put(login);

        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean login(String usuario, String senha) throws UsuarioInativoException, SQLException, DAOException {
        Boolean existe = null;

        try {

            webResource = client
                    .resource("http://10.0.2.2/WaterLevel/WS/user/existe/" + usuario + "/" + senha);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            existe = objectMapper.readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return existe;


    }

    @Override
    public boolean existe(String descricao) throws SQLException, DAOException {
        Boolean existe = null;

        try {

            webResource = client
                    .resource("http://10.0.2.2/WaterLevel/WS/user/existe/" + descricao);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            existe = objectMapper.readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return existe;


    }

    @Override
    public boolean existeEmail(String email) throws SQLException, DAOException {
        Boolean existe = null;

        try {

            webResource = client
                    .resource("http://10.0.2.2/WaterLevel/WS/user/existe2/" + email);

            ClientResponse response = webResource.type(
                    MediaType.APPLICATION_JSON).get(ClientResponse.class);

            existe = objectMapper.readValue(response.getEntity(String.class),
                    new TypeReference<Boolean>() {
                    });
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return existe;
    }

    @Override
    public Usuario getUsuarioLogado() throws SQLException, DAOException {
        Cursor cursor = null;
        Usuario usuario = null;

        try {
            db = conexao.openDb();
            if (db != null) {

                String sql = "SELECT * FROM USUARIO_LOGADO WHERE ID = ?";
                String[] whereArgs = {String.valueOf(1)};

                cursor = db.rawQuery(sql, whereArgs);

                cursor.moveToFirst();

                if (cursor.getCount() > 0 && cursor != null) {
                    usuario = procurar(cursor.getInt(cursor.getColumnIndex("ID_USUARIO")));
                    Log.i("procurar usuario logado", "usuario logado achado com sucesso ");
                }
            } else {
                throw new BDException();
            }

        } catch (SQLException e) {
            Log.i("erro no metodo procurar usuario logado da classe usuarioDAO ", "" + e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo procurar usuario logado da classe usuarioDAO ", "" + e.getMessage());
            throw new DAOException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
        return usuario;
    }

    @Override
    public void logout() throws SQLException, DAOException {
        try {
            String where = "ID = ?";
            String[] whereArgs = new String[]{String.valueOf(1)};

            db = conexao.openDb();
            if (db != null)
                db.delete("USUARIO_LOGADO", where, whereArgs);
            else
                throw new BDException();

        } catch (SQLException e) {
            Log.i("erro no metodo logout da classe usuarioDAO ", "" + e.getMessage());
            throw new SQLException(e);

        } catch (Exception e) {
            Log.i("erro no metodo logout da classe usuarioDAO", "" + e.getMessage());
            throw new DAOException(e);

        } finally {
            if (db != null) {
                if (db.isOpen())
                    db.close();
            }
        }
    }
}

