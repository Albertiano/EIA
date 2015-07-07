package br.net.eia.util.certificado;

import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;


public class PegarKs {
    static KeyStore ks;
    static String alias;
    static PrivateKeyEntry keyEntry;
    static PrivateKey pKey;

    public static KeyStore getKS() {
        return ks;
    }

    public static void setKS(KeyStore kys, String senha, String alias) {
        PegarKs.ks = kys;
        PegarKs.setAlias(alias);
        try {
            ks.load(null, senha == null ? null : senha.toCharArray());
            PegarKs.setKeyEntry((KeyStore.PrivateKeyEntry) ks.getEntry(alias, senha == null ? new KeyStore.PasswordProtection("changeit".toCharArray()) : new KeyStore.PasswordProtection(senha.toCharArray())));
            PegarKs.setpKey((PrivateKey) ks.getKey(alias, senha == null ? null : senha.toCharArray()));

            verificarValidade();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Senha do Certificado Digital incorreta ou Certificado inválido.\n\n" + e.getMessage());
            e.printStackTrace();

        }
    }

    public static X509Certificate getX509Certificate() {
        X509Certificate x509 = null;
        try {
            x509 = (X509Certificate) ks.getCertificate(alias);
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }
        return x509;
    }

    public static String getAlias() {
        return alias;
    }

    public static void setAlias(String aAlias) {
        alias = aAlias;
    }

    public static PrivateKeyEntry getKeyEntry() {
        return keyEntry;
    }

    public static void setKeyEntry(PrivateKeyEntry aKeyEntry) {
        keyEntry = aKeyEntry;
    }

    private static void verificarValidade() {
        X509Certificate cert = null;
        try {
            Certificate certificado = null;
            try {
                certificado = (Certificate) ks.getCertificate(getAlias());
            } catch (KeyStoreException ex) {
                ex.printStackTrace();
            }

            cert = (X509Certificate) certificado;

            cert.checkValidity();
        } catch (CertificateNotYetValidException ex) {
            ex.printStackTrace();
        } catch (CertificateExpiredException e1) {            
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss");
            JOptionPane.showMessageDialog(null, cert.getSubjectDN().getName()
                    + "\n"
                    + "Validade do certificado digital espirada em: "
                    + dateFormat.format(cert.getNotAfter())
                    + "\n\n"
                    + "Adquira um novo certificado.", "Aviso!", JOptionPane.ERROR_MESSAGE);
                   
        }
    }

    public static PrivateKey getpKey() {
        return pKey;
    }

    public static void setpKey(PrivateKey apKey) {
        pKey = apKey;
    }
}