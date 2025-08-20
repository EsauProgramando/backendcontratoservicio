package org.autoservicio.backendcontratoservicio.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class base64Util {
    public static File convertBase64ToFile(String base64, String nombreExacto, String extensiondoc) throws IOException {

        if (base64.contains(",")) {
            base64 = base64.substring(base64.indexOf(",") + 1);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        File directorioTemp = new File(System.getProperty("java.io.tmpdir"));
        File archivo = new File(directorioTemp, nombreExacto + "." + extensiondoc);

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(decodedBytes);
        }

        return archivo;
    }
}
