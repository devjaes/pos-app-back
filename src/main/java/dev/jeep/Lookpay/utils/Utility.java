package dev.jeep.Lookpay.utils;

import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.sql.Date;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.models.vm.AssetModel;
import dev.jeep.Lookpay.services.TablesService;

public class Utility {

    public static TablesService tablesService;

    public static Document doc;

    public static Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    private static final Path root = Paths.get("uploads");

    public static String encryptPassword(String password) {
        return argon2.hash(1, 1024, 1, password.toCharArray());
    }

    public static String transformWebDateToDBDate(String date) {
        // Tue Jan 31 2023 01:11:33 GMT-0500 (Ecuador Time) to 2023-01-31 01:11:33
        HashMap<String, String> months = new HashMap<>();
        months.put("Jan", "01");
        months.put("Feb", "02");
        months.put("Mar", "03");
        months.put("Apr", "04");
        months.put("May", "05");
        months.put("Jun", "06");
        months.put("Jul", "07");
        months.put("Aug", "08");
        months.put("Sep", "09");
        months.put("Oct", "10");
        months.put("Nov", "11");
        months.put("Dec", "12");

        String[] dateParts = date.split(" ");
        String year = dateParts[3];
        String month = months.get(dateParts[1]);
        String day = dateParts[2];
        String time = dateParts[4];
        String[] timeParts = time.split(":");
        String hour = timeParts[0];
        String minute = timeParts[1];
        String second = timeParts[2];
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    public static String module11(String code) {
        if (!code.matches("\\d+")) {
            return null;
        }
        int sum = 0;
        int factor = 2;
        for (int i = code.length() - 1; i >= 0; i--) {
            if (i == code.length() - 1) {
                sum += Integer.valueOf(code.substring(i)) * factor;
            } else {
                sum += Integer.valueOf(code.substring(i, i + 1)) * factor;
            }
            if (factor == 7) {
                factor = 2;
            } else {
                factor++;
            }
        }
        int dv = 11 - (sum % 11);
        if (dv == 10) {
            dv = 1;
        } else if (dv == 11) {
            dv = 0;
        }
        return String.valueOf(dv);
    }

    public static String increaseId(String id) {
        int lastId = Integer.parseInt(id);
        lastId++;
        String newId = String.valueOf(lastId);
        if (newId.length() == 1) {
            newId = "00" + newId;
        } else if (newId.length() == 2) {
            newId = "0" + newId;
        }
        return newId;
    }

    public static String increaseSequential(String sequential) {
        int lastSequential = Integer.parseInt(sequential);
        lastSequential++;
        String newSequential = String.valueOf(lastSequential);
        if (newSequential.length() == 1) {
            newSequential = "00000000" + newSequential;
        } else if (newSequential.length() == 2) {
            newSequential = "0000000" + newSequential;
        } else if (newSequential.length() == 3) {
            newSequential = "000000" + newSequential;
        } else if (newSequential.length() == 4) {
            newSequential = "00000" + newSequential;
        } else if (newSequential.length() == 5) {
            newSequential = "0000" + newSequential;
        } else if (newSequential.length() == 6) {
            newSequential = "000" + newSequential;
        } else if (newSequential.length() == 7) {
            newSequential = "00" + newSequential;
        } else if (newSequential.length() == 8) {
            newSequential = "0" + newSequential;
        }
        return newSequential;
    }

    public static Double round(Double num) {
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static File invoice(StoreModel store, InvoiceModel invoice, List<SellingProductModel> sellingProducts)
            throws Exception {

        CustomerModel customer = invoice.getCustomer();
        BoxModel box = invoice.getBox();
        BranchModel branch = box.getBranch();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        doc = implementation.createDocument(null, "factura", null);
        Element root = doc.getDocumentElement();

        root.setAttribute("id", "comprobante");
        root.setAttribute("version", "1.0.0");

        // <infoTributaria>
        Element infoTributaria = doc.createElement("infoTributaria");
        addElement(infoTributaria, "ambiente", invoice.getEnvironmentType().getCodeSri());
        addElement(infoTributaria, "tipoEmision", invoice.getEmissionType().getCodeSri());
        addElement(infoTributaria, "razonSocial", store.getStoreName());
        addElement(infoTributaria, "nombreComercial", store.getTradeName());
        addElement(infoTributaria, "ruc", store.getRuc());
        addElement(infoTributaria, "claveAcceso", invoice.getAccessKey());
        addElement(infoTributaria, "codDoc", invoice.getReceiptType().getCodeSri());
        addElement(infoTributaria, "estab", branch.getKey());
        addElement(infoTributaria, "ptoEmi", box.getKey());
        addElement(infoTributaria, "secuencial", box.getSequential());
        addElement(infoTributaria, "dirMatriz", store.getAddress());
        root.appendChild(infoTributaria);
        // </infoTributaria>

        // <infoFactura>
        Element infoFactura = doc.createElement("infoFactura");
        addElement(infoFactura, "fechaEmision", convertDate(invoice.getEmissionDate()));
        addElement(infoFactura, "dirEstablecimiento", branch.getAddress());
        if (store.getEspecialTaxPayer() != null) {
            addElement(infoFactura, "contribuyenteEspecial", store.getEspecialTaxPayer());
        }
        if (store.getForcedAccounting()) {
            addElement(infoFactura, "obligadoContabilidad", "SI");
        } else {
            addElement(infoFactura, "obligadoContabilidad", "NO");
        }
        addElement(infoFactura, "tipoIdentificacionComprador", customer.getIdentificationType().getCodeSri());
        addElement(infoFactura, "guiaRemision", invoice.getRemissionGuide());
        addElement(infoFactura, "razonSocialComprador", customer.getBusinessName());
        addElement(infoFactura, "identificacionComprador", customer.getIdentification());
        addElement(infoFactura, "direccionComprador", customer.getAddress());
        addElement(infoFactura, "totalSinImpuestos", Double.toString(invoice.getTotalWithoutTax()));
        addElement(infoFactura, "totalDescuento", Double.toString(invoice.getTotalDiscount()));
        addElement(infoFactura, "totalConImpuestos", Double.toString(invoice.getTotal()));
        addElement(infoFactura, "propina", Double.toString(invoice.getTip()));
        addElement(infoFactura, "importeTotal", Double.toString(invoice.getTotal()));
        addElement(infoFactura, "moneda", invoice.getCurrency());

        Element pagos = doc.createElement("pagos");
        Element pago = doc.createElement("pago");
        addElement(pago, "formaPago", "01");
        addElement(pago, "total", Double.toString(invoice.getTotal()));
        pagos.appendChild(pago);
        infoFactura.appendChild(pagos);

        root.appendChild(infoFactura);
        // </infoFactura>

        // <detalles>
        Element detalles = doc.createElement("detalles");
        for (SellingProductModel sp : sellingProducts) {
            ProductModel product = sp.getProduct();

            Element detalle = doc.createElement("detalle");
            addElement(detalle, "codigoPrincipal", product.getMainCode());
            addElement(detalle, "codigoAuxiliar", product.getAuxCode());
            addElement(detalle, "descripcion", product.getDescription());
            addElement(detalle, "cantidad", Integer.toString(sp.getQuantity()));
            addElement(detalle, "precioUnitario", Double.toString(product.getUnitPrice()));
            addElement(detalle, "descuento", Double.toString(sp.getDiscount()));
            addElement(detalle, "precioTotalSinImpuesto", Double.toString(sp.getSubtotal()));

            Element detallesAdicionales = doc.createElement("detallesAdicionales");
            Element detAdicional = doc.createElement("detAdicional");
            detAdicional.setAttribute("nombre", "descripcion");
            detAdicional.setAttribute("valor", "valor");
            detallesAdicionales.appendChild(detAdicional);

            detalle.appendChild(detallesAdicionales);

            Element impuestos = doc.createElement("impuestos");

            Element impuesto = doc.createElement("impuesto");
            addElement(impuesto, "codigo", product.getIvaType().getTaxType().getCodeSri());
            addElement(impuesto, "codigoPorcentaje", product.getIvaType().getCodeSri());
            addElement(impuesto, "tarifa", Integer.toString(sp.getIva()));
            addElement(impuesto, "baseImponible", Double.toString(sp.getSubtotal()));
            addElement(impuesto, "valor", Double.toString(sp.getIvaValue()));
            impuestos.appendChild(impuesto);

            if (product.getIceType() != null) {
                Element impuesto1 = doc.createElement("impuesto");
                addElement(impuesto1, "codigo", product.getIceType().getTaxType().getCodeSri());
                addElement(impuesto1, "codigoPorcentaje", product.getIceType().getCodeSri());
                addElement(impuesto1, "tarifa", Integer.toString(sp.getIce()));
                addElement(impuesto1, "baseImponible", Double.toString(sp.getSubtotal()));
                addElement(impuesto1, "valor", Double.toString(sp.getIceValue()));
                impuestos.appendChild(impuesto1);
            }
            if (product.getIrbpType() != null) {
                Element impuesto1 = doc.createElement("impuesto");
                addElement(impuesto1, "codigo", product.getIrbpType().getTaxType().getCodeSri());
                addElement(impuesto1, "codigoPorcentaje", product.getIrbpType().getCodeSri());
                addElement(impuesto1, "tarifa", Integer.toString(sp.getIrbp()));
                addElement(impuesto1, "baseImponible", Double.toString(sp.getSubtotal()));
                addElement(impuesto1, "valor", Double.toString(sp.getIrbpValue()));
                impuestos.appendChild(impuesto1);
            }

            detalle.appendChild(impuestos);

            detalles.appendChild(detalle);
        }

        root.appendChild(detalles);
        // </detalles>

        // <infoAdicional>
        Element infoAdicional = doc.createElement("infoAdicional");
        Element campoAdicional = doc.createElement("campoAdicional");
        campoAdicional.setAttribute("nombre", ".");
        campoAdicional.setTextContent("Observacion");
        infoAdicional.appendChild(campoAdicional);

        root.appendChild(infoAdicional);
        // </infoAdicional>

        Source source = new DOMSource(doc);
        File file = new File("uploads/" + invoice.getAccessKey() + ".xml");
        Result result = new StreamResult(file);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);

        return file;
    }

    public static void addElement(Element rootElement, String elementName, String elementContent) {
        Element newElement = doc.createElement(elementName);
        newElement.setTextContent(elementContent);
        rootElement.appendChild(newElement);
    }

    public static String convertDate(Date date) {
        String[] dateParts = date.toString().split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        return day + "/" + month + "/" + year;
    }

    public static void saveElectronicSignatureFile(String electronicSignatureKey, AssetModel assetModel)
            throws IOException {

        if (!"application/x-pkcs12".equals(assetModel.getContentType())) {
            throw new IllegalArgumentException("El tipo de contenido no es válido para un archivo .p12");
        }

        Files.createDirectories(root);

        String fileName = electronicSignatureKey.split("/")[1];

        Files.write(root.resolve(fileName), assetModel.getContent());

    }

    public static void deleteElectronicSignatureFile(String electronicSignatureKey) throws IOException {
        String fileName = electronicSignatureKey.split("/")[1];
        Files.deleteIfExists(root.resolve(fileName));
    }
}
