package com.liro.animals.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.StringPrivateKeySupplier;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.function.Supplier;

@Configuration
public class OciClient {


    @Bean
    public ObjectStorage objectStorage(){

        Supplier<InputStream> privateKeySupplier
                = new StringPrivateKeySupplier("-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDhGkXHlE/JVunk\n" +
                "27jUSOPI0KuA1Ip6X+dFT7SOeeStC0b1ER4i4lQoz8jz5EIVfpMoqDkkAAzVOMCG\n" +
                "q2PCKaHInKVgAGKsui+nvQWjajkI2lPUQVpO8rPUlaP0q2Y5aeTkCaPjiuX70p8/\n" +
                "TgXEtQACgs5CLxsbbP6CHJ7RBuG4LEJcHhHjvCJdc4IhAPRCG6ZNsdciaqS7B7qx\n" +
                "BFOcx/+rub5heAyOqegECPzHMab2bRumC3RR0ZkXNjp5191BIGt0z42BKZnARJEh\n" +
                "2dUsjoyPaEeR0J5BuOV5vPzS0DsX8tT7JRxq2OrBdGVgctLglcIg3xgcw3/uZo7Q\n" +
                "QdWolSV7AgMBAAECggEAGJ9dE/r/IeY7PhNitlB6yhPj4/sGYrhsKG1oMfrXWVkA\n" +
                "cVIRWn4oooYyqarwJ5W+1xWqRRZ++4ZyWMi/6BR8Y+CZSP+sdiRaOMjjqFUNhBBj\n" +
                "AbaxqtfAH/ghBQ8H5tkpy7Uvk3Y1Y0ROCF3/zN0s8RlfImeGBDakkurlMbbE/fYf\n" +
                "ju5Ag/MyuPrkhiAzsiqH7Bui1rir7s09CQRfH+ipdRN6CT6+rU4i2Gj6TcAmv6lG\n" +
                "9C2r/vlinL7HmJwA2HhOXul2txEEuXgW1eIZTk221jAsO2NG+TvTJMbEU6YWzebN\n" +
                "368k/NVnQOze8yb5x5CjP7wo6OyuCiZ/kt0JhIukcQKBgQDy0NSnK45lEpdmUfY0\n" +
                "Xbhqhwhsde2RwSeyqWqgY2z3itSa/cADMx4HF4EdS9j4xHMa7WsMDwPyck633cXe\n" +
                "VRv1KGHGSLBpjytGFSFwKz+ySKplEeOI0NawKclZtL3+p4gN10qVwDeIUjOfa5wh\n" +
                "e1q9M3ZUlS/4EHkHvlibM9Tp1wKBgQDtUzozMY79+HPfxyjfR+TijBZ3zXqrhz+r\n" +
                "0JpWfAwoLreyrwqIGx5cD0WdlLuIzPYTkn0eXSHrd2gOtrOAD0hUnYW93oPcg/d/\n" +
                "7TzKynF/ycnqj/Ewg/LWCaz8PLhfAfcVF2/890COm/Vtdrvmd2eInbk1n4RtIB3j\n" +
                "3q4BhhzU/QKBgQCzi49y/VYRjqm80yZGmnZe7vJfoOPGPxlnlrZtWpecg1+qK6/r\n" +
                "rcSqYcrrKsaJTFoAs0XQaNn2UGe8tZQ2TqCZqv6BbAZmR6Mr8zwCgThcUDcO/SdO\n" +
                "Z34YTQ3B80/6GU8VTPiturBVcNMTMrMEXv49oOwCpz4NI8Ea5LkIKk9piQKBgEnA\n" +
                "CWALUJJf+TJbY7ovT1OPkRPdyPcw6npsMuoZVnXOPsvKVY2CT+bv4AReSgDzIK1o\n" +
                "YnVjH71at/2KjTGIjkOtR/h/A58ta2ZXxQKH8slxXcwEu0EGYBpfm2AvRmRP6gal\n" +
                "5+lGtMsccCJ6OV4fUQ9FZF7jmXWH+4Hqm0xh7ufJAoGAL87Q1HyezD/DzPhtNq9L\n" +
                "uHA7bfzzLrDySF6w+xxuxdAMk8xWffGNmzwVR0PVAe4sZ+Vz8tTlK9XQD0HplYor\n" +
                "aqtNulA5OvV7P29h3mxychX0NVhB8dya9Ngr0bz3wfjvP+gFajhJd+ERdCDCHv5R\n" +
                "Nqo4fOC/JCNSeG7sM1C2shc=\n" +
                "-----END PRIVATE KEY-----\n" +
                "OCI_API_KEY");


        final SimpleAuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .tenantId("ocid1.tenancy.oc1..aaaaaaaaggvk6ahdnlod7immbighowzk64kgkw2xesej65zirfl6jnc4a4zq")
                .userId("ocid1.user.oc1..aaaaaaaaws5uy3cyv5hfz4fbe3fzcvgekr5rseacbtegzggrregsmnzxcjoq")
                .fingerprint("ef:08:c8:e4:0b:e1:6e:22:cd:b8:e1:1e:bd:1e:71:60")
                .region(Region.SA_SANTIAGO_1)
                .privateKeySupplier(privateKeySupplier)
                .build();

        return ObjectStorageClient.builder().region(Region.SA_SANTIAGO_1).build(provider);
    }
}
