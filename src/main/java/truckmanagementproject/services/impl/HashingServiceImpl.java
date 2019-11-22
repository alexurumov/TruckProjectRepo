package truckmanagementproject.services.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import truckmanagementproject.services.HashingService;

@Service
public class HashingServiceImpl implements HashingService {

    @Override
    public String hash(String str) {
        return DigestUtils.sha1Hex(str);
    }
}
