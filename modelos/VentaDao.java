
package Modelo;
//MAYOR IMPOTACIONES DE PDF
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

public class VentaDao {
    
    //CONEXIONES
    
    Connection Conexion;
    Conexion Conectar = new Conexion();
    PreparedStatement Consulta;
    ResultSet Resultado;
    int Vaciar;
    //-----------------
   
    
    
    //OBTENER VALORES
    public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(sql);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                id = Resultado.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    
    
    
    //REGISTRO DE VENTAS
    public int RegistrarVenta(Venta v){
        String Registro_VT = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Registro_VT);
            Consulta.setInt(1, v.getCliente());
            Consulta.setString(2, v.getVendedor());
            Consulta.setDouble(3, v.getTotal());
            Consulta.setString(4, v.getFecha());
            Consulta.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return Vaciar;
    }
    
    
    
    //DETALLE
    public int RegistrarDetalle(Detalle Dv){
       String Detalles = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Detalles);
            Consulta.setInt(1, Dv.getId_pro());
            Consulta.setInt(2, Dv.getCantidad());
            Consulta.setDouble(3, Dv.getPrecio());
            Consulta.setInt(4, Dv.getId());
            Consulta.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return Vaciar;
    }
    
    
    
    //ACTUALIZAR LA CANTIDAD
    public boolean ActualizarStock(int cant, int id){
        String UPDATE_STK = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(UPDATE_STK);
            Consulta.setInt(1,cant);
            Consulta.setInt(2, id);
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    
    
    //LISTA VENTAS
    public List Listarventas(){
       List<Venta> ListaVenta = new ArrayList();
       String Lista_VT = "SELECT c.id AS id_cli, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
       try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(Lista_VT);
           Resultado = Consulta.executeQuery();
           while (Resultado.next()) {               
               Venta vent = new Venta();
               vent.setId(Resultado.getInt("id"));
               vent.setNombre_cli(Resultado.getString("nombre"));
               vent.setVendedor(Resultado.getString("vendedor"));
               vent.setTotal(Resultado.getDouble("total"));
               ListaVenta.add(vent);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaVenta;
   }
    
    
    
    //BUSCAR VENTAS
    public Venta BuscarVenta(int id){
        Venta Busqueda_vt = new Venta();
        String buscarVT = "SELECT * FROM ventas WHERE id = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(buscarVT);
            Consulta.setInt(1, id);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                Busqueda_vt.setId(Resultado.getInt("id"));
                Busqueda_vt.setCliente(Resultado.getInt("cliente"));
                Busqueda_vt.setTotal(Resultado.getDouble("total"));
                Busqueda_vt.setVendedor(Resultado.getString("vendedor"));
                Busqueda_vt.setFecha(Resultado.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Busqueda_vt;
    }
    
    
    
    
    //LLAMADOS DE PDF
    public void pdfV(int idventa, int idCliente, double total, String usuario) {
    try {
        Date fechaActual = new Date();
        String rutaArchivo = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "venta.pdf";
        FileOutputStream archivo = new FileOutputStream(new File(rutaArchivo));
        Document doc = new Document();
        PdfWriter.getInstance(doc, archivo);
        doc.open();

        agregarEncabezado(doc, idventa, fechaActual, usuario);
        agregarInfoCliente(doc, idCliente);
        agregarDetalleVenta(doc, idventa);
        agregarTotal(doc, total);

        doc.close();
        archivo.close();
        Desktop.getDesktop().open(new File(rutaArchivo));
    } catch (DocumentException | IOException e) {
        System.out.println(e.toString());
    }
}

    
    
    //ENCABEZADO
    private void agregarEncabezado(Document doc, int idventa, Date fechaActual, String usuario) throws DocumentException, BadElementException {
            try {
                Image img = Image.getInstance(getClass().getClassLoader().getResource("Imagenes/fyde.png"));
            } catch (IOException ex) {
                Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        Paragraph fecha = new Paragraph();
        Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
        fecha.add(Chunk.NEWLINE);
        fecha.add("Vendedor: " + usuario + "\nFolio: " + idventa + "\nFecha: " + new SimpleDateFormat("dd/MM/yyyy").format(fechaActual) + "\n\n");

        PdfPTable encabezado = new PdfPTable(4);
        encabezado.setWidthPercentage(100);
        encabezado.getDefaultCell().setBorder(0);
        float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
        encabezado.setWidths(columnWidthsEncabezado);
        encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
        //encabezado.addCell(img);
        encabezado.addCell("");
        encabezado.addCell("Serial:    " + obtenerConfiguracion("Serial") + "\nNombre: " + obtenerConfiguracion("nombre") + "\nTeléfono: " + obtenerConfiguracion("telefono") + "\nDirección: " + obtenerConfiguracion("direccion") + "\n\n");
        encabezado.addCell(fecha);

        doc.add(encabezado);
    }

    
    
    //OBTIENE LA INFORMACION DE LA TIENDA
        private String obtenerConfiguracion(String campo) {
            String INFORMACION_TIENDA = "SELECT " + campo + " FROM config";
            try {
                Conexion = Conectar.getConnection();
                Consulta = Conexion.prepareStatement(INFORMACION_TIENDA);
                Resultado = Consulta.executeQuery();
                if (Resultado.next()) {
                    return Resultado.getString(campo);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return "";
        }
        
        
        
        
        //INFORMACION DEL CLIENTE
        private void agregarInfoCliente(Document doc, int idCliente) throws DocumentException {
            Paragraph infoCliente = new Paragraph();
            infoCliente.add(Chunk.NEWLINE);
            infoCliente.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(infoCliente);

            PdfPTable tablaCliente = new PdfPTable(3);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{50f, 25f, 25f};
            tablaCliente.setWidths(columnWidthsCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", obtenerNegrita()));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", obtenerNegrita()));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", obtenerNegrita()));

            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);

            tablaCliente.addCell(cliNom);
            tablaCliente.addCell(cliTel);
            tablaCliente.addCell(cliDir);

            agregarInfoClienteDesdeBD(tablaCliente, idCliente);

            doc.add(tablaCliente);
        }

        
        
        //LETRA
        private Font obtenerNegrita() {
            return new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
        }

        
        
        
        
        //AGREGAR INFO AL PDF
        private void agregarInfoClienteDesdeBD(PdfPTable tablaCliente, int idCliente) {
            String consultaCliente = "SELECT * FROM clientes WHERE id = ?";
            try {
                Consulta = Conexion.prepareStatement(consultaCliente);
                Consulta.setInt(1, idCliente);
                Resultado = Consulta.executeQuery();

                if (Resultado.next()) {
                    tablaCliente.addCell(Resultado.getString("nombre"));
                    tablaCliente.addCell(Resultado.getString("telefono"));
                    tablaCliente.addCell(Resultado.getString("direccion") + "\n\n");
                } else {
                    tablaCliente.addCell("Publico en General");
                    tablaCliente.addCell("S/N");
                    tablaCliente.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

        
        
        //DETALLES DE VENTA
        private void agregarDetalleVenta(Document doc, int idventa) throws DocumentException {
            PdfPTable tablaDetalle = new PdfPTable(4);
            tablaDetalle.setWidthPercentage(100);
            tablaDetalle.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tablaDetalle.setWidths(columnWidths);
            tablaDetalle.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c1 = crearCeldaConFondo("Cant.");
            PdfPCell c2 = crearCeldaConFondo("Descripción.");
            PdfPCell c3 = crearCeldaConFondo("P. unt.");
            PdfPCell c4 = crearCeldaConFondo("P. Total");

            tablaDetalle.addCell(c1);
            tablaDetalle.addCell(c2);
            tablaDetalle.addCell(c3);
            tablaDetalle.addCell(c4);

            agregarProductosDesdeBD(tablaDetalle, idventa);

            doc.add(tablaDetalle);
        }

        
        
        
        //FONDO
        private PdfPCell crearCeldaConFondo(String texto) {
            PdfPCell celda = new PdfPCell(new Phrase(texto, obtenerNegrita()));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            return celda;
        }
        
        
        
        //AGREGAR LOS PRODUCTOS
        private void agregarProductosDesdeBD(PdfPTable tablaDetalle, int idventa) {
            String consultaProductos = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
            try {
                Consulta = Conexion.prepareStatement(consultaProductos);
                Consulta.setInt(1, idventa);
                Resultado = Consulta.executeQuery();

                while (Resultado.next()) {
                    double subTotal = Resultado.getInt("cantidad") * Resultado.getDouble("precio");
                    tablaDetalle.addCell(Resultado.getString("cantidad"));
                    tablaDetalle.addCell(Resultado.getString("nombre"));
                    tablaDetalle.addCell(Resultado.getString("precio"));
                    tablaDetalle.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

        
        //TOTAL DE VENTA
        private void agregarTotal(Document doc, double total) throws DocumentException {
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total S/: " + total);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add(obtenerConfiguracion("mensaje"));
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
        }


    
}
