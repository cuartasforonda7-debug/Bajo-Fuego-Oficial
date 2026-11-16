package app.backend.infraestructura.adaptadores;


import dominio.puertos.salida.PagoRepositoryPort;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class PagoRepositoryImpl implements PagoRepositoryPort {
    @Override
    public pago guardar(pago pago) {
        String sql = "INSERT INTO pagos (pedido_id, total_pedido, dinero_recibido, vueltas, metodo_pago) VALUES (?,?,?,?,?)";
        try(Connection con = ConexionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, pago.getPedidoId());
            ps.setDouble(2, pago.getTotalPedido());
            ps.setDouble(3, pago.getDineroRecibido());
            ps.setDouble(4, pago.getVueltas());
            ps.setString(5, pago.getMetodoPago());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) pago.setId(rs.getInt(1));
            return pago;
        }catch(Exception e){ throw new RuntimeException(e); }
    }

    @Override
    public List<pago> listarTodos() {
        List<pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos";
        try(Connection con = ConexionDB.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){
            while(rs.next()){
                pago p = new pago(rs.getInt("pedido_id"), rs.getDouble("total_pedido"), rs.getDouble("dinero_recibido"), rs.getDouble("vueltas"));
                p.setId(rs.getInt("id"));
                lista.add(p);
            }
        }catch(Exception e){ throw new RuntimeException(e); }
        return lista;
    }
}
