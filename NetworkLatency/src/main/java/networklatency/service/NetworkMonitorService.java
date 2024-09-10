package networklatency.service;

/**
 * @Datetime: 2024/9/10下午9:12
 * @author: Camellia.xioahua
 */
public interface NetworkMonitorService {

    public Long pingDevice();

    public Long checkHttpLatency();

}
