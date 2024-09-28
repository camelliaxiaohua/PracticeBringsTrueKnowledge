package camellia.cloudstorage.enums;

/**
 * @Datetime: 2024/9/28下午1:19
 * @author: Camellia.xioahua
 */
public enum SftpEnum {
    HOST("192.168.1.1"),
    PORT("22"),
    USERNAME("root"),
    PASSWORD("123456");

    private final String value;

    SftpEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
