package pro.schuhmann.tmdlib;

import org.junit.BeforeClass;
import org.junit.Test;
import pro.schuhmann.tmdlib.enums.PublicKeyType;
import pro.schuhmann.tmdlib.enums.SignatureType;
import pro.schuhmann.tmdlib.parts.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TMDTest {

  private static TMD tmd;

  @BeforeClass
  public static void setUp() {
    try {
      File tmdFile = new File("test.tmd");
      if (tmdFile.exists() && (tmdFile.length() == 4708)) {
        System.out.println("TMD file with valid size exists! Skip download.");
      } else {
        if (tmdFile.exists()) {
          System.out.println("Already existing file has a wrong file size: deleting.");
          tmdFile.delete();
        }
        // Hey, let's download the TMD for Super Smash Bros.! :)
        URL fileToDownload = new URL("http://nus.cdn.c.shop.nintendowifi.net/ccs/download/00040000000EE000/tmd.2080");
        ReadableByteChannel rbc = Channels.newChannel(fileToDownload.openStream());
        FileOutputStream fos = new FileOutputStream("test.tmd");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
      }

      tmd = new TMD(tmdFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void tmdSignatureDataTest(){
    SignatureData sd = tmd.getSignatureData();
    HexString hs = new HexString("52729E7EDE1599128CC40096579DABA49CD5A2D78F3460364B0F0FB51D1FAF310A3E2930E13DA8FD5906A29FB98496E9DA0E124C6FC305E7E7E71EB3B63C9CA53D1E5E4C0FBC1900623F0C808D8AADE9B2B391BDB345E60105863E4317E304AB9A9F3757954E45FE315394A18B6610DFA30115F6D68857197E0086FB7CB7E5CA6CD0AAE99AAD1503AA31B259FFA3DE0EBB311CC7E80C860D18F06E21B662FD0840F5EE9A9630793BFEF22DB3F5E88C064D24BF22968D23F5C069017143FD572AB5ED0953138D5348C3CC8F403D36592DB08EB3BFC3E5764D73604370BF8C88BD5CF51FAAA4D7F3BEBF8D6255C80F46DFD6604A4072CDD81C812748962E775574");

    assertEquals(SignatureType.RSA_2048_SHA256, sd.getSignatureType());
    assertTrue(hs.equals(sd.getSignature()));
  }

  @Test
  public void tmdHeaderTest() {
    Header hd = tmd.getHeader();
    String signatureIssuer           = "Root-CA00000003-CP0000000b";
    HexString titleId                = new HexString("00040000000EE000");
    HexString contentInfoRecordsHash = new HexString("79231AEFA0EB1A6E5F4B4469ED2CBACE18DE4AC8DE8397C6FA19EA152F2D3221");

    assertEquals(signatureIssuer, hd.getSignatureIssuer());
    assertEquals(1, hd.getVersion());
    assertEquals(0, hd.getCaCrlVersion());
    assertEquals(0, hd.getSignerCrlVersion());
    assertEquals(0, hd.getSystemVersion());
    assertTrue(titleId.equals(hd.getTitleId()));
    assertEquals(64, hd.getTitleType());
    assertEquals(0, hd.getGroupId());
    assertEquals(2048, hd.getSaveDataSize());
    assertEquals(0, hd.getSrlPrivateSaveDataSize());
    assertEquals(0, hd.getSrlFlag());
    assertEquals(0, hd.getAccessRights());
    assertEquals(2080, hd.getTitleVersion());
    assertEquals(2, hd.getContentCount());
    assertEquals(0, hd.getBootContent());
    assertTrue(contentInfoRecordsHash.equals(hd.getContentInfoRecordsHash()));
  }

  @Test
  public void tmdContentInfoRecordsTest() {
    ContentInfoRecord cir = tmd.getContentInfoRecords().get(0);
    HexString recordsHash = new HexString("191A28E267F380B06A896C04B156AA587BD42D833AB36E55251061570F8E7139");

    assertEquals(0, cir.getContentIndexOffset());
    assertEquals(2, cir.getContentCommandCount());
    assertTrue(recordsHash.equals(cir.getNextContentRecordsHash()));
  }

  @Test
  public void tmdContentChunkRecordsTest() {
    ContentChunkRecord ccr = tmd.getContentChunkRecords().get(0);
    HexString sha256hash   = new HexString("99813A2BF09F06EF2D4A34C5CF1F39A2FA27217138A9AB9EF1CE0A09025D5138");

    assertEquals(2, ccr.getContentId());
    assertEquals(0, ccr.getContentIndex());
    assertEquals(1, ccr.getContentType());
    assertEquals(1431719936, ccr.getContentSize());
    assertTrue(sha256hash.equals(ccr.getSha256hash()));

    ccr        = tmd.getContentChunkRecords().get(1);
    sha256hash = new HexString("1F63895254EF6280839240F34D451D1FA8F7D6287AEDCAE5C72A0EDF9F292903");

    assertEquals(1, ccr.getContentId());
    assertEquals(1, ccr.getContentIndex());
    assertEquals(1, ccr.getContentType());
    assertEquals(6012928, ccr.getContentSize());
    assertTrue(sha256hash.equals(ccr.getSha256hash()));
  }

  @Test
  public void tmdCertificatesTest() {
    Certificate ctk        = tmd.getCertificates()[0];
    HexString signature    = new HexString("2EA66C66CFF335797D0497B77A197F9FE51AB5A41375DC73FD9E0B10669B1B9A5B7E8AB28F01B67B6254C14AA1331418F25BA549004C378DD72F0CE63B1F7091AAFE3809B7AC6C2876A61D60516C43A63729162D280BE21BE8E2FE057D8EB6E204242245731AB6FEE30E5335373EEBA970D531BBA2CB222D9684387D5F2A1BF75200CE0656E390CE19135B59E14F0FA5C1281A7386CCD1C8EC3FAD70FBCE74DEEE1FD05F46330B51F9B79E1DDBF4E33F14889D05282924C5F5DC2766EF0627D7EEDC736E67C2E5B93834668072216D1C78B823A072D34FF3ECF9BD11A29AF16C33BD09AFB2D74D534E027C19240D595A68EBB305ACC44AB38AB820C6D426560C");
    String issuer          = "Root-CA00000003";
    String name            = "CP0000000b";
    HexString modulus      = new HexString("A689C590FD0B2F0D4F56B632FB934ED0739517B33A79DE040EE92DC31D37C7F73BF04BD3E44E20AB5A6FEAF5984CC1F6062E9A9FE56C3285DC6F25DDD5D0BF9FE2EFE835DF2634ED937FAB0214D104809CF74B860E6B0483F4CD2DAB2A9602BC56F0D6BD946AED6E0BE4F08F26686BD09EF7DB325F82B18F6AF2ED525BFD828B653FEE6ECE400D5A48FFE22D538BB5335B4153342D4335ACF590D0D30AE2043C7F5AD214FC9C0FE6FA40A5C86506CA6369BCEE44A32D9E695CF00B4FD79ADB568D149C2028A14C9D71B850CA365B37F70B657791FC5D728C4E18FD22557C4062D74771533C70179D3DAE8F92B117E45CB332F3B3C2A22E705CFEC66F6DA3772B");
    Integer publicExponent = 65537;

    assertEquals(SignatureType.RSA_2048_SHA256, ctk.getSignatureData().getSignatureType());
    assertTrue(signature.equals(ctk.getSignatureData().getSignature()));
    assertEquals(issuer, ctk.getIssuer());
    assertEquals(name, ctk.getName());
    assertEquals(PublicKeyType.RSA_2048, ctk.getPublicKey().getPublicKeyType());
    assertTrue(modulus.equals(ctk.getPublicKey().getModulus()));
    assertEquals(publicExponent, ctk.getPublicKey().getPublicExponent());
    assertEquals(null, ctk.getPublicKey().getPublicKey());

    ctk            = tmd.getCertificates()[1];
    signature      = new HexString("704138EFBBBDA16A987DD901326D1C9459484C88A2861B91A312587AE70EF6237EC50E1032DC39DDE89A96A8E859D76A98A6E7E36A0CFE352CA893058234FF833FCB3B03811E9F0DC0D9A52F8045B4B2F9411B67A51C44B5EF8CE77BD6D56BA75734A1856DE6D4BED6D3A242C7C8791B3422375E5C779ABF072F7695EFA0F75BCB83789FC30E3FE4CC8392207840638949C7F688565F649B74D63D8D58FFADDA571E9554426B1318FC468983D4C8A5628B06B6FC5D507C13E7A18AC1511EB6D62EA5448F83501447A9AFB3ECC2903C9DD52F922AC9ACDBEF58C6021848D96E208732D3D1D9D9EA440D91621C7A99DB8843C59C1F2E2C7D9B577D512C166D6F7E1AAD4A774A37447E78FE2021E14A95D112A068ADA019F463C7A55685AABB6888B9246483D18B9C806F474918331782344A4B8531334B26303263D9D2EB4F4BB99602B352F6AE4046C69A5E7E8E4A18EF9BC0A2DED61310417012FD824CC116CFB7C4C1F7EC7177A17446CBDE96F3EDD88FCD052F0B888A45FDAF2B631354F40D16E5FA9C2C4EDA98E798D15E6046DC5363F3096B2C607A9D8DD55B1502A6AC7D3CC8D8C575998E7D796910C804C495235057E91ECD2637C9C1845151AC6B9A0490AE3EC6F47740A0DB0BA36D075956CEE7354EA3E9A4F2720B26550C7D394324BC0CB7E9317D8A8661F42191FF10B08256CE3FD25B745E5194906B4D61CB4C2E");
    issuer         = "Root";
    name           = "CA00000003";
    modulus        = new HexString("B279C9E2EEE121C6EAF44FF639F88F078B4B77ED9F9560B0358281B50E55AB721115A177703C7A30FE3AE9EF1C60BC1D974676B23A68CC04B198525BC968F11DE2DB50E4D9E7F071E562DAE2092233E9D363F61DD7C19FF3A4A91E8F6553D471DD7B84B9F1B8CE7335F0F5540563A1EAB83963E09BE901011F99546361287020E9CC0DAB487F140D6626A1836D27111F2068DE4772149151CF69C61BA60EF9D949A0F71F5499F2D39AD28C7005348293C431FFBD33F6BCA60DC7195EA2BCC56D200BAF6D06D09C41DB8DE9C720154CA4832B69C08C69CD3B073A0063602F462D338061A5EA6C915CD5623579C3EB64CE44EF586D14BAAA8834019B3EEBEED379");
    publicExponent = 65537;

    assertEquals(SignatureType.RSA_4096_SHA256, ctk.getSignatureData().getSignatureType());
    assertTrue(signature.equals(ctk.getSignatureData().getSignature()));
    assertEquals(issuer, ctk.getIssuer());
    assertEquals(name, ctk.getName());
    assertEquals(PublicKeyType.RSA_2048, ctk.getPublicKey().getPublicKeyType());
    assertTrue(modulus.equals(ctk.getPublicKey().getModulus()));
    assertEquals(publicExponent, ctk.getPublicKey().getPublicExponent());
    assertEquals(null, ctk.getPublicKey().getPublicKey());
  }
}