package com.satgy.embudi.product.client;

import com.satgy.embudi.product.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "embudi-user", url = "localhost:2010")
public interface UserClientRest {

    @GetMapping("/api/user/uuid/{uuid}")
    public User findByUuid(@PathVariable("uuid") String uuid);
}
